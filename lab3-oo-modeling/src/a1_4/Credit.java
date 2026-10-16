package a1_4;

import java.time.LocalDate;

/** 信用卡支付。 */
public class Credit extends Payment {
    private final String number;
    private final String type;
    private final LocalDate expireDate;

    public Credit(double amount, String number, String type, LocalDate expireDate) {
        super(amount);
        this.number = number;
        this.type = type;
        this.expireDate = expireDate;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    @Override
    public String toString() {
        return String.format("信用卡 %s %s（有效期至 %s）  %.2f 元", type, number, expireDate, getAmount());
    }
}
