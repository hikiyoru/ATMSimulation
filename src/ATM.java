import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM extends BankEntity {
    private ATMState atmState = ATMState.AWAITING_CARDNUMBER;
    private Map<String, Card> cards = new HashMap<>();

    ATM(DataFileManager dataManager) {
        dataManager.read(this);
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
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The amount cannot be less than or equal to 0");
            return false;
        }
        if (getCard(cardNumber).balance.compareTo(amount) < 0) {
            System.out.println("Insufficient funds on bank card");
            return false;
        }
        if (balance.compareTo(amount) < 0) {
            System.out.println("Insufficient funds in ATM");
            return false;
        }
        updateBalance(amount.negate());
        System.out.println("The funds were successfully withdrawn");
        return true;
    }
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The amount cannot be less than or equal to 0");
            return false;
        }
        if (amount.compareTo(new BigDecimal("1000000")) > 0){
            System.out.println("The amount cannot be more than 1000000");
            return false;
        }
        System.out.println("The funds were successfully deposited");
        updateBalance(amount);
        return true;
    }
    @Override
    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
        getCard(cardNumber).updateBalance(amount);
    }
}
