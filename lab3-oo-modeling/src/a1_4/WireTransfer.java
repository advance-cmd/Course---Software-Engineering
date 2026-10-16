package a1_4;

/** 在线转账。 */
public class WireTransfer extends Payment {
    private final String bankID;
    private final String bankName;

    public WireTransfer(double amount, String bankID, String bankName) {
        super(amount);
        this.bankID = bankID;
        this.bankName = bankName;
    }

    public String getBankID() {
        return bankID;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public String toString() {
        return String.format("在线转账 %s（%s）  %.2f 元", bankName, bankID, getAmount());
    }
}
