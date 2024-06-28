import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ATM {
    private ATMState atmState = ATMState.AWAITING_CARDNUMBER;
    private BigDecimal balance = BigDecimal.valueOf(0);
    private String currentCard;
    private Map<String, Card> cards = new HashMap<>();

    ATM(DataFileManager dataManager) {
        dataManager.read(this);
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public ATMState getATMState() {
        return atmState;
    }
    public String getCurrentCard() {
        return currentCard;
    }
    public Card checkCard(String enteredCard) {
        return cards.get(enteredCard);
    }

    public void changeBalance(BigDecimal ammount) {
        balance = balance.add(ammount);
    }

    public void setATMState(String status) {
        this.atmState = ATMState.valueOf(status);
    }
    public void setCurrentCard(String currentCard) {
        this.currentCard = currentCard;
    }
    public void addCard(Card card) {
        cards.put(card.getCardNumber(), card);
    }
}
