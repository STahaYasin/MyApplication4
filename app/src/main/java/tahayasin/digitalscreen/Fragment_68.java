package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Fragment_68 extends Fragment_00 {

    RecyclerView rv;
    TextView tv_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_68, container, false);

        rv = (RecyclerView) v.findViewById(R.id.fragment_68_rv);
        tv_name = (TextView) v.findViewById(R.id.fragment_68_name);

        tv_name.setText("@" + playListObject.getVariabele());

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        downloadUserData();
    }
    private void downloadUserData(){
        final String url = "https://www.instagram.com/" + playListObject.getVariabele() + "/?__a=1";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String res;

                try {
                    res = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    res = "{}";
                }

                final InstaResult instaResult = new Gson().fromJson(res, InstaResult.class);

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gotData(instaResult);
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotData(InstaResult instaResult){
        Node[] nodes = instaResult.user.media.nodes;

        rv.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        rv.setAdapter(new PhotoAdapter(nodes));
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder>{

        private Node[] nodes;

        public PhotoAdapter(Node[] nodes){
            this.nodes = nodes;
        }
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_68_list_item_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(final PhotoHolder holder, int position) {
            final String url = nodes[position].display_src;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap btm = null;

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
                                holder.iv.setImageBitmap(fBtm);
                            }
                        });
                    }
                }
            });
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();
        }

        @Override
        public int getItemCount() {
            return nodes.length;
        }

        public class PhotoHolder extends RecyclerView.ViewHolder{

            ImageView iv;

            public PhotoHolder(View itemView) {
                super(itemView);

                iv = (ImageView) itemView.findViewById(R.id.type_68_image);
            }
        }
    }

    public class InstaResult{
        @SerializedName("user")
        @Expose
        public User user;
    }
    public class User{
        @SerializedName("full_name")
        @Expose
        public String full_name;

        @SerializedName("profile_pic_url")
        @Expose
        public String profile_pic_url;

        @SerializedName("is_private")
        @Expose
        public boolean is_private;

        @SerializedName("media")
        @Expose
        public Media media;
    }
    public class Media{
        @SerializedName("nodes")
        @Expose
        public Node[] nodes;
    }
    public class Node{
        @SerializedName("_typename")
        @Expose
        public String _typename;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("dimentions")
        @Expose
        public Dimentions dimentions;

        public class Dimentions{
            @SerializedName("height")
            @Expose
            public Integer height;

            @SerializedName("width")
            @Expose
            public Integer width;
        }

        @SerializedName("owner")
        @Expose
        public Owner owner;

        public class Owner{
            @SerializedName("id")
            @Expose
            public String id;
        }

        @SerializedName("code")
        @Expose
        public String code;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("is_video")
        @Expose
        public boolean is_video;

        @SerializedName("comments")
        @Expose
        public Comments comments;

        public class Comments{
            @SerializedName("count")
            @Expose
            public Integer count;
        }

        @SerializedName("likes")
        @Expose
        public Likes likes;

        public class Likes{
            @SerializedName("count")
            @Expose
            public Integer count;
        }

        @SerializedName("media_preview")
        @Expose
        public String media_preview;

        @SerializedName("display_src")
        @Expose
        public String display_src;

        @SerializedName("thumbnail_src")
        @Expose
        public String thumbnail_src;
    }
}