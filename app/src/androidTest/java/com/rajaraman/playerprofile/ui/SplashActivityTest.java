package com.rajaraman.playerprofile.ui;

import android.support.v4.app.FragmentManager;
import android.widget.ListView;

import com.rajaraman.playerprofile.PlayerProfileApp;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.CountryEntity;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.provider.PlayerProfileApiDataProvider;
import com.rajaraman.playerprofile.ui.adapters.CountryListAdapter;
import com.rajaraman.playerprofile.ui.adapters.PlayerListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Config(emulateSdk = 18, manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class SplashActivityTest {

    SplashActivity splashActivity;
    MainActivity mainActivity;

    @Before
    public void setup()  {
        // To redirect Robolectric to stdout
        System.setProperty("robolectric.logging", "stdout");

        //activity = Robolectric.buildActivity(MainActivity.class).create().get();

        // Simulate activity's onCreate, onStart, onResume life cycle callbacks. This may be
        // needed if the fragment is not created in Activity.onCreate(). Just keep this for
        // reference. visible() call is for making sure Activity is visible
        splashActivity = Robolectric.buildActivity(SplashActivity.class).create().
                                                            visible().start().resume().get();

        // Make sure country list adapter is not null
        populateCountryListAdapter();

        // Simulate activity's onCreate, onStart, onResume life cycle callbacks. This may be
        // needed if the fragment is not created in Activity.onCreate(). Just keep this for
        // reference. visible() call is for making sure Activity is visible
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().
                                                            visible().start().resume().get();
    }

    @Test
    public void testSplashActivityIsNotNull() throws Exception {
        assertNotNull(splashActivity);
    }

    @Test
    public void testCountryListAdapterIsNull() throws Exception {
        CountryListAdapter countryListAdapter = getCountryListAdapter(null);
        assertTrue(countryListAdapter == null);
    }

    @Test
    public void testCountryListAdapterIsNotNull() throws Exception {
        populateCountryListAdapter();
    }

    @Test
    public void testMainActivityIsNotNull() throws Exception {

        assertNotNull(mainActivity);

        assertTrue(false);
    }


    @Test
    public void testNavigationDrawerFragmentIsNotNull() throws Exception {

        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

        NavigationDrawerFragment navDrawerFragment =
                (NavigationDrawerFragment)fragmentManager.findFragmentById(R.id.navigation_drawer);

        assertNotNull(navDrawerFragment);

        assertTrue(navDrawerFragment.isInLayout());

        // Get the root view (i.e) list view
        // Note: Ideally getView itself should have returned ListView because that is the
        // only view inflated in this fragment but that gives ClassCastException, similar to the
        // one mentioned here - http://stackoverflow.com/questions/7318050/classcastexception-when-casting-to-viewpager
        // So when I created an unique id for this view (navigation_drawer_listview) and then
        // retrieve as below it could return ListView.
        ListView drawerListView = (ListView) navDrawerFragment.getView().
                findViewById(R.id.listview_navigation_drawer);
        //assertNotNull(drawerListView);
    }

    @Test
    public void testPlayerListFragmentIsNotFilledWithPlayerList() throws Exception {

        // Add a dummy country
        CountryEntity countryEntity = new CountryEntity("http://www.google.com", 1, "australia");

        PlayerListAdapter playerListAdapter = getPlayerListAdapter(countryEntity, null);

        assertTrue(playerListAdapter == null);
    }

    @Test
    public void testPlayerListFragmentIsFilledWithPlayerList() throws Exception {
        // Add a dummy country
        CountryEntity countryEntity = new CountryEntity("http://www.google.com", 1, "australia");

        // Add a dummy player
        ArrayList<PlayerEntity> playerEntityList = new ArrayList<PlayerEntity>();

        PlayerEntity playerEntity = new PlayerEntity();

        // One field is enough for simulation
        playerEntity.setCountryId(1);

        playerEntityList.add(playerEntity);

        PlayerListAdapter playerListAdapter = getPlayerListAdapter(countryEntity, playerEntityList);

        assertNotNull(playerListAdapter);

        // Check whether the drawer list has at least one item
        assertTrue(playerListAdapter.getCount() > 0);
    }

    private PlayerListAdapter getPlayerListAdapter(CountryEntity countryEntity,
                                                   ArrayList<PlayerEntity> playerEntityList) {

        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.player_list_fragment_container,
                        PlayerListFragment.newInstance(countryEntity))
                .commit();

        PlayerListFragment playerListFragment =
                (PlayerListFragment)fragmentManager.findFragmentById(R.id.player_list_fragment_container);

        assertNotNull(playerListFragment);

        // The below statement does not seem to work for fragments replaced in FrameLayout id
        // assertTrue(playerListFragment.isInLayout());

        // Simulate the callback that will be triggered on getting the country list
        playerListFragment.onDataFetched(
                PlayerProfileApiDataProvider.GET_PLAYER_LIST_FOR_COUNTRY_ID_API, playerEntityList);

        // Check whether drawer list view is populated
        ListView playerListView = (ListView)playerListFragment.getView().
                                                        findViewById(R.id.listview_playerlist);

        assertNotNull(playerListView);

        PlayerListAdapter playerListAdapter = (PlayerListAdapter) playerListView.getAdapter();

        return playerListAdapter;
    }

    private void populateCountryListAdapter() {

        ArrayList<CountryEntity> countryEntityList = getCountryEntityList();

        assertNotNull(countryEntityList);

        CountryListAdapter countryListAdapter = getCountryListAdapter(countryEntityList);

        assertNotNull(countryListAdapter);

        // Check whether the drawer list has at least one item
        assertTrue(countryListAdapter.getCount() > 0);
    }

    private ArrayList<CountryEntity> getCountryEntityList() {
        // Add a dummy country
        ArrayList<CountryEntity> countryEntityList = new ArrayList<CountryEntity>();
        CountryEntity countryEntity = new CountryEntity("http://www.google.com", 1, "australia");
        countryEntityList.add(countryEntity);

        return countryEntityList;
    }

    public CountryListAdapter getCountryListAdapter(ArrayList<CountryEntity> countryEntityList) {

        // Simulate the callback that will be triggered on getting the country list
        splashActivity.onDataFetched(PlayerProfileApiDataProvider.GET_COUNTRY_LIST_API,
                                                                                countryEntityList);

        CountryListAdapter countryListAdapter = ((PlayerProfileApp)splashActivity.getApplication()).
                                                                            getCountryListAdapter();

        return countryListAdapter;
    }
}