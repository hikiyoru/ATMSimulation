package views;

import models.ATM;

public class ConsoleView {

    public void showInfoMessage(String message) {
        System.out.println(message);
    }

    public void showCurrentCard(ATM atm) {
        System.out.println("Current card: " + atm.getCard().getCardNumber());
    }

    public void showBalance(ATM atm) {
        System.out.println("Current balance: " + atm.getCard().getBalance());
    }

    public void showErrorMessage(String message) {
        System.err.println("[Error] " + message);
    }

    public void showSuccessMessage(String message) {
        System.out.println("[Success] " + message);
    }
}
