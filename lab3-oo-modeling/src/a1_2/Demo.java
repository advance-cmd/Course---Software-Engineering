package a1_2;

public class Demo {
    public static void main(String[] args) {
        College college = new College("计算机与信息学院");
        Building j1 = new Building("J1 教学楼");
        Building lab = new Building("实验楼");
        college.addBuilding(j1);
        college.addBuilding(lab);

        college.openCourse("软件工程", j1);
        college.openCourse("数据结构", j1);
        college.openCourse("软件工程实验", lab);

        System.out.println("=== A1-2 College / Building / Course ===");
        System.out.println(college.getName() + " 的建筑：");
        for (Building b : college.getBuildings()) {
            System.out.println("  " + b.getName());
        }
        System.out.println(college.getName() + " 的课程：");
        for (Course c : college.getCourses()) {
            System.out.println("  " + c.getTitle() + " @ " + c.getBuilding().getName());
        }

        try {
            college.openCourse("体育", new Building("体育馆"));
        } catch (IllegalArgumentException e) {
            System.out.println("在非本学院建筑开课：" + e.getMessage());
        }
        try {
            college.openCourse("网课", null);
        } catch (IllegalArgumentException e) {
            System.out.println("不指定建筑开课：" + e.getMessage());
        }
    }
}
