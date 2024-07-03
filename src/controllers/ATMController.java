package controllers;

import models.ATM;
import models.ATMState;
import models.BankEntity;
import models.Card;
import repositories.FileBankEntityRepository;
import services.Transaction;
import utils.StringHelper;
import views.ConsoleView;

import java.util.Map;
import java.util.Scanner;

public class ATMController {
    String input;
    Scanner scanner = new Scanner(System.in);
    FileBankEntityRepository repository;
    ConsoleView consoleView;
    ATM atm;
    Map<String, Card> cards;

    public ATMController(FileBankEntityRepository repository, ConsoleView consoleView) {
        this.repository = repository;
        this.consoleView = consoleView;
        this.atm = repository.getATM();
        this.cards = repository.getCards();
    }

    public void run() {
        consoleView.showInfoMessage("Welcome");
        while (true) {
            switch (atm.getATMState()) {
                case IDLE:
                    idle();
                    break;
                case AWAITING_CARD_PIN:
                    awaitingCardPin();
                    break;
                case AWAITING_ACTION:
                    awaitingAction();
                    break;
                case WITHDRAWING:
                    withdrawing();
                    break;
                case DEPOSITING:
                    depositing();
                    break;
                case CHECKING_BALANCE:
                    checkingBalance();
                    break;
                case EXITING:
                    exiting();
                    return;
            }
            repository.saveAll();
        }
    }

    private void idle() {
        while(true) {
            consoleView.showInfoMessage("ATM waiting for card number (XXXX-XXXX-XXXX-XXXX) or enter 'exit':");
            input = scanner.next();
            if (input.equals("exit")) {
                atm.setATMState(ATMState.EXITING);
                break;
            }
            if (!StringHelper.isStringCardNumber(input)) {
                consoleView.showErrorMessage("Invalid card number. Try again");
                continue;
            }
            Card card = cards.get(input);
            if (card == null) {
                consoleView.showErrorMessage("Card number not found. Try again");
                continue;
            }
            consoleView.showSuccessMessage("A card with this number was found");
            atm.setCard(card);
            atm.setATMState(ATMState.AWAITING_CARD_PIN);
            break;
        }
    }

    private void awaitingCardPin() {
        while(true) {
            if (atm.getCard() == null) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            consoleView.showCurrentCard(atm);
            consoleView.showInfoMessage("Enter your PIN code or enter 'back' to remove card:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            if (atm.tryAuthorize(input)) {
                consoleView.showSuccessMessage("Authorized");
                atm.setATMState(ATMState.AWAITING_ACTION);
                break;
            }
        }
    }

    private void awaitingAction() {
        label:
        while (true) {
            if (!atm.isAuthorized()) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            consoleView.showCurrentCard(atm);
            consoleView.showInfoMessage("Choose an action:\n" +
                                    "1. Withdraw\n" +
                                    "2. Deposit\n" +
                                    "3. Check balance\n" +
                                    "4. Remove card\n");
            input = scanner.next();
            switch (input) {
                case "1":
                    atm.setATMState(ATMState.WITHDRAWING);
                    break label;
                case "2":
                    atm.setATMState(ATMState.DEPOSITING);
                    break label;
                case "3":
                    atm.setATMState(ATMState.CHECKING_BALANCE);
                    break label;
                case "4":
                    atm.setATMState(ATMState.IDLE);
                    break label;
            }
            consoleView.showErrorMessage("Incorrect input. Try again");
        }
    }

    private void withdrawing() {
        while (true) {
            if (!atm.isAuthorized()) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            consoleView.showInfoMessage("Enter amount or enter 'back' to cancel operation:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.AWAITING_CARD_PIN);
                break;
            }
            Transaction<BankEntity> transaction = new Transaction<>(atm, atm.getCard());
            if (!transaction.withdraw(input)) {
                continue;
            }
            consoleView.showSuccessMessage("Money has been withdrawn");
            break;
        }
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void depositing() {
        while (true) {
            if (!atm.isAuthorized()) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            consoleView.showInfoMessage("Enter amount or enter 'back' to cancel operation:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.AWAITING_CARD_PIN);
                break;
            }
            Transaction<BankEntity> transaction = new Transaction<>(atm, atm.getCard());
            if (!transaction.deposit(input)) {
                continue;
            }
            consoleView.showSuccessMessage("Money has been deposit");
            break;
        }
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void checkingBalance() {
        if (!atm.isAuthorized()) {
            atm.setATMState(ATMState.IDLE);
        }
        consoleView.showBalance(atm);
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void exiting() {
        consoleView.showInfoMessage("Goodbye");
        atm.setATMState(ATMState.IDLE);
        repository.saveAll();
    }
}
