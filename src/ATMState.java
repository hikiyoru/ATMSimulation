import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ATMState {
    IDLE,
    AWAITING_CARDPIN,
    AWAITING_ACTION,
    WITHDRAWING,
    DEPOSITING,
    CHECKING_BALANCE;
}
