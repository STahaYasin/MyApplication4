package tahayasin.digitalscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Taha Yasin on 12/10/2017.
 */

public class PlayListObjects implements Serializable{
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("variabele")
    @Expose
    private String variabele;
    @SerializedName("lengte")
    @Expose
    private String lengte;
    @SerializedName("downloads")
    @Expose
    private String downloads;
    @SerializedName("animation")
    @Expose
    private String animation;
    @SerializedName("naam")
    @Expose
    private String naam;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("speed")
    @Expose
    private String speed;

    public String getType(){
        return type;
    }
    public String getVariabele(){
        return variabele;
    }
    public String getLengte(){
        return lengte;
    }
    public String getDownloads(){
        return downloads;
    }
    public String getAnimation(){
        return animation;
    }
    public String getNaam(){
        return naam;
    }
    public String getBackground(){
        return background;
    }
    public String getSpeed(){
        return speed;
    }
}
