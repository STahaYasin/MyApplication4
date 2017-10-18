package tahayasin.digitalscreen;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taha Yasin on 17/07/2017.
 */

public class GetRequestString extends AsyncTask<String, String, String> {
    private String myurl;

    public GetRequestString(String _url){
        myurl = _url;
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
        String jsontring = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(myurl)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try{
            Response response = client.newCall(request).execute();
            jsontring = response.body().string();
        }
        catch (IOException ie){
            jsontring = "{'success' : false, 'msg' : 'internet error'}";
        }
        return jsontring;
    }
}