package a1_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 系：至少拥有 1 位指导教师，至少开设 1 门课程。 */
public class Department {
    private final String name;
    private final List<Instructor> instructors = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();

    /** 包内可见：系只能由 School.createDepartment 创建（组合）。 */
    Department(String name) {
        this.name = name;
    }

    /** 一位教师可以同时属于多个系，关联两端同时更新。 */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            instructor.joinDepartment(this);
        }
    }

    public void offer(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public String getName() {
        return name;
    }

    public List<Instructor> getInstructors() {
        return Collections.unmodifiableList(instructors);
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }
}
