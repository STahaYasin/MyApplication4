package tahayasin.digitalscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        waitAndGo();
    }
    private void waitAndGo(){
        final Context context = this;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2500);
                }
                catch(Exception e){
                    Toast.makeText(context, "Error: tv wont wait!", Toast.LENGTH_SHORT).show();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeActivity();
                    }
                });
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    private void changeActivity(){
        startActivity(new Intent(this, UuidActivity.class));
        finish();
    }
}