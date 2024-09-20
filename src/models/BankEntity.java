package models;

import java.math.BigDecimal;

public abstract class BankEntity {
    protected BigDecimal balance;

    public BankEntity(String balance) {
        setBalance(balance);
    }

    public BigDecimal getBalance() {
        return balance;
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

    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
