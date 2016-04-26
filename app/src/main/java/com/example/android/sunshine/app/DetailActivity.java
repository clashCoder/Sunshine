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
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.weather_detail_container, fragment).commit();
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

//    public static class DetailFragment extends Fragment implements LoaderCallbacks<Cursor>{
//
//        private String weatherInfo;
//        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
//        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
//        private ShareActionProvider mShareActionProvider;
//
//        private static final int DETAIL_LOADER_ID = 0;
//
//        private static final String[] FORECAST_COLUMNS = {
//                WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
//                WeatherContract.WeatherEntry.COLUMN_DATE,
//                WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
//                WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
//                WeatherContract.WeatherEntry.COLUMN_MIN_TEMP
//        };
//
//        // these constants correspond to the projection defined above, and must change if
//        // the projection changes
//        private static final int COL_WEATHER_ID = 0;
//        private static final int COL_WEATHER_DATE = 1;
//        private static final int COL_WEATHER_DESC = 2;
//        private static final int COL_WEATHER_MAX_TEMP = 3;
//        private static final int COL_WEATHER_MIN_TEMP = 4;
//
//
//
//        public DetailFragment() {
//            setHasOptionsMenu(true);
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//
//            /*Intent intent = getActivity().getIntent();
//
//            if (intent != null) {
//                weatherInfo = intent.getDataString();
//            }*/
//
//            /*if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
//                weatherInfo = intent.getStringExtra(Intent.EXTRA_TEXT);
//
//                // get TextView object present in fragment_detail.xml
//                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
//
//                // set the text of the TextView to that of the tab pressed
//                textView.setText(weatherInfo);
//            }*/
//
//            /*if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
//                weatherInfo = intent.getStringExtra(Intent.EXTRA_TEXT);
//            }*/
//
//            /*if (weatherInfo != null) {
//                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
//                textView.setText(weatherInfo);
//            }*/
//
//            return rootView;
//        }
//
//        @Override
//        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            //Inflate the menu; this adds items to the action bar if it is present
//            inflater.inflate(R.menu.detailfragment, menu);
//
//            //Retrieve the share menu item
//            MenuItem menuItem = menu.findItem(R.id.action_share);
//
//
//            // Get the provider and hold onto it to set/change the share intent.
//            /*ShareActionProvider mShareActionProvider =
//                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);*/
//
//            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
//
//            if (weatherInfo != null) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, weatherInfo + FORECAST_SHARE_HASHTAG);
//                mShareActionProvider.setShareIntent(intent);
//            }
//
//        }
//
//        @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//            getLoaderManager().initLoader(DETAIL_LOADER_ID, null, this);
//            super.onActivityCreated(savedInstanceState);
//        }
//
//        @Override
//        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            Log.v(LOG_TAG, "In onCreateLoader");
//            Intent intent = getActivity().getIntent();
//
//            if (intent == null) {
//                return null;
//            }
//
//            // Now create and return a CursorLoader that will take care of creating a Cursor for the
//            // data being displayed.
//            return new CursorLoader(
//                    getActivity(),
//                    intent.getData(),
//                    FORECAST_COLUMNS,
//                    null,
//                    null,
//                    null
//            );
//        }
//
//        @Override
//        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//            Log.v(LOG_TAG, "In onLoadFinished");
//
//            if (!data.moveToFirst()) {
//                return;
//            }
//
//            String dateString = Utility.formatDate(data.getLong(COL_WEATHER_DATE));
//            String weatherDesc = data.getString(COL_WEATHER_DESC);
//            boolean isMetric = Utility.isMetric(getActivity());
//            String high = Utility.formatTemperature(getActivity(),
//                    data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);
//            String low = Utility.formatTemperature(getActivity(),
//                    data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);
//
//            weatherInfo = String.format("%s - %s - %s/%s", dateString, weatherDesc, high, low);
//            Log.d(LOG_TAG, "weatherInfo: " + weatherInfo);
//
//            TextView detailTextView = (TextView) getView().findViewById(R.id.fragment_detail_text);
//            detailTextView.setText(weatherInfo);
//
//            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
//            if (mShareActionProvider != null) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, weatherInfo + FORECAST_SHARE_HASHTAG);
//                mShareActionProvider.setShareIntent(shareIntent);
//            }
//        }
//
//        @Override
//        public void onLoaderReset(Loader<Cursor> loader) {}
//    }

}
