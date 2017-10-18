package tahayasin.digitalscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class PlaylistloaderActivity extends Activity {

    final Context context = this;

    TextView tv_progress;
    TextView tv_progress_discription;

    private int playList_loader_count = 0;
    private int playList_loader_loaded = 0;

    private PlayListObjects[] playList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistloader);

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_progress_discription = (TextView) findViewById(R.id.tv_progress_description);

        checkPlaylist();
        //hasPlaylist();
    }
    private void checkPlaylist(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://www.digitalscreen.be/api/hasplaylist/" + appValues.getUUID(context) + "/";
                String hasPlayListString;

                try{
                    hasPlayListString = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    hasPlayListString = "0";
                }

                final String finalHasPlayListString = hasPlayListString;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalHasPlayListString == "" || finalHasPlayListString == null){
                            wait5sec();
                        }
                        else{
                            if(finalHasPlayListString.equals("1")){
                                hasPlaylist();
                            }
                            else{
                                wait5sec();
                            }
                        }
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void wait5sec(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(appValues.refreshTimeMillis);
                }
                catch(Exception e){

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkPlaylist();
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void hasPlaylist(){
        loadPlayList();
    }
    private void loadPlayList() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://www.digitalscreen.be/api/getplaylist/" + appValues.getUUID(context) + "/";

                String jsonPlaylistString;
                try{
                    jsonPlaylistString = new GetRequestString(url).execute().get();
                }
                catch (Exception e){
                    jsonPlaylistString = "[]";
                }

                final String finalJsonPlayListString = jsonPlaylistString;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleJson(finalJsonPlayListString);
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void handleJson(String jsonPlayList){
        playList = new Gson().fromJson(jsonPlayList, PlayListObjects[].class);

        playList_loader_count = playList.length;
        updateProgress();

        tv_progress_discription.setText("Downloading PlayList data please wait..");

        downloadPlayListData();
    }
    private void downloadPlayListData(){
        for(int i = 0; i < playList.length; i ++){

            final PlayListObjects playListObject = playList[i];

            Thread downloadThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    Bitmap btm = null;

                    if(playListObject.getVariabele() != ""){
                        try {
                            btm = new ImageDownloader(playListObject.getVariabele()).execute().get();
                        }
                        catch (Exception e){

                        }
                    }
                    else{

                    }

                    final Bitmap bitmap = btm;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            playList_loader_loaded ++;
                            updateProgress();
                        }
                    });
                }
            });
            downloadThread.setPriority(Thread.NORM_PRIORITY);
            downloadThread.start();
        }
    }
    private void updateProgress(){
        tv_progress.setText(playList_loader_loaded + " / " + playList_loader_count);
        if(playList_loader_loaded == playList_loader_count){
            startActivity(new Intent(this, PlayListActivity.class).putExtra("playlist", playList));
            finish();
        }
    }
}