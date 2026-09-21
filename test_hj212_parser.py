import unittest

from hj212_parser import HJ212Parser

# HJ212-2017 标准正文中的示例报文
STANDARD_MESSAGE = (
    "##0101QN=20160801085857223;ST=32;CN=1062;PW=100000;"
    "MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1C80\r\n"
)


def build_message(parser, data):
    return f"##{len(data):04d}{data}{parser.calculate_crc(data)}\r\n"


class HJ212ParserTest(unittest.TestCase):
    def setUp(self):
        self.parser = HJ212Parser()

    def test_standard_example(self):
        """标准示例报文：格式正确，CRC 为 1C80"""
        self.assertTrue(self.parser.is_valid_message(STANDARD_MESSAGE))
        self.assertTrue(self.parser.validate_crc(STANDARD_MESSAGE))
        self.assertTrue(self.parser.validate_crc(STANDARD_MESSAGE.encode()))
        self.assertEqual(self.parser.calculate_crc(STANDARD_MESSAGE[6:-6]), "1C80")

    def test_invalid_format(self):
        """缺包头、缺包尾、长度不符、CRC 非十六进制：格式校验失败"""
        for message in (
            STANDARD_MESSAGE[1:],
            STANDARD_MESSAGE[:-2],
            STANDARD_MESSAGE.replace("##0101", "##0102"),
            STANDARD_MESSAGE.replace("1C80", "1CZ0"),
        ):
            with self.subTest(message=message):
                self.assertFalse(self.parser.is_valid_message(message))
                self.assertFalse(self.parser.validate_crc(message))

    def test_wrong_crc(self):
        """格式正确但 CRC 错误：CRC 校验失败"""
        message = STANDARD_MESSAGE.replace("1C80", "1C81")
        self.assertTrue(self.parser.is_valid_message(message))
        self.assertFalse(self.parser.validate_crc(message))

    def test_parse_data_segment(self):
        """数据段解析为字典，CP 字段解析为嵌套字典"""
        self.assertEqual(self.parser.parse_data_segment(STANDARD_MESSAGE), {
            "QN": "20160801085857223",
            "ST": "32",
            "CN": "1062",
            "PW": "100000",
            "MN": "010000A8900016F000169DC0",
            "Flag": "5",
            "CP": {"RtdInterval": "30"},
        })

    def test_extract_monitoring_data(self):
        """按因子编码提取监测数据，数值参数转换为 float"""
        message = build_message(
            self.parser,
            "QN=20260921201500000;ST=22;CN=2011;PW=123456;MN=88888880000001;Flag=5;"
            "CP=&&DataTime=20260921201500;a21026-Rtd=12.5,a21026-Flag=N;"
            "a34004-Rtd=35,a34004-ZsRtd=40.2,a34004-Flag=N,a34004-SampleTime=20260921201400&&",
        )
        self.assertTrue(self.parser.validate_crc(message))
        self.assertEqual(self.parser.extract_monitoring_data(message), {
            "a21026": {"Rtd": 12.5, "Flag": "N"},
            "a34004": {"Rtd": 35.0, "ZsRtd": 40.2, "Flag": "N", "SampleTime": "20260921201400"},
        })

    def test_cp_not_closed(self):
        """CP 字段缺少结尾的 &&：抛出 ValueError"""
        with self.assertRaises(ValueError):
            self.parser.parse_data_segment(build_message(self.parser, "QN=1;CP=&&a01001-Rtd=1"))


if __name__ == "__main__":
    unittest.main()
