package a1_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 学生：参加 1~5 门课程。 */
public class Student {
    public static final int MAX_SUBJECTS = 5;

    private final String name;
    private final List<Subject> subjects = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public void attend(Subject subject) {
        if (subjects.contains(subject)) {
            return;
        }
        if (subjects.size() >= MAX_SUBJECTS) {
            throw new IllegalStateException(name + " 已参加 " + MAX_SUBJECTS + " 门课程，不能再增加");
        }
        subjects.add(subject);
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }
}
