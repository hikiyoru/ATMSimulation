package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatHelper {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
    private static final SimpleDateFormat formatter = dateFormat;

    public static Date toDateFormat(String date) throws ParseException { return dateFormat.parse(date); }
    public static String toDateFormat(Date date) {
        return formatter.format(date);
    }
}
