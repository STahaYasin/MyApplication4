package tahayasin.digitalscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

public class PlayListActivity extends FragmentActivity implements Fragment_00.OnFragmentInteractionListener {

    FrameLayout container;
    Fragment[] fragments;

    PlayListObjects[] playList;
    Integer isAtPlayItem = 0;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        if(getIntent().getExtras() != null && getIntent().getExtras().getSerializable("playlist") != null){
            playList = (PlayListObjects[]) getIntent().getExtras().getSerializable("playlist");
        }

        createFragments();

        setFragment();
    }
    void createFragments(){
        fragments = new Fragment[playList.length];

        for (Integer i = 0; i < playList.length; i ++){
            fragments[i] = FragmentFactory.makeFactoryFromType(context, playList[i]);
        }

        isAtPlayItem = 0;
    }
    void startAnimationTimer(){
        final Integer timer = appValues.getDelay(Integer.valueOf(playList[isAtPlayItem].getLengte()));

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(timer); //TODO change
                }
                catch (Exception e){

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeFragment();
                    }
                });
            }
        });
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    void changeFragment(){
        if(isAtPlayItem + 1 < fragments.length) isAtPlayItem ++;
        else isAtPlayItem = 0;
        setFragment();
    }
    void setFragment(){
        //if(fragments[isAtPlayItem] != null)
        // getSupportFragmentManager().beginTransaction().add(R.id.container, fragments[isAtPlayItem], fragments[isAtPlayItem].getTag()).addToBackStack(fragments[isAtPlayItem].getTag() + String.valueOf(isAtPlayItem)).commit();
        //Toast.makeText(context, ((Fragment_00) fragments[isAtPlayItem]).getPlayListObject().getNaam(), Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments[isAtPlayItem]).commit();
        startAnimationTimer();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}