package a1_4;

/** 支付方式（抽象类），具体方式见各子类。 */
public abstract class Payment {
    private final double amount;

    protected Payment(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("支付金额必须大于 0");
        }
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
