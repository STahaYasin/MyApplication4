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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Fragment_20 extends Fragment_00 {


    RecyclerView rv;
    ImageView iv_profile;
    ImageView iv_qr;
    TextView tv_type;

    private ObjectTweet[] tweets;
    private ObjectTweet.User user;

    String twitterMessages = null;
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
        View v =inflater.inflate(R.layout.fragment_20, container, false);

        rv = (RecyclerView) v.findViewById(R.id.fragment_20_rv);
        iv_profile = (ImageView) v.findViewById(R.id.fragment_20_profile);
        iv_qr = (ImageView) v.findViewById(R.id.fragment_20_qr);
        tv_type = (TextView) v.findViewById(R.id.fragment_20_name);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_type.setText(playListObject.getNaam());
        checkTwitterAcc();

    }
    private void checkTwitterAcc(){
        if(playListObject.getVariabele() != null && playListObject.getVariabele() != ""){
            getTweets();
        }
        else{
            Toast.makeText(context, "no twitter account found", Toast.LENGTH_SHORT).show();
        }
    }
    private void getTweets(){
        useData();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                String aa;
                String url = appValues.url_getTweets + "@" + playListObject.getVariabele() + "/";
                try{
                    aa = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    aa = "";
                }

                final String finalStringResult = aa;

                if(getActivity() != null && finalStringResult != ""){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            twitterMessages = finalStringResult;
                            useData();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void useData(){
        if(twitterMessages == null || twitterMessages == "") return;

        tweets = new Gson().fromJson(twitterMessages, ObjectTweet[].class);

        if(tweets != null){
            user = (tweets[0] != null) ? tweets[0].getUser() : null;
            user.profile_image_url = user.profile_image_url.replace("_normal", "");

            setUser();
            setRV();
        }
    }
    private void setUser(){
        tv_type.setText("@" + user.name.toUpperCase());

        setProfilePic();
        setQRImage();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap btm;

                try {
                    btm = new ImageDownloader(user.profile_image_url).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap finalbtm = btm;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(finalbtm != null) btm_profile = finalbtm;
                            setProfilePic();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        final Context _context = context;

        Thread q = new Thread(new Runnable() {
            @Override
            public void run() {



                String qrurl = "https://chart.googleapis.com/chart?cht=qr&chs=400x400&chl=http://www.twitter.com/" + playListObject.getVariabele() + "&chld=H|0'";

                Bitmap btm;

                try {
                    btm = new ImageDownloader(qrurl).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap finalbtm = btm;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(finalbtm != null) btm_qr = finalbtm;
                            setQRImage();
                        }
                    });
                }
            }
        });
        q.setPriority(Thread.MAX_PRIORITY);
        q.start();
    }
    private void setProfilePic(){
        if(btm_profile != null)
            iv_profile.setImageBitmap(btm_profile);
    }
    private void setQRImage(){
        if(btm_qr != null)
            iv_qr.setImageBitmap(btm_qr);
    }
    private void setRV(){
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new TweetAdapter(tweets));
        rv.post(new Runnable() {
            @Override
            public void run() {
                rv.smoothScrollToPosition(rv.getAdapter().getItemCount() - 1);
            }
        });
    }

    public class ObjectTweet{

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("created_at")
        @Expose
        private String created_at;

        public String getText(){
            return text;
        }
        public User getUser() {return user; }
        public String getCreated_at(){return created_at; }


        public class User{
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("profile_image_url")
            @Expose
            public String profile_image_url;
        }
    }
    public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ItemHolder>{

        private ObjectTweet[] tweets;

        public TweetAdapter(ObjectTweet[] _tweets){
            tweets = _tweets;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_20_list_item_tweet, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            ObjectTweet tweet = tweets[position];

            if(holder.tv_text != null){
                String text = tweet.getText();

                ArrayList<Integer> starts = new ArrayList<>();
                ArrayList<Integer> stops = new ArrayList<>();

                for(Integer i = 0; i < text.length(); i ++){
                    if(text.charAt(i) == ("#").toCharArray()[0]){
                        starts.add(i);
                        stops.add((text.indexOf(" ", i) == -1) ? text.length() - 1 : text.indexOf(" ", i));
                    }
                }

                SpannableStringBuilder sp = new SpannableStringBuilder(text);

                if(stops.size() < starts.size()) stops.add(text.length() - 1);

                for(Integer i = 0; i < starts.size(); i ++){
                    sp.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.type_20_title)), starts.get(i), stops.get(i), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), starts.get(i), stops.get(i), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }

                holder.tv_text.setText(sp, TextView.BufferType.SPANNABLE);
            }
            if(holder.tv_username != null)holder.tv_username.setText("@" + tweet.getUser().name + " at: " + DateTimeFactory.parseDateToddMMyyyy(tweet.getCreated_at(), "EEE MMM dd HH:mm:ss Z yyyy", "EEE, d MMM yyyy HH:mm"));
        }

        @Override
        public int getItemCount() {
            return (tweets != null)? tweets.length : 0;
        }

        public class ItemHolder extends RecyclerView.ViewHolder{

            TextView tv_text;
            TextView tv_username;

            public ItemHolder(View itemView) {
                super(itemView);

                tv_text = (TextView) itemView.findViewById(R.id.type_20_list_item_tweet_text);
                tv_username = (TextView) itemView.findViewById(R.id.type_20_list_item_tweet_username);
            }
        }
    }
}