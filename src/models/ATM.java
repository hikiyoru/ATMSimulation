package models;

import exceptions.AuthorizationException;
import utils.StringHelper;

public class ATM extends BankEntity {
    private ATMState state = ATMState.IDLE;
    private Card card;
    private Boolean authorized;

    public ATM(String balance, ATMState state, Card card, Boolean authorized) {
        super(balance);
        setCard(card);
        setATMState(state);
        setAuthorized(authorized);
    }

    public ATMState getATMState() {
        return state;
    }

    public Card getCard() {
        return card;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setATMState(ATMState state) {
        if (state == ATMState.IDLE) {
            setCard(null);
            setAuthorized(false);
        }
        else if (state == ATMState.AWAITING_CARD_PIN) {
            setAuthorized(false);
        }
        else {
            setAuthorized(true);
        }
        this.state = state;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public Boolean isAuthorized() {
        return card != null && authorized;
    }

    public boolean tryAuthorize(String pinCode) {
        try {
            if (card == null) {
                setATMState(ATMState.IDLE);
                throw new AuthorizationException("No card inserted");
            }
            if (!StringHelper.isPinCode(pinCode)) {
                throw new AuthorizationException("PIN code must consist of 4 digits");
            }
            if (!card.checkPinCode(pinCode)) {
                throw new AuthorizationException("Invalid PIN code");
            }
            if (card.isCardBlocked()) {
                setATMState(ATMState.IDLE);
                throw new AuthorizationException("The card is blocked due to too many attempts to enter the PIN code");
            }
            setAuthorized(true);
            return true;
        } catch (Exception e) {
            System.err.println("[Error] " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return getBalance() + " " + getATMState() +
                (card != null ? " " + card.getCardNumber() + " " + getAuthorized() : "");
    }
}
