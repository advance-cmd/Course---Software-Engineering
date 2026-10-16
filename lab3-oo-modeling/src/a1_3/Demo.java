package a1_3;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {
        School school = new School("计算机与信息学院");
        Department cs = school.createDepartment("计算机科学系");
        Department se = school.createDepartment("软件工程系");

        Subject ds = new Subject("数据结构");
        Subject os = new Subject("操作系统");
        Subject net = new Subject("计算机网络");
        Subject swe = new Subject("软件工程");
        Subject db = new Subject("数据库原理");
        Subject java = new Subject("Java程序设计");
        cs.offer(ds);
        cs.offer(os);
        cs.offer(net);
        se.offer(swe);
        se.offer(db);
        se.offer(java);

        Instructor wang = new Instructor("王老师");
        Instructor li = new Instructor("李老师");
        Instructor zhang = new Instructor("张老师");
        wang.teach(ds);
        wang.teach(os);
        li.teach(swe);
        li.teach(db);
        li.teach(java);
        zhang.teach(net);
        cs.addInstructor(wang);
        cs.addInstructor(zhang);
        se.addInstructor(li);
        se.addInstructor(wang); // 王老师同时属于两个系

        Student s1 = new Student("学生甲");
        Student s2 = new Student("学生乙");
        s1.attend(ds);
        s1.attend(swe);
        s1.attend(db);
        s2.attend(os);
        s2.attend(net);
        school.admit(s1);
        school.admit(s2);

        System.out.println("=== A1-3 School / Department / Instructor / Subject / Student ===");
        System.out.println(school.getName());
        Set<Instructor> instructors = new LinkedHashSet<>();
        for (Department d : school.getDepartments()) {
            System.out.println("  " + d.getName()
                    + "  开设：" + d.getSubjects().stream().map(Subject::getTitle).toList()
                    + "  教师：" + d.getInstructors().stream().map(Instructor::getName).toList());
            instructors.addAll(d.getInstructors());
        }
        for (Instructor i : instructors) {
            System.out.println("  " + i.getName()
                    + "  所属系：" + i.getDepartments().stream().map(Department::getName).toList()
                    + "  讲授：" + i.getSubjects().stream().map(Subject::getTitle).toList());
        }
        for (Student s : school.getStudents()) {
            System.out.println("  " + s.getName()
                    + "  参加：" + s.getSubjects().stream().map(Subject::getTitle).toList());
        }
        printCheck(school.checkMultiplicity());

        try {
            li.teach(net);
        } catch (IllegalStateException e) {
            System.out.println("李老师教第 4 门课：" + e.getMessage());
        }
        try {
            s1.attend(os);
            s1.attend(net);
            s1.attend(java);
        } catch (IllegalStateException e) {
            System.out.println("学生甲选第 6 门课：" + e.getMessage());
        }

        school.admit(new Student("学生丙"));
        school.createDepartment("人工智能系");
        System.out.println("新增未选课的学生丙和空的人工智能系后：");
        printCheck(school.checkMultiplicity());
    }

    private static void printCheck(List<String> problems) {
        if (problems.isEmpty()) {
            System.out.println("多重性检查：全部满足");
        } else {
            System.out.println("多重性检查：");
            problems.forEach(p -> System.out.println("  × " + p));
        }
    }
}
