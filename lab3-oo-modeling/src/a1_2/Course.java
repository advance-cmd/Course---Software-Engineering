package a1_2;

import java.util.Objects;

/** 课程。只能由 College 创建（组合），并且必须指定上课的建筑。 */
public class Course {
    private final String title;
    private final Building building;

    /** 包内可见：外部只能通过 College.openCourse 创建课程。 */
    Course(String title, Building building) {
        this.title = title;
        // 约束：Course 一定在某个 Building 中进行
        this.building = Objects.requireNonNull(building, "Course 必须在某个 Building 中进行");
    }

    public String getTitle() {
        return title;
    }

    public Building getBuilding() {
        return building;
    }
}
