package tahayasin.digitalscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    FBRes fbRes;
    FBObject[] fbObjects;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_24, container, false);

        rv = (RecyclerView) v.findViewById(R.id.fragment_24_rv);
        tv_name = (TextView) v.findViewById(R.id.fragment_24_name);
        tv_name.setText(playListObject.getNaam());

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFacebookMessages();

    }
    private void getFacebookMessages(){
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
                            gotFacebookMessages(fres);
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotFacebookMessages(String a){
        fbRes = new Gson().fromJson(a, FBRes.class);

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
            holder.fb_message.setText(fbObjects[position].message);
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