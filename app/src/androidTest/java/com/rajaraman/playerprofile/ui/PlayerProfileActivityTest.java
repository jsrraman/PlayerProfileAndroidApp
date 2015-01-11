package com.rajaraman.playerprofile.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;

import com.rajaraman.playerprofile.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Config(emulateSdk = 18, manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class PlayerProfileActivityTest {

    PlayerProfileActivity activity;

    @Before
    public void setup()  {
        // To redirect Robolectric to stdout
        System.setProperty("robolectric.logging", "stdout");

        //activity = Robolectric.buildActivity(MainActivity.class).create().get();

        // Simulate activity's onCreate, onStart, onResume life cycle callbacks. This may be
        // needed if the fragment is not created in Activity.onCreate(). Just keep this for
        // reference. visible() call is for making sure Activity is visible
        activity = Robolectric.buildActivity(PlayerProfileActivity.class).create().
                                                            visible().start().resume().get();
    }

    @Test
    public void testPlayerProfileActivityIsNotNull() throws Exception {
        assertNotNull(activity);
    }
}