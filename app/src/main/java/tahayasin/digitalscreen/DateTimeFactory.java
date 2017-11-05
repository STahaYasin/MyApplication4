package tahayasin.digitalscreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Taha Yasin on 2/11/2017.
 */

public class DateTimeFactory {

    public static String parseDateToddMMyyyy(String time) {
        //String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String inputPattern = "EEE, dd MMM YYYY H:m:s z";
        //String outputPattern = "dd-MMM-yyyy h:mm a";
        String outputPattern = "d MMM yyyy | HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String parseDateToddMMyyyy(String time, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, new Locale("nl", "NL"));

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}