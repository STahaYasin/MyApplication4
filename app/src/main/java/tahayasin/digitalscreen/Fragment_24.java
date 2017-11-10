package tahayasin.digitalscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.logging.XMLFormatter;


public class Fragment_24 extends Fragment_00 {

    final Context context = getContext();
    RecyclerView rv;
    TextView tv_name;

    ImageView iv_qr;
    ImageView iv_pr;

    String data;
    FBRes fbRes;
    FBObject[] fbObjects;
    Bitmap btm_profile = null;
    Bitmap btm_qr = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //LayoutInflater li = getActivity().getLayoutInflater();
        //View v = inflater.inflate(R.layout.fragment_24, container, false);
        //View v = li.inflate(R.layout.fragment_24, container, false);
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_24, container, false);

        rv = (RecyclerView) v.findViewById(R.id.fragment_24_rv);
        tv_name = (TextView) v.findViewById(R.id.fragment_24_name);
        tv_name.setText(playListObject.getNaam());
        iv_qr = (ImageView) v.findViewById(R.id.fragment_24_qr);
        getQR();

        iv_pr = (ImageView) v.findViewById(R.id.fragment_24_profile);
        getProfile();

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFacebookMessages();

    }
    private void getQR(){
        setQR();
        final String url = "https://chart.googleapis.com/chart?cht=qr&chs=400x400&chl=http://www.facebook.com/" + playListObject.getVariabele();// + "&chld=H|0";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap btm;

                try {
                    btm = new ImageDownloader(url).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap fBtm = btm;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(fBtm != null) btm_qr = fBtm;
                            setQR();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void setQR(){
        iv_qr.setImageBitmap(btm_qr);
    }
    private void getProfile(){
        setProfile();
        final String url = "http://graph.facebook.com/" + playListObject.getVariabele() + "/picture?type=large";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap btm;

                try {
                    btm = new ImageDownloader(url).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap fBtm = btm;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(fBtm != null){
                                if(fBtm != null) btm_profile = fBtm;
                                setProfile();
                            }
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void setProfile(){
        if(btm_profile == null) return;
        iv_pr.setImageBitmap(btm_profile);
    }
    private void getFacebookMessages(){
        gotFacebookMessages();
        final String url = "https://www.digitalscreen.be/fbdata.php?var=" + playListObject.getVariabele();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String res;

                try {
                    res = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    res = "";
                }
                final String fres = res;

                if(getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(fres != null) data = fres;
                            gotFacebookMessages();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotFacebookMessages(){
        if(data == null || data == "") return;

        fbRes = new Gson().fromJson(data, FBRes.class);

        if(fbRes == null) return;

        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new FBObjectAdapter(fbRes.post_info));
    }

    public class FBRes{
        @SerializedName("post_info")
        @Expose
        public FBObject[] post_info;
    }
    public class FBObject{
        @SerializedName("message")
        @Expose
        public String message;
    }
    public class FBObjectAdapter extends RecyclerView.Adapter<Fragment_24.FBObjectAdapter.ItemHolder>{

        FBObject[] fbObjects;

        public FBObjectAdapter(FBObject[] a){
            fbObjects = a;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_24_list_item_face, parent, false));
        }
        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.fb_message.setText(Html.fromHtml(fbObjects[position].message));
        }

        @Override
        public int getItemCount() {
            return fbObjects.length;
        }


        public class ItemHolder extends RecyclerView.ViewHolder{

            TextView fb_message;

            public ItemHolder(View itemView) {
                super(itemView);

                fb_message = (TextView) itemView.findViewById(R.id.type_24_list_item_fb_message);
            }
        }
    }
}