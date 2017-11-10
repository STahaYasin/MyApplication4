package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.zip.Inflater;


public class Fragment_23 extends Fragment_00 {

    String data = null;

    ImageView iv_0;
    ImageView iv_1;
    ImageView iv_2;
    ImageView iv_3;
    ImageView iv_4;

    TextView tv_0_h;
    TextView tv_0_l;
    TextView tv_0_n;
    TextView tv_0_r;

    TextView tv_1_t;
    TextView tv_2_t;
    TextView tv_3_t;
    TextView tv_4_t;

    TextView tv_0_d;
    TextView tv_1_d;
    TextView tv_2_d;
    TextView tv_3_d;
    TextView tv_4_d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //LayoutInflater li = getActivity().getLayoutInflater();
        //View v =inflater.inflate(R.layout.fragment_23, container, false);
        //View v = li.inflate(R.layout.fragment_23, container, false);
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_23, container, false);

        iv_0 = (ImageView) v.findViewById(R.id.fragment_23_00_icon);
        iv_1 = (ImageView) v.findViewById(R.id.fragment_23_01_icon);
        iv_2 = (ImageView) v.findViewById(R.id.fragment_23_02_icon);
        iv_3 = (ImageView) v.findViewById(R.id.fragment_23_03_icon);
        iv_4 = (ImageView) v.findViewById(R.id.fragment_23_04_icon);

        tv_0_h = (TextView) v.findViewById(R.id.fragment_23_00_temphigh);
        tv_0_l = (TextView) v.findViewById(R.id.fragment_23_00_templow);
        tv_0_n = (TextView) v.findViewById(R.id.fragment_23_00_nem);
        tv_0_r = (TextView) v.findViewById(R.id.fragment_23_00_ruzgar);

        tv_1_t = (TextView) v.findViewById(R.id.fragment_23_01_temp);
        tv_2_t = (TextView) v.findViewById(R.id.fragment_23_02_temp);
        tv_3_t = (TextView) v.findViewById(R.id.fragment_23_03_temp);
        tv_4_t = (TextView) v.findViewById(R.id.fragment_23_04_temp);

        tv_0_d = (TextView) v.findViewById(R.id.fragment_23_00_day);
        tv_1_d = (TextView) v.findViewById(R.id.fragment_23_01_day);
        tv_2_d = (TextView) v.findViewById(R.id.fragment_23_02_day);
        tv_3_d = (TextView) v.findViewById(R.id.fragment_23_03_day);
        tv_4_d = (TextView) v.findViewById(R.id.fragment_23_04_day);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();
    }
    private void getData(){
        gotData();

        final String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D\"antwerpen%2C%20ak\")%20and%20u='C'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String data_string;

                try {
                    data_string = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    data_string = "";
                }

                final String fRes = data_string;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(fRes != null && fRes != ""){
                                data = fRes;
                            }
                            gotData();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotData(){
        if(data == null || data == "") return;
        final WheatherObject finalWheatherObject = new Gson().fromJson(data, WheatherObject.class);

        if(finalWheatherObject == null) return;


        Channel.Forecast[] forecasts = finalWheatherObject.query.results.channel.item.forecast;

        setTempValues(forecasts);

        if(tv_0_n != null) tv_0_n.setText(finalWheatherObject.query.results.channel.atmosphere.humidity + "%");
        if(tv_0_r != null) tv_0_r.setText(finalWheatherObject.query.results.channel.wind.direction + "°");

        setDateValues(forecasts);
        setImageValues(forecasts);

    }
    private void setTempValues(Channel.Forecast[] forecasts){
        if(tv_0_h != null) tv_0_h.setText(forecasts[0].high + "°");
        if(tv_0_l != null) tv_0_l.setText(forecasts[0].low + "°");

        if(tv_1_t != null) tv_1_t.setText(forecasts[1].high + "°");
        if(tv_2_t != null) tv_2_t.setText(forecasts[2].high + "°");
        if(tv_3_t != null) tv_3_t.setText(forecasts[3].high + "°");
        if(tv_4_t != null) tv_4_t.setText(forecasts[4].high + "°");
    }
    private void setDateValues(Channel.Forecast[] forecasts){
        if(tv_0_d != null) tv_0_d.setText("VANDAAG " + (DateTimeFactory.parseDateToddMMyyyy(forecasts[0].date, "dd MMM yyyy", "dd  MMM")).toUpperCase());
        if(tv_1_d != null) tv_1_d.setText((DateTimeFactory.parseDateToddMMyyyy(forecasts[1].date, "dd MMM yyyy", "dd  MMM")).toUpperCase());
        if(tv_2_d != null) tv_2_d.setText((DateTimeFactory.parseDateToddMMyyyy(forecasts[2].date, "dd MMM yyyy", "dd  MMM")).toUpperCase());
        if(tv_3_d != null) tv_3_d.setText((DateTimeFactory.parseDateToddMMyyyy(forecasts[3].date, "dd MMM yyyy", "dd  MMM")).toUpperCase());
        if(tv_4_d != null) tv_4_d.setText((DateTimeFactory.parseDateToddMMyyyy(forecasts[4].date, "dd MMM yyyy", "dd  MMM")).toUpperCase());
    }
    private void setImageValues(Channel.Forecast[] forecasts){

        Class res = R.drawable.class;

        try {
            Field field = res.getField("w" + forecasts[0].code);
            int drawableId = field.getInt(null);

            if(iv_0 != null) iv_0.setImageResource(drawableId);
        }
        catch (Exception e) {

        }

        try {
            Field field = res.getField("w" + forecasts[1].code);
            int drawableId = field.getInt(null);

            if(iv_1 != null) iv_1.setImageResource(drawableId);
        }
        catch (Exception e) {

        }

        try {
            Field field = res.getField("w" + forecasts[2].code);
            int drawableId = field.getInt(null);

            if(iv_2 != null) iv_2.setImageResource(drawableId);
        }
        catch (Exception e) {

        }

        try {
            Field field = res.getField("w" + forecasts[3].code);
            int drawableId = field.getInt(null);

            if(iv_3 != null) iv_3.setImageResource(drawableId);
        }
        catch (Exception e) {

        }

        try {
            Field field = res.getField("w" + forecasts[4].code);
            int drawableId = field.getInt(null);

            if(iv_4 != null) iv_4.setImageResource(drawableId);
        }
        catch (Exception e) {

        }
    }


    public class WheatherObject{
        @SerializedName("query")
        @Expose
        public Query query;
    }
    public class Query{
        @SerializedName("created")
        @Expose
        public String created;

        @SerializedName("results")
        @Expose
        public Result results;
    }
    public class Result{
        @SerializedName("channel")
        @Expose
        public Channel channel;
    }
    public class Channel{
        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("link")
        @Expose
        public String link;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("location")
        @Expose
        public Location location;

        @SerializedName("wind")
        @Expose
        public Wind wind;

        @SerializedName("atmosphere")
        @Expose
        public Atmosphere atmosphere;

        @SerializedName("item")
        @Expose
        public Item item;

        public class Location{
            @SerializedName("city")
            @Expose
            public String city;

            @SerializedName("country")
            @Expose
            public String country;

            @SerializedName("region")
            @Expose
            public String region;
        }
        public class Atmosphere{
            @SerializedName("humidity")
            @Expose
            public String humidity;
        }
        public class Wind{
            @SerializedName("direction")
            @Expose
            public String direction;
        }
        public class Item{
            @SerializedName("title")
            @Expose
            public String title;

            @SerializedName("pubDate")
            @Expose
            public String pubDate;

            @SerializedName("condition")
            @Expose
            public Condition condition;

            @SerializedName("forecast")
            @Expose
            public Forecast[] forecast;
        }
        public class Condition{
            @SerializedName("code")
            @Expose
            public String code;

            @SerializedName("date")
            @Expose
            public String date;

            @SerializedName("temp")
            @Expose
            public String temp;

            @SerializedName("text")
            @Expose
            public String text;
        }
        public class Forecast{
            @SerializedName("code")
            @Expose
            public String code;

            @SerializedName("date")
            @Expose
            public String date;

            @SerializedName("day")
            @Expose
            public String day;

            @SerializedName("high")
            @Expose
            public String high;

            @SerializedName("low")
            @Expose
            public String low;

            @SerializedName("text")
            @Expose
            public String text;
        }
    }
}