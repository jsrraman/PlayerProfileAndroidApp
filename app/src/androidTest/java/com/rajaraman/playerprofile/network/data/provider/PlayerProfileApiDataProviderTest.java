package com.rajaraman.playerprofile.network.data.provider;

import com.rajaraman.playerprofile.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// ***************************************************
// This class is for future reference so commented out
// ***************************************************
//
//@Config(emulateSdk = 18, manifest = "./src/main/AndroidManifest.xml")
//@RunWith(RobolectricTestRunner.class)
//public class PlayerProfileApiDataProviderTest implements DataProvider.OnDataReceivedListener {
//
//    MainActivity activity;
//    AtomicBoolean testDone;
//
//
//    @Before
//    public void setup()  {
//        // To redirect Robolectric to stdout
//        System.setProperty("robolectric.logging", "stdout");
//
//        activity = Robolectric.buildActivity(MainActivity.class).create().
//                visible().start().resume().get();
//    }
//
//    // We set a timeout as a check on the event being posted
//    @Test(timeout = 5000) // timeout in milliseconds
//    public void testGetCountryListApi() {
//
//        this.testDone = new AtomicBoolean(false);
//
//        PlayerProfileApiDataProvider playerProfileApiDataProvider =
//                                                        PlayerProfileApiDataProvider.getInstance();
//
//        // Try getting the country list
//        playerProfileApiDataProvider.getCountryList(activity, this);
//
//        // Wait for test to finish or timeout
//        while(!testDone.get());
//    }
//
//    @Override
//    public void onDataFetched(int playerProfileApiId, Object responseData) {
//
//        assertEquals(playerProfileApiId, PlayerProfileApiDataProvider.GET_PLAYER_LIST_FOR_COUNTRY_ID_API);
//
//        assertNotNull(responseData);
//
//        this.testDone.set(true);
//    }
//}