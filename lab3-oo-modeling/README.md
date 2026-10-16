# 软件工程实验三：面向对象的系统建模 A

用 Java 实现 A1-1 ~ A1-4 四道题的类模型，类图见 `diagrams/`。

| 目录 | 题目 | 类 |
| --- | --- | --- |
| `src/a1_1` | A1-1 纯种马 | Thoroughbred |
| `src/a1_2` | A1-2 学院、建筑与课程 | College, Building, Course |
| `src/a1_3` | A1-3 学院、系、教师、课程与学生 | School, Department, Instructor, Subject, Student |
| `src/a1_4` | A1-4 网上购物系统 | Customer, Order, OrderStatus, OrderDetail, Product, Payment 及 5 种支付方式 |

每个包里的 `Demo` 类演示模型的用法。需要 JDK 17 及以上（开发时使用 JDK 21）：

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -Dstdout.encoding=UTF-8 -cp out a1_4.Demo
```
