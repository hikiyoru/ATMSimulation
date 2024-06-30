import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Card extends BankEntity {
    private String pin;
    private byte pinAttempt;
    private Date blockingCardUntil;

    private static final byte MAX_PIN_ATTEMPTS = 3;

    Card(String cardNumber, String pin, byte pinAttempt, BigDecimal balance, Date blockingCardUntil) {
        setCardNumber(cardNumber);
        setPin(pin);
        setPinAttempt(pinAttempt);
        setBalance(balance);
        setBlockingCardUntil(blockingCardUntil);
    }
    public String getPin() {
        return pin;
    }
    public byte getPinAttempt() {
        return pinAttempt;
    }
    public void setPin(String pin) {
        if (pin != null && pin.matches("\\d{4}")) {
            this.pin = pin;
        } else {
            throw new IllegalArgumentException("The PIN code must consist of 4 digits");
        }
    }
    public void setPinAttempt(byte pinAttempt) {
        if (pinAttempt >= 0 && pinAttempt <= MAX_PIN_ATTEMPTS) {
            this.pinAttempt = pinAttempt;
        } else {
            throw new IllegalArgumentException("The pin attempts must be more or equal then 0 and less or equal 3");
        }
    }
    public void setBlockingCardUntil(Date blockingCardUntil) {
        this.blockingCardUntil = blockingCardUntil;
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