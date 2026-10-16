package a1_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 学院：聚合若干 Building，组合若干 Course。 */
public class College {
    private final String name;
    /** 聚合：建筑由外部创建并传入，可以独立于学院存在。 */
    private final List<Building> buildings = new ArrayList<>();
    /** 组合：课程由学院创建，随学院一起销毁。 */
    private final List<Course> courses = new ArrayList<>();

    public College(String name) {
        this.name = name;
    }

    public void addBuilding(Building building) {
        if (!buildings.contains(building)) {
            buildings.add(building);
        }
    }

    /** 开设课程。上课地点必须是本学院的建筑。 */
    public Course openCourse(String title, Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Course 必须在某个 Building 中进行");
        }
        if (!buildings.contains(building)) {
            throw new IllegalArgumentException("课程只能安排在本学院的建筑中：" + building.getName());
        }
        Course course = new Course(title, building);
        courses.add(course);
        return course;
    }

    public String getName() {
        return name;
    }

    public List<Building> getBuildings() {
        return Collections.unmodifiableList(buildings);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
