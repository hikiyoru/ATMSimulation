import java.math.BigDecimal;

public abstract class BankEntity {
    protected BigDecimal balance = BigDecimal.valueOf(0);
    protected String cardNumber;

    public BigDecimal getBalance() {
        return balance;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        if (isStringCardNumber(cardNumber)) {
            this.cardNumber = cardNumber;
        }
    }
    public void setBalance(BigDecimal balance) {
        if (isNotNegative(balance)) {
            this.balance = balance;
        }
    }
    public boolean isNotNegative(BigDecimal value) {
        return value.signum() >= 0;
    }
    public boolean isStringCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        return cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    }
    public abstract void updateBalance(BigDecimal amount);
}
