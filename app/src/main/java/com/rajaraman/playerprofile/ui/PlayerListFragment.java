package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.CountryEntity;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.provider.DataProvider;
import com.rajaraman.playerprofile.network.data.provider.PlayerProfileApiDataProvider;
import com.rajaraman.playerprofile.ui.adapters.PlayerListAdapter;
import com.rajaraman.playerprofile.utils.AppUtil;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PlayerListFragment extends Fragment implements
                                        AbsListView.OnItemClickListener,
                                        DataProvider.OnDataReceivedListener {

    private static final String TAG = PlayerListFragment.class.getCanonicalName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "countryId";
    private static final String ARG_PARAM2 = "countryName";

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    private ArrayList<PlayerEntity> playerEntityList = null;

    public static PlayerListFragment newInstance(CountryEntity countryEntity) {
        PlayerListFragment fragment = new PlayerListFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_PARAM1, countryEntity.getCountryId());
        args.putString(ARG_PARAM2, countryEntity.getName());

        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayerListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtil.logDebugMessage(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        AppUtil.logDebugMessage(TAG, "onResume");

        getPlayerList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListView = (ListView) inflater.inflate(R.layout.fragment_playerlist, container, false);
        mListView.setOnItemClickListener(this);

        return mListView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);

//            Intent intent = new Intent(getActivity(), PlayerProfileActivity.class);
//            intent.putExtra("playerId", this.playerEntityList.get(position).getPlayerId());

            Intent intent = new Intent(getActivity(), PlayerProfileAllActivity.class);
            intent.putExtra("playerId", this.playerEntityList.get(position).getPlayerId());

            startActivity(intent);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

    // Callback for the backend to let this fragment know the player list from the web service
    public void onDataFetched(int playerProfileApiId, Object responseData) {
        AppUtil.logDebugMessage(TAG, "onDataFetched callback");

        AppUtil.dismissProgressDialog();

        boolean showErrorMessage = false;

        if (null == responseData) {
            showErrorMessage = true;
        }

        // Some APIs return boolean as responseData, so check for that as well
        if (false == showErrorMessage) {
            // Even though the service would have sent it as boolean, the value would be
            // autoboxed to Boolean, so it is safe to check like this
            if ( responseData instanceof Boolean) {
                boolean status = ((Boolean)responseData).booleanValue();
                showErrorMessage = !status; // status = true means the API had succeeded
            }
        }

        if (showErrorMessage) {
            // The app has failed to get a response from webservice. Show appropriate error message
            String message = getActivity().getString(R.string.response_failed);
            AppUtil.showDialog(getActivity(), message);
            return;
        }

        switch (playerProfileApiId) {
            case PlayerProfileApiDataProvider.GET_PLAYER_LIST_FOR_COUNTRY_ID_API: {
                HandleGetPlayerListResponse(responseData);
                break;
            }

            case PlayerProfileApiDataProvider.SCRAPE_PLAYER_LIST_FOR_COUNTRY_ID_API: {
                HandleScrapePlayerListResponse(responseData);
                break;
            }

            default: break;
        }
    }

    // Handles the get player list API response
    private void HandleGetPlayerListResponse(Object responseData) {

        // Try getting the data for the country list and show the list
        this.playerEntityList = (ArrayList<PlayerEntity>)responseData;

        if (null == this.playerEntityList) {
            AppUtil.logDebugMessage(TAG, "Player entity list is null. This is unexpected !!!");
            AppUtil.showDialog(getActivity(), getActivity().getString(R.string.response_failed));
            return;
        }

        // If there is no player list available yet for this country, first scrape the data
        // and then try getting the data again.
        if ( 0 == this.playerEntityList.size() ) {
            int countryId = getArguments().getInt(ARG_PARAM1);
            String countryName = getArguments().getString(ARG_PARAM2);

            PlayerProfileApiDataProvider.getInstance().
                    scrapePlayerListForCountry(getActivity(), this, countryId, countryName);

            AppUtil.showProgressDialog(getActivity());

            return;
        }

        PlayerListAdapter playerListAdapter = new PlayerListAdapter(getActivity(),
                                                                            this.playerEntityList);
         // Client side sorting - Keep this for reference
//        // Sort the player list by player names in the ascending order
//        if (null != playerListAdapter) {
//            playerListAdapter.sort(new Comparator<PlayerEntity>() {
//                @Override
//                public int compare(PlayerEntity lhs, PlayerEntity rhs) {
//                    return lhs.getName().compareTo(rhs.getName());
//                }
//            });
//        }

        mListView.setAdapter(playerListAdapter);
    }

    // Player list scrapped successfully, so try getting the player list for the country again
    private void HandleScrapePlayerListResponse(Object responseData) {

        // Get the country id passed as a parameter during this fragment's creation
        int countryId = getArguments().getInt(ARG_PARAM1);

        PlayerProfileApiDataProvider.getInstance().
                getPlayerListForCountry(getActivity(), this, countryId);

        AppUtil.showProgressDialog(getActivity());
    }

    private void getPlayerList() {

        // Try getting the player list
        // Get the country id passed as a parameter during this fragment's creation
        int countryId = getArguments().getInt(ARG_PARAM1);

        PlayerProfileApiDataProvider.getInstance().
                getPlayerListForCountry(getActivity(), this, countryId);

        AppUtil.showProgressDialog(getActivity());
    }
}