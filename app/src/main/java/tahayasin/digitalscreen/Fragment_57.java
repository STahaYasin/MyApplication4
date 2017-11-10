package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Fragment_57 extends Fragment_00 {

    TextView tv_type;
    ImageView iv_background;
    ImageView iv_imageData;
    WebView webView;

    Bitmap image = null;
    String data = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_57, container, false);

        tv_type = (TextView) v.findViewById(R.id.fragment_x_type);
        iv_background = (ImageView) v.findViewById(R.id.background);
        iv_imageData = (ImageView) v.findViewById(R.id.iv_imageData);
        webView = (WebView)v.findViewById(R.id.fragment_57_webview);

        webView.setBackgroundColor(0x00000000);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_type.setText(playListObject.getNaam());

        //downloadBackGround();
        downloadImageData();
    }
    private void downloadBackGround(){

        final String region = (playListObject.getVariabele() != null) ? playListObject.getVariabele() : "vlaanderen";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://www.filebeeld.be/traffic/image?format=LARGE&region=" + region + "&colorblind=false&width=1920&height=1080&aspectratio=16:9";
                Bitmap btm = null;

                try{
                    btm = new ImageDownloader(url).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap finalbtm = btm;

                if(getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO vind gvd waar je de background moet halen
                        //iv_background.setImageBitmap(finalbtm);
                    }
                });
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    /* An instance of this class will be registered as a JavaScript interface */
    class MyJavaScriptInterface
    {
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            // process the html as needed by the app
        }
    }

    private void downloadData(){
        gotList();

        final String region = (playListObject.getVariabele() != null) ? playListObject.getVariabele() : "vlaanderen";

        final WebView _webView = webView;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String a;

                try{
                    a = new GetRequestString("http://www.filebeeld.be/mobiel/kaart?region=" + region).execute().get(); // TODO region
                }
                catch (Exception e){
                    a = "";
                }

                final String domString = a;

                try {
                    Document htmlDocument = Jsoup.parse(a);
                    //Document htmlDocument = Jsoup.connect("http://darebee.com/").get(); // TODO original code
                    Element element = htmlDocument.select("table").first().parent().parent();

                    // replace body with selected element
                    htmlDocument.body().empty().append(element.toString());
                    String html = htmlDocument.toString();

                    html = html.replace("<!-- header.vm -->", "<base href=\"http://www.filebeeld.be/\"/>");
                    html = html.replace("class=", "style=\"color:#fff;\" class=");

                    final String fHtml = html;


                    if(getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(fHtml != null && fHtml != ""){
                                    data = fHtml;
                                }
                                gotList();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    private void gotList(){
        if(data == null) return;

        webView.loadData(data, "text/html", "UTF-8");
    }
    private void downloadImageData(){
        gotImage();
        final String region = (playListObject.getVariabele() != null) ? playListObject.getVariabele() : "vlaanderen";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO change regio
                String url = "http://www.filebeeld.be/traffic/image?format=LARGE&region=" + region + "&colorblind=false&width=1920&height=1080&aspectratio=16:9";
                Bitmap btm = null;

                try{
                    btm = new ImageDownloader(url).execute().get();
                }
                catch (Exception e){
                    btm = null;
                }

                final Bitmap finalbtm = btm;

                if(getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalbtm != null){
                            image = finalbtm;
                        }
                        gotImage();
                    }
                });
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void gotImage(){
        if(image == null) return;

        iv_imageData.setImageBitmap(image);
        downloadData();
    }
}