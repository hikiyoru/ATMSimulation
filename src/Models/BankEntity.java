package Models;

import java.math.BigDecimal;

public abstract class BankEntity {
    protected BigDecimal balance;
    protected String cardNumber;

    public BankEntity(String cardNumber, String balance) {
        setCardNumber(cardNumber);
        setBalance(balance);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (isStringCardNumber(cardNumber)) {
            this.cardNumber = cardNumber;
        } else {
            throw new IllegalArgumentException("Incorrect card number");
        }
    }

    public void setBalance(String balance) {
        try {
            BigDecimal balanceValue = new BigDecimal(balance);
            if (isNotNegative(balanceValue)) {
                this.balance = balanceValue;
            } else {
                throw new IllegalArgumentException("Balance must be non-negative");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid balance format: " + balance);
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

    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
