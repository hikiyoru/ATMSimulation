package views;

import models.ATM;
import models.ATMState;
import models.BankEntity;
import models.Card;
import repositories.FileBankEntityRepository;
import services.Transaction;

import java.util.Scanner;

public class ConsoleView {
    private String input = "";
    private final Scanner scanner = new Scanner(System.in);
    private final FileBankEntityRepository repository;
    private final ATM atm;
    private Card card;

    public ConsoleView(ATM atm, FileBankEntityRepository repository) {
        this.repository = repository;
        this.atm = atm;
        this.card = atm.getCard(atm.getCardNumber());
    }

    public void run() {
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
            repository.update(atm);
        }
    }

    private void idle() {
        while (true) {
            System.out.println("ATM waiting for card number (XXXX-XXXX-XXXX-XXXX) or enter 'exit':");
            input = scanner.next();
            if (input.equals("exit")) {
                atm.setATMState(ATMState.EXITING);
                break;
            }
            if (!atm.isStringCardNumber(input)) {
                System.err.println("Invalid card number. Try again");
                continue;
            }
            card = atm.getCard(input);
            if (card == null) {
                System.err.println("Card number not found. Try again");
                continue;
            }
            atm.setATMState(ATMState.AWAITING_CARD_PIN);
            break;
        }
    }

    private void awaitingCardPin() {
        while (true) {
            System.out.println("Entered card: " + atm.getCardNumber() + "\nEnter your PIN code or enter 'back' to remove card:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.IDLE);
                break;
            }
            if (!card.isPinCode(input)) {
                System.err.println("PIN code must consist of 4 digits. Try again");
                continue;
            }
            if (!card.checkPinCode(input)) {
                System.err.println("Invalid PIN code. Try again");
                continue;
            }
            if (card.isCardBlocked()) {
                System.err.println("The card is blocked due to too many attempts to enter the PIN code");
                atm.setATMState(ATMState.IDLE);
                break;
            } else {
                atm.setATMState(ATMState.AWAITING_ACTION);
                break;
            }
        }
    }

    private void awaitingAction() {
        label:
        while (true) {
            System.out.println("Entered card: " + atm.getCardNumber() +
                    "\nChoose an action:\n" +
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
            System.err.println("Incorrect input. Try again");
        }
    }

    private void withdrawing() {
        while (true) {
            System.out.println("Entered card: " + atm.getCardNumber() + "\nEnter amount or enter 'back' to cancel operation:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.AWAITING_CARD_PIN);
                break;
            }
            Transaction<BankEntity> transaction = new Transaction<>(atm, card);
            if (!transaction.withdraw(input)) {
                continue;
            }
            System.out.println("Success");
            break;
        }
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void depositing() {
        while (true) {
            System.out.println("Entered card: " + atm.getCardNumber() + "\nEnter amount or enter 'back' to cancel operation:");
            input = scanner.next();
            if (input.equals("back")) {
                atm.setATMState(ATMState.AWAITING_CARD_PIN);
                break;
            }
            Transaction<BankEntity> transaction = new Transaction<>(atm, card);
            if (!transaction.deposit(input)) {
                continue;
            }
            System.out.println("Success");
            break;
        }
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void checkingBalance() {
        System.out.println("Entered card: " + atm.getCardNumber() +
                           "\nAvailable balance: " + card.getBalance() + "\n");
        atm.setATMState(ATMState.AWAITING_CARD_PIN);
    }

    private void exiting() {
        atm.setATMState(ATMState.IDLE);
        repository.update(atm);
    }
}
