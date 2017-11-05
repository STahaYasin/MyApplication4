package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.XML;


public class Fragment_35 extends Fragment_00 {

    RecyclerView rv;
    TextView tv_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_35, container, false);

        rv = (RecyclerView) v.findViewById(R.id.fragment_36_rv);
        tv_date = (TextView) v.findViewById(R.id.fragment_36_date);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();
    }
    private void getData(){
        final String url = "http://www.demorgen.be/nieuws/rss.xml";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                String a;

                try {
                    a = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    a = ""; // TODO
                }

                final String b = a;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gotData(b);
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotData(String data){
        String jsonData;

        try {
            jsonData = XML.toJSONObject(data).toString();
        }
        catch (Exception e){
            jsonData = ""; //TODO
        }

        myextreemfuckeddata _myextreemfuckeddata = new Gson().fromJson(jsonData, myextreemfuckeddata.class);

        if(_myextreemfuckeddata == null || _myextreemfuckeddata.rss == null || _myextreemfuckeddata.rss.channel == null) return;

        //tv_date.setText(_myextreemfuckeddata.rss.channel.pubDate);
        tv_date.setText(DateTimeFactory.parseDateToddMMyyyy(_myextreemfuckeddata.rss.channel.pubDate, "EEE, dd MMM YYYY H:m:s z", "d MMM yyyy | HH:mm"));

        Item[] items = new Item[3];

        for(Integer i = 0; i < 3; i ++){
            if(i < _myextreemfuckeddata.rss.channel.item.length){
                items[i] = _myextreemfuckeddata.rss.channel.item[i];
            }
        }

        rv.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        rv.setAdapter(new ItemAdapter(items));


    }
    public class myextreemfuckeddata{
        @SerializedName("rss")
        @Expose
        public Rss rss;


    }
    public class Rss{
        @SerializedName("version")
        @Expose
        public Integer version;

        @SerializedName("channel")
        @Expose
        public Channel channel;
    }
    public class Channel{
        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("pubDate")
        @Expose
        public String pubDate;

        @SerializedName("link")
        @Expose
        public String link;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("item")
        @Expose
        public Item[] item;
    }
    public class Item{
        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("pubDate")
        @Expose
        public String pubDate;

        @SerializedName("enclosure")
        @Expose
        public Enclosure enclosure;
    }
    public class Enclosure{
        @SerializedName("url")
        @Expose
        public String url;

        @SerializedName("type")
        @Expose
        public String type;
    }
    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{

        private Item[] items;

        public ItemAdapter(Item[] _items){
            items = _items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_35_list_item_news, parent, false));
        }


        @Override
        public void onBindViewHolder(final ItemHolder holder, int position) {
            if(holder.tv_title != null)
                holder.tv_title.setText(items[position].title);

            if(holder.tv_description != null)
                holder.tv_description.setText(Html.fromHtml(items[position].description));

            if(holder.iv_image != null){
                if(items[position].enclosure != null && items[position].enclosure.url != ""){
                    final String _url = items[position].enclosure.url;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap btm;

                            try {
                                btm = new ImageDownloader(_url).execute().get();
                            }
                            catch (Exception e){
                                btm = null;
                            }

                            final Bitmap fbtm = btm;

                            if(getActivity() != null){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(holder.iv_image != null)
                                        holder.iv_image.setImageBitmap(fbtm);
                                    }
                                });
                            }
                        }
                    });
                    t.setPriority(Thread.MAX_PRIORITY);
                    t.start();
                }
            }
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        public class ItemHolder extends RecyclerView.ViewHolder{

            TextView tv_title;
            TextView tv_description;
            ImageView iv_image;

            public ItemHolder(View itemView) {
                super(itemView);

                tv_title = (TextView) itemView.findViewById(R.id.type_36_title);
                tv_description = (TextView) itemView.findViewById(R.id.type_36_description);
                iv_image = (ImageView) itemView.findViewById(R.id.type_36_image);
            }
        }
    }
}