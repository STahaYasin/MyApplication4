package tahayasin.digitalscreen;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taha Yasin on 17/07/2017.
 */

public class GetRequestString extends AsyncTask<String, String, String> {
    private String myurl;
    private boolean saveToStorage;
    Context context;

    public GetRequestString(String _url){
        myurl = _url;
        saveToStorage = false;
    }
    public GetRequestString(Context context, String _url, boolean _saveToStorage){
        myurl = _url;
        saveToStorage = _saveToStorage;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
    @Override
    protected String doInBackground(String... url) {
        return (saveToStorage)? getAndSave() : getOnly();
    }
    private String getOnly(){
        final String fileName = myurl;

        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(fileName)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try{
            Response response = client.newCall(request).execute();
            result = response.body().string();
        }
        catch (IOException ie){
            result = "{'success' : false, 'msg' : 'internet error'}";
        }

        return result;
    }
    private String getAndSave(){
        final String fileName = "qq";

        String data;
        data = readFromFile(fileName);

        final String fData = data;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String result = "";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(fileName)
                        .get()
                        .addHeader("cache-control", "no-cache")
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                }
                catch (IOException ie){
                    result = "{'success' : false, 'msg' : 'internet error'}";
                }

                final String fResult = result;

                writeToFile(fileName, fResult);
            }
        };

        Thread t = new Thread(runnable);
        t.setPriority(Thread.MAX_PRIORITY);

        if(data != null && data != ""){
            t.start();
            return data;
        }
        else {
            runnable.run();
            return readFromFile(fileName);
        }
    }
    private String readFromFile(String fileName){
        if(context == null) return null;

        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            //FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        catch (Exception e) {
            return null;
        }
    }
    private void writeToFile(String fileName, String data){
        if(context == null) return;

        try {
            //FileOutputStream fos = new FileOutputStream(new File(fileName));
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (data != null) {
                fos.write(data.getBytes());
            }
            fos.close();
        } catch (Exception e) {
            String a = e.getMessage();
        }
    }
}