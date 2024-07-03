package models;

public enum ATMState {
    IDLE,
    AWAITING_CARD_PIN,
    AWAITING_ACTION,
    WITHDRAWING,
    DEPOSITING,
    CHECKING_BALANCE,
    EXITING;

    public static ATMState fromString(String string) {
        try {
            return ATMState.valueOf(string);
        } catch (IllegalArgumentException e) {
            return ATMState.IDLE;
        }
    }
}
