package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rajaraman.playerprofile.R;

import org.json.JSONObject;

public class PlayerProfileDynamicUiActivity extends Activity {

    private static final String TAG = PlayerProfileDynamicUiActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Reconstruct the JSON object from string
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));

            PlayerProfileUiLayout playerProfileUiLayout = new PlayerProfileUiLayout();

            // Go ahead and construct the dynamic UI based on this JSON object
            playerProfileUiLayout.constructUi(jsonObject, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}