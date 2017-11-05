package tahayasin.digitalscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UuidActivity extends Activity {

    TextView tv_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuid);

        tv_uuid = (TextView) findViewById(R.id.tv_uuid);

        checkUuidLocally();
    }
    private void checkUuidLocally(){
        String myUuid = appValues.getUUIDFroSharedPrefences(this);

        if(myUuid.equals("UUID")){
            getNewUuid();
        }
        else{
            useUuid(myUuid);
        }
    }
    private void getNewUuid(){
        final Context context = this;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String str_uuid;
                try{
                    str_uuid = new GetRequestString("https://www.digitalscreen.be/api/generateuuid.php").execute().get();
                }
                catch(Exception e){
                    str_uuid = "UUID";
                }

                final String finalUuid = str_uuid;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        storeUuidLocally(finalUuid);
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void storeUuidLocally(String uuid){
        SharedPreferences sp_uuid = getSharedPreferences(appValues.prefenceUuid, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp_uuid.edit();
        editor.putString(appValues.prefenceUuid, uuid);
        editor.commit();

        checkUuidLocally();
    }
    private void useUuid(final String uuid){
        tv_uuid.setText(uuid);
        final Context context = this;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeActivity(uuid);
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    private void changeActivity(String uuid){
        startActivity(new Intent(this, PlaylistloaderActivity.class).putExtra(appValues.prefenceUuid, uuid));
        finish();
    }
}