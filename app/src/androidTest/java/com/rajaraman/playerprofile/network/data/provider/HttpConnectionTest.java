package com.rajaraman.playerprofile.network.data.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Config(emulateSdk = 18, manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class HttpConnectionTest {
    @Before
    public void setup()  {
        // To redirect Robolectric to stdout
        System.setProperty("robolectric.logging", "stdout");
    }

    @Test
    public void testGetCountryListWebServiceIsWorking() throws Exception {

        String url = PlayerProfileApiDataProvider.playerProfileWebServicesBaseUrl;
        url += PlayerProfileApiDataProvider.getCountryListUrl;

        HttpConnection httpConn = new HttpConnection();
        InputStream inputStream = httpConn.getData(url);

        assertNotNull(inputStream);
    }

    @Test
    public void testGetPlayerListForEnglandWebServiceIsWorking() throws Exception {

        String url = PlayerProfileApiDataProvider.playerProfileWebServicesBaseUrl;
        url += PlayerProfileApiDataProvider.getPlayerListUrl;
        url += "1"; // England

        HttpConnection httpConn = new HttpConnection();
        InputStream inputStream = httpConn.getData(url);

        assertNotNull(inputStream);
    }

    @Test
    public void testGetPlayerProfileForPlayerWebServiceIsWorking() throws Exception {

        String url = PlayerProfileApiDataProvider.playerProfileWebServicesBaseUrl;
        url += PlayerProfileApiDataProvider.getPlayerProfileUrl;
        url += "8917"; // Moeen Ali

        HttpConnection httpConn = new HttpConnection();
        InputStream inputStream = httpConn.getData(url);

        assertNotNull(inputStream);
    }
}