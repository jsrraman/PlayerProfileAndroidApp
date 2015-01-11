package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.BatFieldAvg;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerProfileBatFieldAvgFragment.OnPlayerProfileBatFieldAvgFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerProfileBatFieldAvgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerProfileBatFieldAvgFragment extends Fragment {

    private static final String TAG = PlayerProfileBatFieldAvgFragment.class.getCanonicalName();
    private static final String ARG_PARAM1 = "battingAndFieldingAvg";
    private OnPlayerProfileBatFieldAvgFragmentInteractionListener mListener;

    // Factory method to create a new instance of this fragment using the provided parameters.
    public static PlayerProfileBatFieldAvgFragment newInstance(BatFieldAvg batFieldAvg) {

        PlayerProfileBatFieldAvgFragment fragment = new PlayerProfileBatFieldAvgFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, batFieldAvg) ;

        fragment.setArguments(args);

        return fragment;
    }

    public PlayerProfileBatFieldAvgFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                                Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_player_profile_bat_field_avg,
                                                                            container, false);

        // Get the batting and fielding statistics
        Bundle bundle = getArguments();

        if ( null == bundle) {
            return rootView;
        }

        BatFieldAvg batFieldAvg = bundle.getParcelable(ARG_PARAM1);

        if ( null == batFieldAvg) {
            return rootView;
        }

        TextView textView = null;

        // Tests
        // Matches
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_matches);
        textView.setText(batFieldAvg.getTests().getMatches());

        // Runs
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_runs);
        textView.setText(batFieldAvg.getTests().getRuns());

        // Highest
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_highest);
        textView.setText(batFieldAvg.getTests().getHighest());

        // Average
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_avg);
        textView.setText(batFieldAvg.getTests().getAverage());

        // 100s
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_100s);
        textView.setText(batFieldAvg.getTests().getHundreds());

        // 50s
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_tests_50s);
        textView.setText(batFieldAvg.getTests().getFifties());

        // ----------------------------------------------------------------------------------------

        // ODIs
        // Matches
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_matches);
        textView.setText(batFieldAvg.getOdis().getMatches());

        // Runs
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_runs);
        textView.setText(batFieldAvg.getOdis().getRuns());

        // Highest
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_highest);
        textView.setText(batFieldAvg.getOdis().getHighest());

        // Average
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_avg);
        textView.setText(batFieldAvg.getOdis().getAverage());

        // 100s
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_100s);
        textView.setText(batFieldAvg.getOdis().getHundreds());

        // 50s
        textView = (TextView)rootView.findViewById(R.id.player_profile_bat_field_avg_tv_odis_50s);
        textView.setText(batFieldAvg.getOdis().getFifties());

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPlayerProfileBatFieldAvgFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlayerProfileBatFieldAvgFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPlayerProfileBatFieldAvgFragmentInteractionListener {
        public void onPlayerProfileBatFieldAvgFragmentInteraction(Uri uri);
    }
}