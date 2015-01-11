// Created by rajaraman on Dec 26, 2014
package com.rajaraman.playerprofile.network.data.provider;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ApiDataProviderServiceReceiver extends ResultReceiver {

    private Listener listener;

    public ApiDataProviderServiceReceiver(Handler handler) {
        super(handler);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (listener != null)
            listener.onReceiveResult(resultCode, resultData);
    }


    public static interface Listener {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}