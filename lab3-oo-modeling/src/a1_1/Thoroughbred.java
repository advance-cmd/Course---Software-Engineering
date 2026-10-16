package a1_1;

/** 优良品种的马（纯种马），记录父母和出生年份。 */
public class Thoroughbred {
    private Thoroughbred mother;
    private Thoroughbred father;
    private int birthyear;

    /** 血统未知的马，父母传 null。 */
    public Thoroughbred(Thoroughbred mother, Thoroughbred father, int birthyear) {
        this.mother = mother;
        this.father = father;
        this.birthyear = birthyear;
    }

    /** 返回这匹马在 currentYear 年的年龄（周岁）。 */
    public int getCurrentAge(int currentYear) {
        if (currentYear < birthyear) {
            throw new IllegalArgumentException("currentYear 不能早于出生年份 " + birthyear);
        }
        return currentYear - birthyear;
    }

    public Thoroughbred getFather() {
        return father;
    }

    public Thoroughbred getMother() {
        return mother;
    }
}
