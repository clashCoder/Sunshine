package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private final String DETAILFRAGMENT_TAG = "DFTAG";
    private String mLocation;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocation = Utility.getPreferredLocation(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.weather_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the dtail fragment using a fragment transaction
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
//                    .commit();
//        }
        //Log.v(LOG_TAG, "onCreate() called.\n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        if (id == R.id.action_map) {
            //TODO: Intent to view preferred Location

            getPreferredLocationInMap();
            /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            String zipCode = sharedPreferences.getString(getString(R.string.pref_location_key),
                    getString(R.string.pref_location_default));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                    .appendQueryParameter("q", zipCode)
                    .build();
            intent.setData(geoLocation);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            else {
                Log.d(LOG_TAG, "No apps installed to view" + zipCode);
            }*/

        }

        return super.onOptionsItemSelected(item);
    }

    private void getPreferredLocationInMap() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String zipCode = sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", zipCode)
                .build();
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d(LOG_TAG, "No apps installed to view" + zipCode);
        }

    }

    @Override
    protected  void onStart() {
        super.onStart();
        //Log.v(LOG_TAG, "onStart() called.\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.v(LOG_TAG, "onResume() called.\n");
        String location = Utility.getPreferredLocation(this);

        // update the location in our second panel using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
            if (ff != null) {
                ff.onLocationChanged();
            }
            mLocation = location;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.v(LOG_TAG, "onPause() called.\n");
    }


    @Override
    protected void onStop() {
        super.onStop();
        //Log.v(LOG_TAG, "onStop() called.\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.v(LOG_TAG, "onDestroy() called.\n");
    }

}

