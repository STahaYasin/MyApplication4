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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Fragment_57 extends Fragment_00 {

    TextView tv_type;
    ImageView iv_background;
    ImageView iv_imageData;
    WebView webView;

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
        webView = (WebView)v.findViewById(R.id.webview);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_type.setText(playListObject.getNaam());

        downloadBackGround();
        downloadImageData();
    }
    private void downloadBackGround(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO change regio
                String url = "http://www.filebeeld.be/traffic/image?format=LARGE&region=antwerpen&colorblind=false&width=1920&height=1080&aspectratio=16:9";
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
        final WebView _webView = webView;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String a;

                try{
                    a = new GetRequestString("http://www.filebeeld.be/mobiel/kaart?region=antwerpen").execute().get();
                }
                catch (Exception e){
                    a = "";
                }

                final String domString = a;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

                            Document doc = Jsoup.parse(domString);
                            final Elements ele = doc.select("div#info");


                            final String mime = "text/html";
                            final String encoding = "utf-8";

                            webView.setWebChromeClient(new WebChromeClient());
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setDomStorageEnabled(true);

                            webView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url)
                                {
                                    webView.loadData(ele.toString(), mime, encoding);
                                }
                                });


                            webView.loadUrl("http://www.filebeeld.be/mobiel/kaart?region=antwerpen#info");

                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    private void downloadImageData(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO change regio
                String url = "http://www.filebeeld.be/traffic/image?format=LARGE&region=antwerpen&colorblind=false&width=1920&height=1080&aspectratio=16:9";
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
                        iv_imageData.setImageBitmap(finalbtm);
                        downloadData();
                    }
                });
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
}