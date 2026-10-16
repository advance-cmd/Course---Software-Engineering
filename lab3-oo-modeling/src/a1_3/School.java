package a1_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** 学院：由 1 到多个系构成（组合），拥有 0 到多名学生。 */
public class School {
    private final String name;
    private final List<Department> departments = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();

    public School(String name) {
        this.name = name;
    }

    /** 组合：系由学院创建，不能脱离学院单独存在。 */
    public Department createDepartment(String departmentName) {
        Department department = new Department(departmentName);
        departments.add(department);
        return department;
    }

    public void admit(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    /**
     * 检查各关联多重性的下限（上限在 teach/attend 时已检查），
     * 返回不满足的条目，全部满足时返回空列表。
     */
    public List<String> checkMultiplicity() {
        List<String> problems = new ArrayList<>();
        if (departments.isEmpty()) {
            problems.add(name + " 至少要有 1 个系");
        }
        Set<Instructor> instructors = new LinkedHashSet<>();
        Set<Subject> offered = new LinkedHashSet<>();
        for (Department d : departments) {
            if (d.getInstructors().isEmpty()) {
                problems.add(d.getName() + " 至少要有 1 位指导教师");
            }
            if (d.getSubjects().isEmpty()) {
                problems.add(d.getName() + " 至少要开设 1 门课程");
            }
            instructors.addAll(d.getInstructors());
            offered.addAll(d.getSubjects());
        }
        for (Instructor i : instructors) {
            if (i.getSubjects().isEmpty()) {
                problems.add(i.getName() + " 至少要教授 1 门课程");
            }
            for (Subject s : i.getSubjects()) {
                if (!offered.contains(s)) {
                    problems.add(i.getName() + " 教授的 " + s.getTitle() + " 没有由任何系开设");
                }
            }
        }
        for (Student st : students) {
            if (st.getSubjects().isEmpty()) {
                problems.add(st.getName() + " 至少要参加 1 门课程");
            }
            for (Subject s : st.getSubjects()) {
                if (!offered.contains(s)) {
                    problems.add(st.getName() + " 参加的 " + s.getTitle() + " 没有由任何系开设");
                }
            }
        }
        for (Subject s : offered) {
            boolean taught = instructors.stream().anyMatch(i -> i.getSubjects().contains(s));
            if (!taught) {
                problems.add(s.getTitle() + " 至少要有 1 位教师讲授");
            }
        }
        return problems;
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }
}
