package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Fragment_2 extends Fragment_00 {

    ImageView iv_background;
    Bitmap image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_2, container, false);

        iv_background = (ImageView) v.findViewById(R.id.fragment_0_background);

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setBackground();
    }

    private void setBackground(){
        setImage();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = playListObject.getVariabele();
                Bitmap btm = null;

                try{
                    btm = new ImageDownloader(url).execute().get();
                }
                catch (Exception e){

                }

                final Bitmap finalbtm = btm;

                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(finalbtm != null) image = finalbtm;
                            setImage();
                        }
                    });
                }
            }
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    private void setImage(){
        if(image == null) return;

        iv_background.setImageBitmap(image);
    }
}