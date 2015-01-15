package com.rajaraman.playerprofile.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.provider.PlayerProfileApiDataProvider;
import com.rajaraman.playerprofile.network.data.provider.VolleySingleton;
import com.rajaraman.playerprofile.utils.AppUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class PlayerProfileAllActivity extends FragmentActivity implements
                 PlayerProfileApiDataProvider.OnDataReceivedListener {

    private static final String TAG = PlayerProfileAllActivity.class.getCanonicalName();

    int playerId = 0;
    private ArrayList<PlayerEntity> playerEntityList = null;
    private LinearLayout parentLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_player_profile);

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

    // Initializes the UI
    private void prepareUi(Intent intent) {

        // Get the passed player id
        this.playerId = intent.getIntExtra("playerId", 0);

//        // Get the player profile for the given player id
//        PlayerProfileApiDataProvider.getInstance().getPlayerProfile(this, this, playerId);

        // Get the player profile for the given player id
        PlayerProfileApiDataProvider.getInstance().getPlayerProfileAll(this, this, playerId);

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
            case PlayerProfileApiDataProvider.GET_PLAYER_PROFILE_ALL_FOR_PLAYER_ID_API: {
                HandleGetPlayerProfileAllResponse(responseData);
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
    private void HandleGetPlayerProfileAllResponse(Object responseData) {

        // Try getting the data for the country list and show the list
        JSONObject jsonObject = (JSONObject)responseData;

        if (null == jsonObject) {
            AppUtil.logDebugMessage(TAG, "JSON data is null. This is unexpected !!!");
            AppUtil.showDialog(this, getString(R.string.response_failed));
            return;
        }

//        // Note: We are expecting a detailed player profile, so there will be only
//        // one item in the playerEntityList array.
//        if (0 == playerEntityList.size()) {
//            AppUtil.logDebugMessage(TAG, "Player entity size is 0. This is unexpected !!!");
//            AppUtil.showDialog(this, getString(R.string.response_failed));
//            return;
//        }

        try {
            String thumbnailUrl = (String) jsonObject.optString("thumbnailUrl");

            // If thumbnail URL is empty for this player, first scrape the
            // player profile data and then try getting the data again.
            if ( thumbnailUrl.isEmpty() ) {
                PlayerProfileApiDataProvider.getInstance().scrapePlayerProfile(this, this, this.playerId);
                AppUtil.showProgressDialog(this);
                return;
            }

            // If we have got the detailed player profile, go ahead and construct the UI
            constructUi(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Player profile scrapped successfully, so try getting the detailed player profile for the
    // given player again
    private void HandleScrapePlayerProfileResponse(Object responseData) {
        PlayerProfileApiDataProvider.getInstance().getPlayerProfile(this, this, this.playerId);
        AppUtil.showProgressDialog(this);
    }

    private void constructUi(JSONObject jsonObject) {

        initPlayerProfileUi();

        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();

            try {
                Object value = jsonObject.get(key);

                TextView textViewValue;

                if (value instanceof String) {
                    addTextViewToUi(key);
                    addTextViewToUi((String)value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.setContentView(this.parentLayout);
    }

    private void initPlayerProfileUi() {
        // Create a linear layout
        this.parentLayout = new LinearLayout(this);
        this.parentLayout.setOrientation(LinearLayout.VERTICAL);

        // Configuring the width and height of the parent layout.
        LinearLayout.LayoutParams parentLayoutParams =
                          new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                           LinearLayout.LayoutParams.MATCH_PARENT);
        this.parentLayout.setLayoutParams(parentLayoutParams);
    }

    private void addTextViewToUi(String text) {

        TextView tv = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(text);

        tv.setPadding(8, 8, 8, 8);
        this.parentLayout.addView(tv);
   }
}