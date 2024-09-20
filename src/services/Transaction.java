package services;

import exceptions.InsufficientFundsException;
import models.BankEntity;

import java.math.BigDecimal;

public class Transaction<T extends BankEntity> {
    private final T source;
    private final T target;

    public Transaction(T source, T target) {
        this.source = source;
        this.target = target;
    }

    public boolean withdraw(String stringAmount) {
        try {
            BigDecimal amount = new BigDecimal(stringAmount);
            if (!isPositive(amount)) {
                throw new IllegalArgumentException("The amount must be positive");
            }
            if (target.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds on bank card");
            }
            if (source.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds in ATM");
            }
            source.updateBalance(amount.negate());
            target.updateBalance(amount.negate());
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid amount format: " + stringAmount);
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deposit(String stringAmount) {
        try {
            BigDecimal amount = new BigDecimal(stringAmount);
            if (!isPositive(amount)) {
                throw new IllegalArgumentException("The amount must be positive");
            }
            if (amount.compareTo(new BigDecimal(1000000)) > 0) {
                throw new InsufficientFundsException("The amount cannot be more than 1000000");
            }
            source.updateBalance(amount);
            target.updateBalance(amount);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid amount format: " + stringAmount);
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean transfer(String stringAmount) {
        try {
            BigDecimal amount = new BigDecimal(stringAmount);
            if (source.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds on bank card");
            }
            source.updateBalance(amount.negate());
            target.updateBalance(amount);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid amount format: " + stringAmount);
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean isPositive(BigDecimal value) {
        return value.signum() > 0;
    }
}
