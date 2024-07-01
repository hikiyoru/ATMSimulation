package models;

import java.util.*;

public class ATM extends BankEntity {
    private ATMState state = ATMState.IDLE;
    private Map<String, Card> cards = new HashMap<>();

    public ATM(String state, String balance, String cardNumber) {
        super(cardNumber, balance);
        setATMState(state);
    }

    public ATMState getATMState() {
        return state;
    }

    public Card getCard(String enteredCard) {
        return cards.get(enteredCard);
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public void setATMState(ATMState state) {
        this.state = state;
    }

    public void setATMState(String status) {
        this.state = ATMState.valueOf(status);
    }

    public void setCards(Map<String, Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.put(card.getCardNumber(), card);
    }

    @Override
    public String toString() {
        return getATMState() + " " + getBalance() + " " + getCardNumber();
    }
}
