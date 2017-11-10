package tahayasin.digitalscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Taha Yasin on 12/10/2017.
 */

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    private String url;
    private boolean save;
    private ISaveImages iSaveImages;


    public ImageDownloader(String url) {
        this.url = url;
        save = false;
    }
    public ImageDownloader(Context context, String url, boolean save, ISaveImages iSaveImages) {
        this.url = url;
        this.save = save; // TODO set to dynamic
        this.context = context;
        this.iSaveImages = iSaveImages;
    }



    protected Bitmap doInBackground(String... urls) {
        return (save)? getAndSave() : onlyGet();
    }
    private Bitmap onlyGet(){
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
    private Bitmap getAndSave(){
        final String fileName = url;
        final String __fileName = getNewString(fileName);

        //Bitmap data;
        //data = readFromFile(__fileName);

        Bitmap fData = null;


        //fData = iSaveImages.getBitmap(__fileName);

        //final Bitmap fData = data;

        //TODO First check if file excists;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Bitmap mIcon11 = null;

                InputStream in;
                try {
                    in = new java.net.URL(fileName).openStream();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    in = null;
                }

                if(in != null){
                    try {
                        mIcon11 = BitmapFactory.decodeStream(in);
                        //writeToFile(mIcon11, __fileName);
                        //iSaveImages.setBitmap(__fileName, mIcon11);
                    }
                    catch (Exception e){
                        mIcon11 = null;
                    }
                }
                else{
                    mIcon11 = null;
                }
            }
        };

        Bitmap resultBtm = null;

        if(fData != null){
            Thread t = new Thread(r);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();

            resultBtm = fData;
        }
        else{
            r.run();
            //resultBtm = readFromFile(__fileName);
            //resultBtm = iSaveImages.getBitmap(__fileName);
        }

        return resultBtm;
    }
    /*private Bitmap readFromFile(String fileName){
        return null;
    }
    private void writeToFile(Bitmap bitmap, String fileName){
        if(bitmap!=null){
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(fileName); //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
    }
    private String getNewString(String z){

        String a = z;

        String b = "-";

        a = a.replace("/", b);
        a = a.replace(":", b);
        a = a.replace("&", b);
        a = a.replace("=", b);
        a = a.replace("@", b);
        a = a.replace(".", b);

        a = ("file" + b + a);

        return a;
    }
}