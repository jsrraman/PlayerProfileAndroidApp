package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.provider.DataProvider;
import com.rajaraman.playerprofile.network.data.provider.PlayerProfileApiDataProvider;
import com.rajaraman.playerprofile.network.data.provider.VolleySingleton;
import com.rajaraman.playerprofile.ui.adapters.PlayerListAdapter;
import com.rajaraman.playerprofile.utils.AppUtil;

import java.util.ArrayList;


public class PlayerProfileActivity extends FragmentActivity implements
            PlayerProfileBatFieldAvgFragment.OnPlayerProfileBatFieldAvgFragmentInteractionListener,
            PlayerProfileBowlingAvgFragment.OnPlayerProfileBowlingAvgFragmentInteractionListener,
            PlayerProfileApiDataProvider.OnDataReceivedListener {

    private static final String TAG = PlayerProfileActivity.class.getCanonicalName();

    int playerId = 0;
    private ArrayList<PlayerEntity> playerEntityList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player_profile);

        //if (savedInstanceState == null) {
        //}

        prepareUi(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPlayerProfileBatFieldAvgFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPlayerProfileBowlingAvgFragmentInteraction(Uri uri) {

    }

    // Initializes the UI
    private void prepareUi(Intent intent) {

        // Get the passed player id
        this.playerId = intent.getIntExtra("playerId", 0);

        // Get the player profile for the given player id
        PlayerProfileApiDataProvider.getInstance().getPlayerProfile(this, this, playerId);

        AppUtil.showProgressDialog(this);
    }

    @Override
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
            String message = getString(R.string.response_failed);
            AppUtil.showDialog(this, message);
            return;
        }

        switch (playerProfileApiId) {
            case PlayerProfileApiDataProvider.GET_PLAYER_PROFILE_FOR_PLAYER_ID_API: {
                HandleGetPlayerProfileResponse(responseData);
                break;
            }

            case PlayerProfileApiDataProvider.SCRAPE_PLAYER_PROFILE_FOR_PLAYER_ID_API: {
                HandleScrapePlayerProfileResponse(responseData);
                break;
            }

            default: break;
        }
    }

    // Handles the get player profile API response
    private void HandleGetPlayerProfileResponse(Object responseData) {

        // Try getting the data for the country list and show the list
        this.playerEntityList = (ArrayList<PlayerEntity>)responseData;

        if (null == this.playerEntityList) {
            AppUtil.logDebugMessage(TAG, "Player entity list is null. This is unexpected !!!");
            AppUtil.showDialog(this, getString(R.string.response_failed));
            return;
        }

        // Note: We are expecting a detailed player profile, so there will be only
        // one item in the playerEntityList array.
        if (0 == playerEntityList.size()) {
            AppUtil.logDebugMessage(TAG, "Player entity size is 0. This is unexpected !!!");
            AppUtil.showDialog(this, getString(R.string.response_failed));
            return;
        }

        PlayerEntity playerEntity = this.playerEntityList.get(0);

        String thumbnailUrl = playerEntity.getThumbnailUrl();

        // If thumnbnail URL is empty for this player, first scrape the
        // player profile data and then try getting the data again.
        if ( thumbnailUrl.isEmpty() ) {
            PlayerProfileApiDataProvider.getInstance().scrapePlayerProfile(this, this, this.playerId);
            AppUtil.showProgressDialog(this);
            return;
        }

        // If we have got the detailed player profile, go ahead and construct the UI
        constructUi(playerEntity);
    }

    // Player profile scrapped successfully, so try getting the detailed player profile for the
    // given player again
    private void HandleScrapePlayerProfileResponse(Object responseData) {
        PlayerProfileApiDataProvider.getInstance().getPlayerProfile(this, this, this.playerId);
        AppUtil.showProgressDialog(this);
    }

    private void constructUi(final PlayerEntity playerEntity) {

        // Player thumbnail
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();

        NetworkImageView imageView = (NetworkImageView)
                                    findViewById(R.id.player_profile_imgview_player_thumbnail);

        imageView.setDefaultImageResId(R.drawable.ic_launcher);
        imageView.setImageUrl(playerEntity.getThumbnailUrl(), imageLoader);

        TextView textView = null;

        // Player name
        textView = (TextView) findViewById(R.id.player_profile_tv_name);
        textView.setText(playerEntity.getName());

        // Player country
        textView = (TextView) findViewById(R.id.player_profile_tv_country);
        textView.setText(playerEntity.getCountry());

        // Player Batting Style
        textView = (TextView) findViewById(R.id.player_profile_tv_bat_style);
        textView.setText(playerEntity.getBattingStyle());

        // Player Bowling Style
        textView = (TextView) findViewById(R.id.player_profile_tv_bowl_style);
        textView.setText(playerEntity.getBowlingStyle());

        Button buttonBattingFieldingAvg = (Button)findViewById(R.id.player_profile_button_batting);

        buttonBattingFieldingAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set the selected and not selected button images
                ((Button)v).setBackgroundResource(R.drawable.tab_selected);

                Button buttonBowlingAvg = (Button)findViewById(R.id.player_profile_button_bowling);
                buttonBowlingAvg.setBackgroundResource(R.drawable.tab_not_selected);

                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_profile_avg_fragment_container,
                        PlayerProfileBatFieldAvgFragment.newInstance(playerEntity.getBatFieldAvg()))
                    .commit();
            }
        });

        Button buttonBowlingAvg = (Button)findViewById(R.id.player_profile_button_bowling);

        buttonBowlingAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set the selected and not selected button images
                ((Button)v).setBackgroundResource(R.drawable.tab_selected);

                Button buttonBattingAvg = (Button)findViewById(R.id.player_profile_button_batting);
                buttonBattingAvg.setBackgroundResource(R.drawable.tab_not_selected);

                getSupportFragmentManager().beginTransaction()
                      .replace(R.id.player_profile_avg_fragment_container,
                              PlayerProfileBowlingAvgFragment.newInstance(playerEntity.getBowlAvg()))
                      .commit();
            }
        });

        // Select the batting statistics by default
        buttonBattingFieldingAvg.performClick();
    }
}