package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.BatFieldAvg;
import com.rajaraman.playerprofile.network.data.entities.BowlAvg;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerProfileBowlingAvgFragment.OnPlayerProfileBowlingAvgFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerProfileBowlingAvgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerProfileBowlingAvgFragment extends Fragment {

    private static final String TAG = PlayerProfileBowlingAvgFragment.class.getCanonicalName();
    private static final String ARG_PARAM1 = "bowlingAvg";
    private OnPlayerProfileBowlingAvgFragmentInteractionListener mListener;

    // Factory method to create a new instance of this fragment using the provided parameters.
    public static PlayerProfileBowlingAvgFragment newInstance(BowlAvg bowlAvg) {

        PlayerProfileBowlingAvgFragment fragment = new PlayerProfileBowlingAvgFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, bowlAvg) ;

        fragment.setArguments(args);

        return fragment;
    }
    public PlayerProfileBowlingAvgFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_player_profile_bowl_avg,
                                                                            container, false);

        // Get the batting and fielding statistics
        Bundle bundle = getArguments();

        if ( null == bundle) {
            return rootView;
        }

        BowlAvg bowlAvg = bundle.getParcelable(ARG_PARAM1);

        if ( null == bowlAvg) {
            return rootView;
        }

        TextView textView = null;

        // Tests
        // Matches
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_matches);
        textView.setText(bowlAvg.getTests().getMatches());

        // Wickets
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_wickets);
        textView.setText(bowlAvg.getTests().getWickets());

        // Best bowling in a match
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_bbm);
        textView.setText(bowlAvg.getTests().getBestMatchBowling());

        // Average
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_avg);
        textView.setText(bowlAvg.getTests().getAverage());

        // 4 wickets in an innings
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_4w);
        textView.setText(bowlAvg.getTests().getFourWktsInInns());

        // 5 wickets in an innings
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_5w);
        textView.setText(bowlAvg.getTests().getFiveWktsInInns());

        // 10 wickets in a match
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_tests_10);
        textView.setText(bowlAvg.getTests().getTenWktsInMatch());


        // ----------------------------------------------------------------------------------------

        // ODIs
        // Matches
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_matches);
        textView.setText(bowlAvg.getOdis().getMatches());

        // Wickets
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_wickets);
        textView.setText(bowlAvg.getOdis().getWickets());

        // Best bowling in a match
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_bbm);
        textView.setText(bowlAvg.getOdis().getBestMatchBowling());

        // Average
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_avg);
        textView.setText(bowlAvg.getOdis().getAverage());

        // 4 wickets in an innings
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_4w);
        textView.setText(bowlAvg.getOdis().getFourWktsInInns());

        // 5 wickets in an innings
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_5w);
        textView.setText(bowlAvg.getOdis().getFiveWktsInInns());

        // 10 wickets in a match
        textView = (TextView)rootView.findViewById(R.id.player_profile_bowl_avg_tv_odis_10);
        textView.setText(bowlAvg.getOdis().getTenWktsInMatch());

        return rootView;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPlayerProfileBowlingAvgFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlayerProfileBowlingAvgFragmentInteractionListener) activity;
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
    public interface OnPlayerProfileBowlingAvgFragmentInteractionListener {
        public void onPlayerProfileBowlingAvgFragmentInteraction(Uri uri);
    }
}
