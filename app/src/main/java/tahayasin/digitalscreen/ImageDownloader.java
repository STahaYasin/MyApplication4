package tahayasin.digitalscreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Taha Yasin on 12/10/2017.
 */

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private String url;

    public ImageDownloader(String url) {
        this.url = url;
    }



    protected Bitmap doInBackground(String... urls) {
        //TODO First check if file excists;



        //If not download the file
        String urldisplay = url;
        Bitmap mIcon11 = null;

        InputStream in;
        try {
            in = new java.net.URL(urldisplay).openStream();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            in = null;
        }

        if(in != null){
            try {
                mIcon11 = BitmapFactory.decodeStream(in);
            }
            catch (Exception e){
                mIcon11 = null;
            }
        }
        else{
            mIcon11 = null;
        }
        return mIcon11;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
    }
}