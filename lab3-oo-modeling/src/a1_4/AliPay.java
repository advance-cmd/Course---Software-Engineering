package a1_4;

/** 支付宝支付。 */
public class AliPay extends Payment {
    private final String number;

    public AliPay(double amount, String number) {
        super(amount);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("支付宝 %s  %.2f 元", number, getAmount());
    }
}
