import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Card extends BankEntity {
    private String pin;
    private byte pinAttempt;
    private Date blockingCardUntil;

    private static final byte MAX_PIN_ATTEMPTS = 3;

    Card(String cardNumber, String pin, byte pinAttempt, BigDecimal balance, Date blockingCardUntil) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.pinAttempt = pinAttempt;
        this.balance = balance;
        this.blockingCardUntil = blockingCardUntil;
    }
    public String getPin() {
        return pin;
    }
    public byte getPinAttempt() {
        return pinAttempt;
    }
    public boolean checkPin(String enteredPin) {
        if (isCardBlocked()) {
           return false;
        }
        if (!pin.equals(enteredPin)) {
            pinAttempt++;
            if (pinAttempt >= MAX_PIN_ATTEMPTS) {
                blockCard();
            }
            return false;
        } else {
            pinAttempt = 0;
            return true;
        }
    }
    public boolean isCardBlocked() {
        Calendar calendar = Calendar.getInstance();
        Date dateNow = new Date();
        calendar.setTime(dateNow);
        return dateNow.before(blockingCardUntil);
    }
    public Date getBlockingCardUntil() {
        return blockingCardUntil;
    }
    private void blockCard() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        blockingCardUntil = date;
        pinAttempt = 0;
    }
    @Override
    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public String toString() {
        return getCardNumber() + " " + getPin() + " " + getPinAttempt() + " " + getBalance() + " " + DateFormatHelper.toDateFormat(getBlockingCardUntil());
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(cardNumber);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Card card = (Card) o;
//        return Objects.equals(cardNumber, card.cardNumber);
//    }
}