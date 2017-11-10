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
    public static int refreshTimeMillis = 2000;

    public static String getUUIDFroSharedPrefences(@NonNull Context context){
        SharedPreferences settings = context.getSharedPreferences(prefenceUuid, MODE_PRIVATE);
        //return settings.getString(prefenceUuid, "UUID"); //TODO change static id to dynamic
        return "7901bb57f7"; // TEKNOZA
        //return "4cf73014ae"; //TAHA
        //return "2c32f3df28"; //FAT
    }
    public static int getDelayInMillis(int sec){
        //return sec * 1000;
        return 5000;
    }

    //URL's
    public static String url_api = "https://www.digitalscreen.be/api/";

    public static String url_getTweets = url_api + "gettwittertweets/";

    //Time
    public static Integer defaultTimeInSec = 5;
    public static Integer defaultTimeInMilli = defaultTimeInSec * 1000;

    public static String makeName(String z){

        String a = z;

        String b = "-";

        a = a.replace("/", b);
        a = a.replace(":", b);
        a = a.replace("&", b);
        a = a.replace("=", b);
        a = a.replace("@", b);
        a = a.replace(".", b);

        a = ("file" + b + a);

        return a.toString();
    }
}
