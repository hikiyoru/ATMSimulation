package models;

import utils.DateFormatHelper;
import utils.StringHelper;

import java.util.Calendar;
import java.util.Date;

public class Card extends BankEntity {
    private String cardNumber;
    private String pinCode;
    private byte pinCodeAttempt;
    private Date blockingUntil;

    private static final byte MAX_PIN_ATTEMPTS = 3;

    public Card(String cardNumber, String pin, byte pinAttempt, String balance, Date blockingUntil) {
        super(balance);
        setCardNumber(cardNumber);
        setPinCode(pin);
        setPinCodeAttempt(pinAttempt);
        setBlockingUntil(blockingUntil);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public byte getPinCodeAttempt() {
        return pinCodeAttempt;
    }

    public String getBlockingCardUntil() {
        return DateFormatHelper.formatDate(blockingUntil);
    }

    public void setCardNumber(String cardNumber) {
        if (StringHelper.isStringCardNumber(cardNumber)) {
            this.cardNumber = cardNumber;
        } else {
            throw new IllegalArgumentException("Incorrect card number");
        }
    }
    public void setPinCode(String pinCode) {
        if (StringHelper.isPinCode(pinCode)) {
            this.pinCode = pinCode;
        } else {
            throw new IllegalArgumentException("The PIN code must consist of 4 digits");
        }
    }

    public void setPinCodeAttempt(byte pinCodeAttempt) {
        if (pinCodeAttempt >= 0 && pinCodeAttempt <= MAX_PIN_ATTEMPTS) {
            this.pinCodeAttempt = pinCodeAttempt;
        } else {
            throw new IllegalArgumentException("Pin attempts must be between 0 and " + MAX_PIN_ATTEMPTS);
        }
    }

    public void setBlockingUntil(Date blockingUntil) {
        this.blockingUntil = blockingUntil;
    }

    public boolean checkPinCode(String pinCode) {
        if (!this.pinCode.equals(pinCode)) {
            pinCodeAttempt++;
            if (pinCodeAttempt >= MAX_PIN_ATTEMPTS) {
                pinCodeAttempt = 0;
                blockCard();
            }
            return false;
        } else {
            pinCodeAttempt = 0;
            return true;
        }
    }

    public boolean isCardBlocked() {
        Calendar calendar = Calendar.getInstance();
        Date dateNow = new Date();
        calendar.setTime(dateNow);
        return dateNow.before(blockingUntil);
    }

    private void blockCard() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        blockingUntil = date;
        pinCodeAttempt = 0;
        System.err.println("The card is blocked due to too many attempts to enter the PIN code");
    }

    @Override
    public String toString() {
        return getCardNumber() + " " + getPinCode() + " " + getPinCodeAttempt() + " " + getBalance() + " " + getBlockingCardUntil();
    }
}