package com.rajaraman.playerprofile.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.rajaraman.playerprofile.PlayerProfileApp;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.CountryEntity;
import com.rajaraman.playerprofile.network.data.provider.DataProvider;
import com.rajaraman.playerprofile.network.data.provider.PlayerProfileApiDataProvider;
import com.rajaraman.playerprofile.ui.adapters.CountryListAdapter;
import com.rajaraman.playerprofile.utils.AppUtil;

public class SplashActivity extends Activity implements
                                                    DataProvider.OnDataReceivedListener {

    private static final String TAG = MainActivity.class.getCanonicalName();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

        setupApp();
	}


    private void setupApp() {

        // Get the country names list
        if (false == AppUtil.isNetworkAvailable(this)) {
            AppUtil.showDialog(this, getString(R.string.network_not_available));
            return;
        }

        PlayerProfileApiDataProvider.getInstance().getCountryList(this, this);
    }

	private void goToHomeScreen() {
		finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
	}

    @Override
    public void onDataFetched(int playerProfileApiId, Object responseData) {

        AppUtil.logDebugMessage(TAG, "onDataFetched callback");

        AppUtil.dismissProgressDialog();

        boolean showErrorMessage = false;

        if (null == responseData) {
            // No response data from service, so make sure the application object's
            // country list adapter is null as well !!!
            ((PlayerProfileApp)getApplication()).setCountryListAdapter(null);

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
            String message = getString(R.string.quit_application);
            AppUtil.showErrorDialogAndQuitApp(this, message);
            return;
        }

        switch (playerProfileApiId) {
            case PlayerProfileApiDataProvider.GET_COUNTRY_LIST_API: {
                HandleGetCountryListResponse(responseData);
                break;
            }

            case PlayerProfileApiDataProvider.SCRAPE_COUNTRY_LIST_API: {
                HandleScrapeCountryListResponse(responseData);
                break;
            }

            default: break;
        }
    }

    // Handles the get player list API response
    private void HandleGetCountryListResponse(Object responseData) {

        // Try getting the data for the country list and show the list
        ArrayList<CountryEntity> countryEntityList = (ArrayList<CountryEntity>)responseData;

        if (null == countryEntityList) {

            AppUtil.logDebugMessage(TAG, "Country entity list is null. This is unexpected !!!");
            AppUtil.showDialog(this, getString(R.string.quit_application));

            return;
        }

        // If there is no country list available yet, first scrape the data and then try
        // getting the data again.
        if ( 0 == countryEntityList.size() ) {
            PlayerProfileApiDataProvider.getInstance().scrapeCountryList(this, this);
            return;
        }

        CountryListAdapter countryListAdapter = new CountryListAdapter(this, countryEntityList);

        // Save the country list adapter in application object which will be used later
        ((PlayerProfileApp)getApplication()).setCountryListAdapter(countryListAdapter);

        goToHomeScreen();
    }

    // Country list scrapped successfully, so try getting the country list again
    private void HandleScrapeCountryListResponse(Object responseData) {

        PlayerProfileApiDataProvider.getInstance().getCountryList(this, this);
    }
}