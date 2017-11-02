package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Fragment_55 extends Fragment_00 {

    TextView tv_type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_55, container, false);

        tv_type = (TextView) v.findViewById(R.id.fragment_55_name);
        tv_type.setText(null); // TODO vnfdlbjngfblkgfnbkljnbjklngbkjgnfdlkvbfdlvjvljfdbvfdkjbvjkhfdbvfdjvbfljvhbfvlj efkes leeg late e

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String got = "*****";

        String[] data = playListObject.getVariabele().split("\\**\\**\\*");

        //tv_type.setText(playListObject.getNaam());

        ((TextView) view.findViewById(R.id.fragment_55_ssid)).setText(data[0]);
        ((TextView) view.findViewById(R.id.fragment_55_wacht)).setText(data[1]);
    }
}