package tahayasin.digitalscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class Fragment_00 extends Fragment {

    private OnFragmentInteractionListener mListener;

    protected PlayListObjects playListObject;
    protected Context context;

    public Fragment_00() {
        // Required empty public constructor
    }

    public void addData(Context context, PlayListObjects _playListObject){
        playListObject = _playListObject;
        this.context = context;
    }
    public PlayListObjects getPlayListObject(){
        return playListObject;
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_20 newInstance() {
        Fragment_20 fragment = new Fragment_20();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
