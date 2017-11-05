package tahayasin.digitalscreen;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Taha Yasin on 15/10/2017.
 */

public class FragmentFactory {
    public static Fragment makeFactoryFromType(Context context, PlayListObjects playListObject){

        String type = playListObject.getType();

        Fragment fragment = null;

        fragment = new Fragment_X();

        switch (type){
            case "0": fragment = new Fragment_X();  break;
            case "1": fragment = new Fragment_X();  break;
            case "2": fragment = new Fragment_2();  break; // Photo
            case "3": fragment = new Fragment_X();  break;
            case "4": fragment = new Fragment_X();  break;
            case "5": fragment = new Fragment_X();  break;
            case "6": fragment = new Fragment_X();  break;
            case "7": fragment = new Fragment_X();  break;
            case "8": fragment = new Fragment_X();  break;
            case "9": fragment = new Fragment_X();  break;

            case "10": fragment = new Fragment_X();  break;
            case "11": fragment = new Fragment_X();  break;
            case "12": fragment = new Fragment_X();  break;
            case "13": fragment = new Fragment_X();  break;
            case "14": fragment = new Fragment_X();  break;
            case "15": fragment = new Fragment_X();  break;
            case "16": fragment = new Fragment_X();  break;
            case "17": fragment = new Fragment_X();  break;
            case "18": fragment = new Fragment_X();  break;
            case "19": fragment = new Fragment_X();  break;

            case "20": fragment = new Fragment_20();  break; //Twitter tweets
            case "21": fragment = new Fragment_X();  break;
            case "22": fragment = new Fragment_X();  break;
            case "23": fragment = new Fragment_23();  break; // WEER
            case "24": fragment = new Fragment_24();  break; // Facebook likes
            case "25": fragment = new Fragment_X();  break;
            case "26": fragment = new Fragment_X();  break;
            case "27": fragment = new Fragment_X();  break;
            case "28": fragment = new Fragment_X();  break;
            case "29": fragment = new Fragment_X();  break;

            case "30": fragment = new Fragment_X();  break;
            case "31": fragment = new Fragment_X();  break;
            case "32": fragment = new Fragment_X();  break;
            case "33": fragment = new Fragment_X();  break;
            case "34": fragment = new Fragment_X();  break;
            case "35": fragment = new Fragment_35();  break; // De morgen
            case "36": fragment = new Fragment_36();  break; // De standaard
            case "37": fragment = new Fragment_X();  break;
            case "38": fragment = new Fragment_X();  break;
            case "39": fragment = new Fragment_X();  break;

            case "40": fragment = new Fragment_X();  break;
            case "41": fragment = new Fragment_X();  break;
            case "42": fragment = new Fragment_X();  break;
            case "43": fragment = new Fragment_X();  break;
            case "44": fragment = new Fragment_X();  break;
            case "45": fragment = new Fragment_X();  break;
            case "46": fragment = new Fragment_X();  break;
            case "47": fragment = new Fragment_X();  break;
            case "48": fragment = new Fragment_X();  break;
            case "49": fragment = new Fragment_X();  break;

            case "50": fragment = new Fragment_X();  break;
            case "51": fragment = new Fragment_51();  break; // Kinepolis
            case "52": fragment = new Fragment_X();  break;
            case "53": fragment = new Fragment_X();  break;
            case "54": fragment = new Fragment_X();  break;
            case "55": fragment = new Fragment_55();  break; // Wifi
            case "56": fragment = new Fragment_X();  break;
            case "57": fragment = new Fragment_57();  break; // Verkeer
            case "58": fragment = new Fragment_X();  break;
            case "59": fragment = new Fragment_X();  break;

            case "60": fragment = new Fragment_X();  break;
            case "61": fragment = new Fragment_X();  break;
            case "62": fragment = new Fragment_X();  break;
            case "63": fragment = new Fragment_X();  break;
            case "64": fragment = new Fragment_X();  break;
            case "65": fragment = new Fragment_X();  break;
            case "66": fragment = new Fragment_X();  break;
            case "67": fragment = new Fragment_X();  break;
            case "68": fragment = new Fragment_68();  break; // Instagram
            case "69": fragment = new Fragment_X();  break;

            //TODO add up to 63 items
        }

        if(fragment != null)
            ((Fragment_00) fragment).addData(context, playListObject);

        return fragment;
    }
}