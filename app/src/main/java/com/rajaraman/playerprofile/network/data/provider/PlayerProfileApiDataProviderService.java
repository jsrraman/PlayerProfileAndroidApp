// Created by rajaraman on Dec 26, 2014

package com.rajaraman.playerprofile.network.data.provider;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rajaraman.playerprofile.network.data.entities.BatFieldAvg;
import com.rajaraman.playerprofile.network.data.entities.BatFieldMatchStatistics;
import com.rajaraman.playerprofile.network.data.entities.BowlAvg;
import com.rajaraman.playerprofile.network.data.entities.BowlMatchStatistics;
import com.rajaraman.playerprofile.network.data.entities.CountryEntity;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.parser.PlayerEntity.BatFieldAvgDeserializer;
import com.rajaraman.playerprofile.network.data.parser.PlayerEntity.BatFieldMatchStatisticsDeserializer;
import com.rajaraman.playerprofile.network.data.parser.PlayerEntity.BowlAvgDeserializer;
import com.rajaraman.playerprofile.network.data.parser.PlayerEntity.BowlMatchStatisticsDeserializer;
import com.rajaraman.playerprofile.network.data.parser.PlayerEntity.PlayerEntityDeserializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerProfileApiDataProviderService extends IntentService {
    private static final String TAG = PlayerProfileApiDataProviderService.class.getCanonicalName();

    public PlayerProfileApiDataProviderService() {
        super("PlayerProfileApiDataProviderService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "On Handle Intent - Start");

        // Note: ApiReqResData needs to implement Parcelable as it is retrieved via getParcelableExtra
        ApiReqResData apiReqResData = intent.getParcelableExtra("REQUEST_DATA");
        final ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("RECEIVER");

        // Request data is a web service url
        String url = apiReqResData.getRequestUrl();

        HttpConnection httpConn = new HttpConnection();
        Bundle bundle = new Bundle();

        try {
            InputStream inputStream = httpConn.getData(url);

            // Convert the input stream into JSON string
            String jsonData = inputStreamToJsonString(inputStream).toString();

            Object parsedResponseData = null;

            int apiId = apiReqResData.getRequestWebServiceApiId();

            switch (apiId) {
                case PlayerProfileApiDataProvider.GET_COUNTRY_LIST_API: {
                    parsedResponseData = getCountryListFromJson(jsonData);
                    break;
                }

                case PlayerProfileApiDataProvider.SCRAPE_COUNTRY_LIST_API: {
                    parsedResponseData = getScrapeResultFromJson(jsonData);
                    break;
                }

                case PlayerProfileApiDataProvider.GET_PLAYER_LIST_FOR_COUNTRY_ID_API:
                case PlayerProfileApiDataProvider.GET_PLAYER_PROFILE_FOR_PLAYER_ID_API:{
                    parsedResponseData = getPlayerProfileFromJson(jsonData);
                    break;
                }

                case PlayerProfileApiDataProvider.GET_PLAYER_PROFILE_ALL_FOR_PLAYER_ID_API:{
                    parsedResponseData = getPlayerProfileAllFromJson(jsonData);
                    break;
                }

                case PlayerProfileApiDataProvider.SCRAPE_PLAYER_LIST_FOR_COUNTRY_ID_API: {
                    parsedResponseData = getScrapeResultFromJson(jsonData);
                    break;
                }

                case PlayerProfileApiDataProvider.SCRAPE_PLAYER_PROFILE_FOR_PLAYER_ID_API: {
                    parsedResponseData = getScrapeResultFromJson(jsonData);
                    break;
                }

                default: {
                    break;
                }
            }

            //AppUtil.logDebugMessage(TAG, json);

            apiReqResData.setResponseData(parsedResponseData);
            bundle.putParcelable("RESPONSE_DATA", apiReqResData);
        } catch (Exception e) {
            e.printStackTrace();
            bundle.putParcelable("RESPONSE_DATA", null);
        } finally {
            httpConn.disconnect();
            resultReceiver.send(apiReqResData.getRequestWebServiceApiId(), bundle);
        }
    }

    private StringBuilder inputStreamToJsonString(InputStream is) {
        String rLine = "";
        StringBuilder jsonBuilder = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                jsonBuilder.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonBuilder;
    }

    private ArrayList<CountryEntity> getCountryListFromJson(String jsonData) {

        ArrayList<CountryEntity> countryEntityList = null;

        Gson gson = new Gson();

        try {
            // Get the root JSON object
            JSONObject rootJsonObj = new JSONObject(jsonData);

            // Get the key we are interested in
            JSONArray countryListKeyJsonArray = rootJsonObj.getJSONArray("result");

            // Convert this again to JSON string so that we can use Gson to
            // easily convert (deserialize) this to the actual entity object
            String countryListJsonString = countryListKeyJsonArray.toString();

            //AppUtil.logDebugMessage(TAG, countryListJsonString);

            // convert (Deserialize) JSON string to the equivalent entity object
            countryEntityList = gson.fromJson(countryListJsonString,
                    new TypeToken<ArrayList<CountryEntity>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return countryEntityList;
        }
    }

    private boolean getScrapeResultFromJson(String jsonData) {

        boolean status = true;

        try {
            // Get the root JSON object
            JSONObject rootJsonObj = new JSONObject(jsonData);

            // Get the status value
            String statusVal = (String) rootJsonObj.get("status");

            status = statusVal.equalsIgnoreCase("success") ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    // Get the player profile from json
    // Note:
    // PlayerProfile has nested objects, so it uses JsonDeserializer to parse
    // the underlying nested objects. Deserializing should not take time, if it takes time
    // consider pushing the deserialize logic to a worker thread
    private ArrayList<PlayerEntity> getPlayerProfileFromJson(String jsonData) {

        ArrayList<PlayerEntity> playerEntityList = null;

        GsonBuilder gsonBuilder = new GsonBuilder();

        try {
            // Register the deserializer objects
            gsonBuilder.registerTypeAdapter(PlayerEntity.class, new PlayerEntityDeserializer());
            gsonBuilder.registerTypeAdapter(BatFieldAvg.class, new BatFieldAvgDeserializer());
            gsonBuilder.registerTypeAdapter(BatFieldMatchStatistics.class,
                                                        new BatFieldMatchStatisticsDeserializer());
            gsonBuilder.registerTypeAdapter(BowlAvg.class, new BowlAvgDeserializer());
            gsonBuilder.registerTypeAdapter(BowlMatchStatistics.class,
                                                        new BowlMatchStatisticsDeserializer());

            Gson gson = gsonBuilder.create();

            // Note: As PlayerEntity is a complex type (i.e it has nested objects), the gson.fromJson
            // conversion is different (i.e PlayerEntity type is passed and PlayerEntityList type is returned)
            // compared to other simple conversions, eg: getCountryListFromJson
            playerEntityList = gson.fromJson(jsonData, new TypeToken<PlayerEntity>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
                return playerEntityList;
        }
    }

    // Get all the player profile from json
    // Note: This is for sharath's requirement
    private JSONObject getPlayerProfileAllFromJson(String jsonData) {

        JSONObject resJsonObject = null;

        try {
            // Get the root JSON object
            JSONObject rootJsonObj = new JSONObject(jsonData);

            // Get the status value
            String statusVal = (String) rootJsonObj.get("status");

            if (statusVal.equalsIgnoreCase("success")) {
                resJsonObject = ((JSONArray) rootJsonObj.get("result")).getJSONObject(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resJsonObject;
        }
    }
}