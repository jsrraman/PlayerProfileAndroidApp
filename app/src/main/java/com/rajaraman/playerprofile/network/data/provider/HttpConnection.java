package com.rajaraman.playerprofile.network.data.provider;// Created by rajaraman on Dec 27, 2014

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection {

    HttpURLConnection httpCon = null;
    InputStream inputStream = null;

    private static int HTTP_CONN_TIMEOUT = 15 * 1000; // 15 seconds
    private static int HTTP_READ_TIMEOUT = 10 * 1000; // 10 seconds

    public HttpConnection() {}

    public InputStream getData(String url) throws IOException {

        this.httpCon = (HttpURLConnection) (new URL(url)).openConnection();

        this.httpCon.setRequestMethod("GET");
        this.httpCon.setConnectTimeout(HTTP_CONN_TIMEOUT);
        this.httpCon.setReadTimeout(HTTP_CONN_TIMEOUT);
        this.httpCon.connect();

        this.inputStream = httpCon.getInputStream();

        return inputStream;
    }

    public void disconnect() {

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.httpCon != null) {
            httpCon.disconnect();
        }
    }
}
