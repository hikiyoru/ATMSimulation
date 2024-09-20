package views;

import models.ATM;

import java.math.BigDecimal;

public class ConsoleView {

    public void showInfoMessage(String message) {
        System.out.println(message);
    }

    public void showCurrentCard(String currentCard) {
        System.out.println("Current card: " + currentCard);
    }

    public void showBalance(BigDecimal balance) {
        System.out.println("Current balance: " + balance);
    }

    public void showErrorMessage(String message) {
        System.err.println("[Error] " + message);
    }

    public void showSuccessMessage(String message) {
        System.out.println("[Success] " + message);
    }
}
