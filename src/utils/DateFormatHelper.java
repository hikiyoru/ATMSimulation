package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class DateFormatHelper {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");

    public static Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(Long.MIN_VALUE);
        }
    }

    public static String formatDate(Date date) {
        return dateFormat.format(Objects.requireNonNullElseGet(date, () -> new Date(Long.MIN_VALUE)));
    }
}
