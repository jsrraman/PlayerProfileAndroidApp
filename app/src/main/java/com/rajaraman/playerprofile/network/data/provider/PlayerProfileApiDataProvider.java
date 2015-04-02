package com.rajaraman.playerprofile.network.data.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rajaraman.playerprofile.utils.AppUtil;

/**
 * Created by Rajaraman on 25/12/14.
 * This interface provides data from PlayerProfileWebServices
 */
public class PlayerProfileApiDataProvider extends DataProvider implements
                                           ApiDataProviderServiceReceiver.Listener {

    private static final String TAG = PlayerProfileApiDataProvider.class.getCanonicalName();

    private ApiReqResData apiReqResData = new ApiReqResData();

    public static final int GET_COUNTRY_LIST_API = 0;
    public static final int SCRAPE_COUNTRY_LIST_API = 1;
    public static final int GET_PLAYER_LIST_FOR_COUNTRY_ID_API = 2;
    public static final int SCRAPE_PLAYER_LIST_FOR_COUNTRY_ID_API = 3;
    public static final int SCRAPE_PLAYER_PROFILE_FOR_PLAYER_ID_API = 4;
    public static final int GET_PLAYER_PROFILE_FOR_PLAYER_ID_API = 5;
    public static final int GET_PLAYER_PROFILE_ALL_FOR_PLAYER_ID_API = 6;

    // Ajirasoft IP
    //public static final String playerProfileWebServicesBaseUrl = "http://192.168.1.106:3000";

    // Home IP
    //public static final String playerProfileWebServicesBaseUrl = "http://10.0.0.115:3000";

    // Stayzilla Bangalore IP
    public static final String playerProfileWebServicesBaseUrl = "http://192.168.30.109:3000";

    // Home DDNS end point
    //public static final String playerProfileWebServicesBaseUrl = "http://rajaraman.ddns.net:3000";

    // Heroku end point
    //public static final String playerProfileWebServicesBaseUrl = "https://player-profile.herokuapp.com";
    public static final String scrapeCountryList = "/scrape/countries";
    public static final String getCountryListUrl = "/players/countries";
    public static final String getPlayerListUrl = "/players/country?countryId=";
    public static final String scrapePlayerListUrlPart1 = "/scrape/players/country?countryId=";
    public static final String scrapePlayerListUrlPart2 = "&name=";
    public static final String scrapePlayerProfileUrl = "/scrape/player?playerId=";
    public static final String getPlayerProfileUrl = "/players?playerId=";

    private static PlayerProfileApiDataProvider playerProfileApiDataProvider = null;

    private PlayerProfileApiDataProvider() {}

    public static PlayerProfileApiDataProvider getInstance() {

        if (null == playerProfileApiDataProvider) {
            playerProfileApiDataProvider = new PlayerProfileApiDataProvider();
        }

        return playerProfileApiDataProvider;
    }

    // Scrape country list
    public void scrapeCountryList(Context context, OnDataReceivedListener onDataReceivedListener) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.scrapeCountryList;

        this.apiReqResData.setRequestWebServiceApiId(SCRAPE_COUNTRY_LIST_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Get the country list
    public void getCountryList(Context context, OnDataReceivedListener onDataReceivedListener) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.getCountryListUrl;

        this.apiReqResData.setRequestWebServiceApiId(GET_COUNTRY_LIST_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Get the player list for the given country id
    public void getPlayerListForCountry(Context context,
                               OnDataReceivedListener onDataReceivedListener,
                               int countryId) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.getPlayerListUrl;

        fullUrl += Integer.toString(countryId);

        this.apiReqResData.setRequestWebServiceApiId(GET_PLAYER_LIST_FOR_COUNTRY_ID_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Scrape the player list data for the given country id and name
    public void scrapePlayerListForCountry(Context context,
                                           OnDataReceivedListener onDataReceivedListener,
                                           int countryId, String countryName) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String remainingUrl = this.scrapePlayerListUrlPart1;
        remainingUrl += countryId;
        remainingUrl += this.scrapePlayerListUrlPart2;
        remainingUrl += countryName.replace(" ", "");

        // Handle space in the country name
//      This is not valid for this project context. This is here for reference.
//        try {
//            // Android URLEncoder.encode replaces " " with + but we need %20, so this workaround
//            remainingUrl += URLEncoder.encode(countryName, "UTF8").replace("+", "%20");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String fullUrl = this.playerProfileWebServicesBaseUrl + remainingUrl;

        this.apiReqResData.setRequestWebServiceApiId(SCRAPE_PLAYER_LIST_FOR_COUNTRY_ID_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Scrape the player profile data for the given player id
    public void scrapePlayerProfile(Context context,
                                           OnDataReceivedListener onDataReceivedListener,
                                           int playerId) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.scrapePlayerProfileUrl;
        fullUrl += Integer.toString(playerId);

        this.apiReqResData.setRequestWebServiceApiId(SCRAPE_PLAYER_PROFILE_FOR_PLAYER_ID_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Scrape the player profile data for the given player id
    public void getPlayerProfile(Context context,
                                    OnDataReceivedListener onDataReceivedListener,
                                    int playerId) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.getPlayerProfileUrl;
        fullUrl += Integer.toString(playerId);

        this.apiReqResData.setRequestWebServiceApiId(GET_PLAYER_PROFILE_FOR_PLAYER_ID_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    // Scrape the player profile data for the given player id
    public void getPlayerProfileAll(Context context,
                                 OnDataReceivedListener onDataReceivedListener,
                                 int playerId) {

        this.context = context;
        this.onDataReceivedListener = onDataReceivedListener;

        String fullUrl = this.playerProfileWebServicesBaseUrl + this.getPlayerProfileUrl;
        fullUrl += Integer.toString(playerId);

        this.apiReqResData.setRequestWebServiceApiId(GET_PLAYER_PROFILE_ALL_FOR_PLAYER_ID_API);
        this.apiReqResData.setRequestUrl(fullUrl);

        this.context.startService(createApiDataProviderServiceIntent(this.apiReqResData));
    }

    private Intent createApiDataProviderServiceIntent(ApiReqResData apiReqResData) {
        Intent i = new Intent(context, PlayerProfileApiDataProviderService.class);
        ApiDataProviderServiceReceiver receiver =
                                  new ApiDataProviderServiceReceiver(new Handler());
        receiver.setListener(this);

        i.putExtra("REQUEST_DATA", apiReqResData);
        i.putExtra("RECEIVER", receiver);
        return i;
    }


    // Receives the data from ResultReceiver
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        AppUtil.logDebugMessage(TAG, "onReceiveResult");

        Object responseDataObj = resultData.getParcelable("RESPONSE_DATA");

        if (responseDataObj == null) {
            AppUtil.logDebugMessage(TAG, "resultData is null. This is unexpected !!!");
            this.onDataReceivedListener.
                    onDataFetched(this.apiReqResData.getRequestWebServiceApiId(), null);
            return;
        }

        Object parsedResponseData = null;

        // Get the parcelable data from the responseDataObj
        ApiReqResData apiReqResData = (ApiReqResData) responseDataObj;

        // Get the actual response data from the parcelable data
        parsedResponseData = apiReqResData.getResponseData();

        this.onDataReceivedListener.
                onDataFetched(this.apiReqResData.getRequestWebServiceApiId(), parsedResponseData);
    }
}