package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Fragment_51 extends Fragment_00 {

    ImageView iv_background;
    WebView wv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_51, container, false);

        iv_background = (ImageView) v.findViewById(R.id.fragment_0_background);
        wv = (WebView) v.findViewById(R.id.fragment_51_wv);
        wv.setBackgroundColor(0x00000000);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        downloadData();
    }
    private void downloadData(){
        final String contentUrl = "https://kinepolis.be/nl/vandaag";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String res;

                try {
                    res = new GetRequestString(contentUrl).execute().get();
                }
                catch (Exception e){
                    res = "";
                }

                final String fRes = res;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            useData(fRes);
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    void useData(String data){
        try {
            Document htmlDocument = Jsoup.parse(data);
            //Document htmlDocument = Jsoup.connect("http://darebee.com/").get(); // TODO original code
            Element element = htmlDocument.select("#kinepolis-movie-overview-movies").first();

            // replace body with selected element
            htmlDocument.body().empty().append(element.toString());
            String html = htmlDocument.toString();

            html = html.replace("<meta charset=\"utf-8\">", "<meta charset=\"utf-8\"><base href=\"https://www.kinepolis.be\"/>");
            html = html.replace("data-src", "src");

            final String fHtml = html;


            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadData(fHtml, "text/html", "UTF-8");
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}