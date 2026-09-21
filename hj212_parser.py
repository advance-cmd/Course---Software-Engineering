"""HJ212-2017《污染物在线监控（监测）系统数据传输标准》报文解析。"""

import re


class HJ212Parser:
    """解析 HJ212-2017 报文。

    报文结构：## + 4 位数据段长度（十进制） + 数据段 + 4 位 CRC（十六进制） + \\r\\n
    数据段示例：QN=...;ST=32;CN=2011;PW=123456;MN=...;Flag=5;CP=&&DataTime=...;a21026-Rtd=0.1,a21026-Flag=N&&
    """

    _PACKET_RE = re.compile(r"##([0-9]{4})(.*)([0-9A-Fa-f]{4})\r\n", re.DOTALL)

    # 按数值返回的监测因子参数，其余参数（Flag、EFlag、SampleTime、Info 等）保留为字符串
    NUMERIC_PARAMS = {"Rtd", "Min", "Avg", "Max", "ZsRtd", "ZsMin", "ZsAvg", "ZsMax", "Cou"}

    def __init__(self, encoding="utf-8"):
        self.encoding = encoding

    def is_valid_message(self, message):
        """检查报文格式：包头、4 位长度、长度与数据段一致、4 位十六进制 CRC、包尾。不校验 CRC 值。"""
        try:
            self._split(message)
        except (TypeError, ValueError):
            return False
        return True

    def validate_crc(self, message):
        """用 ANSI CRC16 重新计算数据段的 CRC，与报文中的 CRC 比较；格式错误时返回 False。"""
        try:
            data, crc = self._split(message)
        except (TypeError, ValueError):
            return False
        return self.calculate_crc(data) == crc.upper()

    def calculate_crc(self, data):
        """按 HJ212-2017 附录中的 ANSI CRC16 算法计算 CRC（初始值 0xFFFF，多项式 0xA001），返回 4 位大写十六进制字符串。"""
        if isinstance(data, str):
            data = data.encode(self.encoding)
        crc = 0xFFFF
        for byte in data:
            # 标准原文为 crc = (crc >> 8) ^ byte，与 Modbus CRC16 的 crc ^= byte 不同
            crc = (crc >> 8) ^ byte
            for _ in range(8):
                if crc & 0x0001:
                    crc = (crc >> 1) ^ 0xA001
                else:
                    crc >>= 1
        return f"{crc:04X}"

    def parse_data_segment(self, message):
        """解析数据段，返回全部键值对；CP 字段的内容解析为嵌套字典，值均为字符串。

        格式错误时抛出 ValueError。
        """
        data, _ = self._split(message)
        head, sep, cp = data.partition("CP=&&")
        result = self._parse_pairs(head)
        if sep:
            if not cp.endswith("&&"):
                raise ValueError("CP 字段必须以 && 结尾")
            result["CP"] = self._parse_pairs(cp[:-2])
        return result

    def extract_monitoring_data(self, message):
        """从 CP 字段中提取监测因子，返回 {因子编码: {参数: 值}}。

        例如 a21026-Rtd=0.1,a21026-Flag=N 解析为 {"a21026": {"Rtd": 0.1, "Flag": "N"}}。
        DataTime 等不带因子编码的字段会被忽略。
        """
        cp = self.parse_data_segment(message).get("CP", {})
        factors = {}
        for key, value in cp.items():
            code, sep, param = key.rpartition("-")
            if not sep or not code:
                continue
            if param in self.NUMERIC_PARAMS:
                value = self._to_number(value)
            factors.setdefault(code, {})[param] = value
        return factors

    def _split(self, message):
        """校验报文结构并返回 (数据段, CRC 字符串)。"""
        if isinstance(message, bytes):
            message = message.decode(self.encoding)
        if not isinstance(message, str):
            raise TypeError("报文必须是 str 或 bytes")
        match = self._PACKET_RE.fullmatch(message)
        if not match:
            raise ValueError("报文结构应为 ## + 4 位长度 + 数据段 + 4 位 CRC + \\r\\n")
        length, data, crc = match.groups()
        if len(data.encode(self.encoding)) != int(length):
            raise ValueError(f"数据段长度不符：声明 {int(length)}，实际 {len(data.encode(self.encoding))}")
        return data, crc

    @staticmethod
    def _parse_pairs(text):
        """解析以 ; 或 , 分隔的 key=value 列表。"""
        pairs = {}
        for item in re.split(r"[;,]", text):
            if not item:
                continue
            key, sep, value = item.partition("=")
            if not sep:
                raise ValueError(f"无法解析的字段：{item!r}")
            pairs[key] = value
        return pairs

    @staticmethod
    def _to_number(value):
        try:
            return float(value)
        except ValueError:
            return value
