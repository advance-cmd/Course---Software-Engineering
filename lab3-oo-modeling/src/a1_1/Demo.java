package a1_1;

public class Demo {
    public static void main(String[] args) {
        Thoroughbred sire = new Thoroughbred(null, null, 2015);
        Thoroughbred dam = new Thoroughbred(null, null, 2017);
        Thoroughbred foal = new Thoroughbred(dam, sire, 2023);

        System.out.println("=== A1-1 Thoroughbred ===");
        System.out.println("foal 在 2026 年的年龄：" + foal.getCurrentAge(2026));
        System.out.println("foal.getFather() == sire：" + (foal.getFather() == sire));
        System.out.println("foal.getMother() == dam：" + (foal.getMother() == dam));
        System.out.println("父亲在 2026 年的年龄：" + foal.getFather().getCurrentAge(2026));
        System.out.println("母亲在 2026 年的年龄：" + foal.getMother().getCurrentAge(2026));
        System.out.println("sire 的父亲（血统未知）：" + sire.getFather());
        try {
            foal.getCurrentAge(2020);
        } catch (IllegalArgumentException e) {
            System.out.println("getCurrentAge(2020) 抛出异常：" + e.getMessage());
        }
    }
}
