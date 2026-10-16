package a1_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 指导教师：至少属于 1 个系，教授 1~3 门课程。 */
public class Instructor {
    public static final int MAX_SUBJECTS = 3;

    private final String name;
    private final List<Department> departments = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();

    public Instructor(String name) {
        this.name = name;
    }

    /** 由 Department.addInstructor 调用，维护双向关联。 */
    void joinDepartment(Department department) {
        if (!departments.contains(department)) {
            departments.add(department);
        }
    }

    public void teach(Subject subject) {
        if (subjects.contains(subject)) {
            return;
        }
        if (subjects.size() >= MAX_SUBJECTS) {
            throw new IllegalStateException(name + " 已教授 " + MAX_SUBJECTS + " 门课程，不能再增加");
        }
        subjects.add(subject);
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }
}
