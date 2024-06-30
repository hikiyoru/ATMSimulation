import java.math.BigDecimal;
import java.util.*;

public class ATM extends BankEntity {
    private ATMState atmState = ATMState.AWAITING_CARDNUMBER;
    private final Map<String, Card> cards = new HashMap<>();
    private final DataFileManager dataFileManager;

    ATM(DataFileManager dataFileManager)
    {
        this.dataFileManager = dataFileManager;
        dataFileManager.read(this);
    }
    public ATMState getATMState() {
        return atmState;
    }
    public Card getCard(String enteredCard) {
        return cards.get(enteredCard);
    }
    public List<String> getAllCardsToString() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Card> entry : cards.entrySet()) {
            String key = entry.getKey();
            Card card = entry.getValue();
            list.add(card.toString());

        }
        return list;
    }
    public void setATMState(String status) {
        this.atmState = ATMState.valueOf(status);
    }
    public void addCard(Card card) {
        cards.put(card.getCardNumber(), card);
    }
    public boolean withdraw(BigDecimal amount) {
        try {
            if (!isPositive(amount)) {
                throw new IllegalArgumentException("The amount must be positive");
            }
            BigDecimal cardBalance = getCard(cardNumber).balance;
            if (cardBalance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds on bank card");
            }
            if (balance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds in ATM");
            }
            updateBalance(amount.negate());
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    public boolean deposit(BigDecimal amount) {
        try {
            if (!isPositive(amount)) {
                throw new IllegalArgumentException("The amount must be positive");
            }
            if (amount.compareTo(new BigDecimal(1000000)) > 0) {
                throw new InsufficientFundsException("The amount cannot be more than 1000000");
            }
            updateBalance(amount);
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    @Override
    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
        getCard(cardNumber).updateBalance(amount);
        dataFileManager.write(this);
    }
}
