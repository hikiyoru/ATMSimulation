package models;

import utils.DateFormatHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Card extends BankEntity {
    private String pinCode;
    private byte pinCodeAttempt;
    private Date blockingUntil;

    private static final byte MAX_PIN_ATTEMPTS = 3;

    public Card(String cardNumber, String pin, String pinAttempt, String balance, String blockingCardUntil) {
        super(cardNumber, balance);
        setPinCode(pin);
        setPinCodeAttempt(pinAttempt);
        setBlockingCardUntil(blockingCardUntil);
    }

    public String getPinCode() {
        return pinCode;
    }

    public byte getPinCodeAttempt() {
        return pinCodeAttempt;
    }

    public void setPinCode(String pinCode) {
        if (isPinCode(pinCode)) {
            this.pinCode = pinCode;
        } else {
            throw new IllegalArgumentException("The PIN code must consist of 4 digits");
        }
    }

    public void setPinCodeAttempt(String pinCodeAttempt) {
        try {
            byte attempts = Byte.parseByte(pinCodeAttempt);
            if (attempts >= 0 && attempts <= MAX_PIN_ATTEMPTS) {
                this.pinCodeAttempt = attempts;
            } else {
                throw new IllegalArgumentException("Pin attempts must be between 0 and " + MAX_PIN_ATTEMPTS);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid pin attempts format: " + pinCodeAttempt);
        }
    }

    public void setBlockingCardUntil(String blockingUntil) {
        try {
            this.blockingUntil = DateFormatHelper.toDateFormat(blockingUntil);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + blockingUntil);
        }
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

    public boolean isPinCode(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public boolean isCardBlocked() {
        Calendar calendar = Calendar.getInstance();
        Date dateNow = new Date();
        calendar.setTime(dateNow);
        return dateNow.before(blockingUntil);
    }

    public String getBlockingCardUntil() {
        return DateFormatHelper.toDateFormat(blockingUntil);
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

    public void setBlockingUntil(Date blockingUntil) {
        this.blockingUntil = blockingUntil;
    }

    @Override
    public String toString() {
        return getCardNumber() + " " + getPinCode() + " " + getPinCodeAttempt() + " " + getBalance() + " " + getBlockingCardUntil();
    }
}