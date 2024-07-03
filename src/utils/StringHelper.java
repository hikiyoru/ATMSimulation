package utils;

public class StringHelper {
    public static boolean isStringCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        return cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    }

    public static boolean isPinCode(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }
}
