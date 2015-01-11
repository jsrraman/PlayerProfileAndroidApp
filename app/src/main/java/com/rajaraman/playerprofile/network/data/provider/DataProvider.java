package com.rajaraman.playerprofile.network.data.provider;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

public abstract class DataProvider {
    protected Context context = null;
    protected OnDataReceivedListener onDataReceivedListener;

    public DataProvider() {}

    // UI should implement this to get the data received from the web service
    public static interface OnDataReceivedListener {
        public void onDataFetched(int playerProfileApiId, Object responseData);
    }
}
