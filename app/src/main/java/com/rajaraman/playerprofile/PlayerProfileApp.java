package com.rajaraman.playerprofile;

import android.app.Application;
import android.content.Context;

import com.rajaraman.playerprofile.ui.adapters.CountryListAdapter;

import java.util.List;

public class PlayerProfileApp extends Application {
    private static PlayerProfileApp mInstance;
    private static Context mAppContext;
    private CountryListAdapter countryListAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        this.setAppContext(getApplicationContext());
    }

    public static PlayerProfileApp getInstance(){
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    public CountryListAdapter getCountryListAdapter() {
        return countryListAdapter;
    }

    public void setCountryListAdapter(CountryListAdapter countryListAdapter) {
        this.countryListAdapter = countryListAdapter;
    }
}