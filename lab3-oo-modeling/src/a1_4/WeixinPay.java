package a1_4;

/** 微信支付。 */
public class WeixinPay extends Payment {
    private final String number;

    public WeixinPay(double amount, String number) {
        super(amount);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("微信支付 %s  %.2f 元", number, getAmount());
    }
}
