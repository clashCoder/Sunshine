/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new DetailFragment()).commit();
        }

        // get Intent that started 'Activity' and retrieve data
        /*Intent intent = getIntent();
        String message = intent.getStringExtra(ForecastFragment.EXTRA_MESSAGE);

        //display the message
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        private String weatherInfo;
        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();

            /*if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                weatherInfo = intent.getStringExtra(Intent.EXTRA_TEXT);

                // get TextView object present in fragment_detail.xml
                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);

                // set the text of the TextView to that of the tab pressed
                textView.setText(weatherInfo);
            }*/

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                weatherInfo = intent.getStringExtra(Intent.EXTRA_TEXT);
            }

            if (weatherInfo != null) {
                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
                textView.setText(weatherInfo);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            //Inflate the menu; this adds items to the action bar if it is present
            inflater.inflate(R.menu.detailfragment, menu);

            //Retrieve the share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);


            // Get the provider and hold onto it to set/change the share intent.
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (mShareActionProvider != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, weatherInfo + FORECAST_SHARE_HASHTAG);
                mShareActionProvider.setShareIntent(intent);
            }
            else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }



        }
    }

}
