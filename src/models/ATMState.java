package models;

public enum ATMState {
    IDLE,
    AWAITING_CARD_PIN,
    AWAITING_ACTION,
    WITHDRAWING,
    DEPOSITING,
    CHECKING_BALANCE,
    EXITING
}
