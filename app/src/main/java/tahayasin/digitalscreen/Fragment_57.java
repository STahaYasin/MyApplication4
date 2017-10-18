package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment_57 extends Fragment_00 {

    TextView tv_type;
    ImageView iv_background;
    ImageView iv_imageData;

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_imageData.setImageBitmap(finalbtm);
                    }
                });
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
}