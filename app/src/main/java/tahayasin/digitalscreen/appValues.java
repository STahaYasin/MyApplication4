package tahayasin.digitalscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Taha Yasin on 12/10/2017.
 */

public class appValues {
    public static String prefenceUuid = "shP_UUID";
    public static int refreshTimeMillis = 5000;

    public static String getUUID(@NonNull Context context){
        SharedPreferences settings = context.getSharedPreferences(prefenceUuid, MODE_PRIVATE);
        //return settings.getString(prefenceUuid, "UUID");
        return "7901bb57f7"; //TODO change static id to dynamic
    }
    public static int getDelay(int sec){
        //return sec * 1000;
        return 5000;
    }

    //URL's
    public static String url_api = "https://www.digitalscreen.be/api/";

    public static String url_getTweets = url_api + "gettwittertweets/";

    //Time
    public static Integer defaultTimeInSec = 5;
    public static Integer defaultTimeInMilli = defaultTimeInSec * 1000;
}