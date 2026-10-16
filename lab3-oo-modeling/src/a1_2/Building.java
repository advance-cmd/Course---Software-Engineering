package a1_2;

/** 建筑。由外部创建后加入学院，学院撤销后建筑仍然存在（聚合）。 */
public class Building {
    private final String name;

    public Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
