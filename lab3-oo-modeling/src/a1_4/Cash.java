package a1_4;

/** 现金支付（如货到付款）。 */
public class Cash extends Payment {
    private final double cashTendered;

    public Cash(double amount, double cashTendered) {
        super(amount);
        if (cashTendered < amount) {
            throw new IllegalArgumentException("支付的现金不能少于应付金额");
        }
        this.cashTendered = cashTendered;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    @Override
    public String toString() {
        return String.format("现金（实收 %.2f 元，找零 %.2f 元）  %.2f 元",
                cashTendered, cashTendered - getAmount(), getAmount());
    }
}
