import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Card {
    private String cardNumber;
    private String pin;
    private byte pinAttempt;
    private BigDecimal cardBalance;
    private Date blockingCardUntil;

    Card(String cardNumber, String pin, byte pinAttempt, BigDecimal balance, Date blockingCardUntil) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.pinAttempt = pinAttempt;
        this.cardBalance = balance;
        this.blockingCardUntil = blockingCardUntil;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean checkPin(String enteredPin) {
        if (!pin.equals(enteredPin)) {
            pinAttempt++;
            if (pinAttempt == 3) {
                blockCard();
            }
            return false;
        } else {
            pinAttempt = 0;
            return true;
        }
    }

    public boolean checkBlock() {
        Calendar calendar = Calendar.getInstance();
        Date dateNow = new Date();
        calendar.setTime(dateNow);
        return dateNow.before(blockingCardUntil);
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public Date getBlockingCardUntil() {
        return blockingCardUntil;
    }

    public void changeBalance(BigDecimal ammount) {
        cardBalance = cardBalance.add(ammount);
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

    public void showCard() {
        System.out.printf("%s | %s | %s | %f | %s\n", cardNumber, pin, pinAttempt, cardBalance, blockingCardUntil);
    }
}