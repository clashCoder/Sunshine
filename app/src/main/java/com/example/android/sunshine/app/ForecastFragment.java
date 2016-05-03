package com.example.android.sunshine.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sunshine.app.data.WeatherContract;
import com.example.android.sunshine.app.sync.SunshineSyncAdapter;

/**
 * Created by itachiuchiha on 3/1/16.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends android.support.v4.app.Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private ForecastAdapter weatherAdapter;        //weather adapter for weather forecast
    private ListView mListView;
    private static final int NO_SELECTION_YET = -1;
    private int mPosition = NO_SELECTION_YET;
    private static final String SELECTION_KEY = "selected_position";
    private boolean mUseTodayLayout;

    /**
     * A callback interface that all activities containing this fragment must implement.
     * This mechanism allows activities to be notified of item selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    //private ListView listView;
    private static final int FORECAST_LOADER_ID = 0;
    private static final String[] FORECAST_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_COORD_LAT,
            WeatherContract.LocationEntry.COLUMN_COORD_LONG
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;
    static final int COL_LOCATION_SETTING = 5;
    static final int COL_WEATHER_CONDITION_ID = 6;
    static final int COL_COORD_LAT = 7;
    static final int COL_COORD_LONG = 8;
    //public final static String EXTRA_MESSAGE = "com.example.android.sunshine.app";

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        //let this fragment handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //create array of "fake" data


        /*ArrayList<String> weatherList = new ArrayList<String>();
        weatherList.add("Today - Sunny - 88/63");
        weatherList.add("Tomorrow - Foggy - 70/46");
        weatherList.add("Weds - Cloudy - 72/63");
        weatherList.add("Thurs - Rainy - 64/51");
        weatherList.add("Fri - Foggy - 70/46");
        weatherList.add("Sat - Sunny - 76/68");
        weatherList.add("Sun - Sunny - 82/63");*/

            /*code in tutorial in case my method does not work

            String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Weds - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                "Sun - Sunny - 80/68",
            };

            List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

             */

        //The ArrayAdapter will take data from a source and use it to populate the ListView it is
        //attached to.
        /*weatherAdapter = new ArrayAdapter<String>(
                //the current content(this fragment's parent activity)
                getActivity(),
                //ID of list item layout
                R.layout.list_item_forecast,
                //ID of textview to populate
                R.id.list_item_forecast_textview,
                //weather forecast data
                new ArrayList<String>());*/

        //listView = (ListView) this.findViewById(R.id.listview_forecast);
        //above does not work because we are not currently in a View object but a fragment object
        //so to get appropriate view we must get the root view from the fragment layout

        /*String locationSetting = Utility.getPreferredLocation(getActivity());

        // Sort order: Ascending, by date.
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis()
        );

        Cursor cur = getActivity().getContentResolver().query(weatherForLocationUri,
                null, null, null, sortOrder);*/

        // The CursorAdapter will take data from our cursor and populate the ListView
        // However, we cannot use FLAG_AUTO_REQUERY since it is deprecated, so we will end
        // up with an empty list the first time we run.

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String weatherInfo = (String) listView.getItemAtPosition(i);
                //Toast.makeText(getActivity(), weatherInfo, Toast.LENGTH_LONG).show();

                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                //startService(detailIntent);
                //detailIntent.putExtra(EXTRA_MESSAGE, weatherInfo);
                detailIntent.putExtra(Intent.EXTRA_TEXT, weatherInfo);
                startActivity(detailIntent);
            }
        });*/

        weatherAdapter = new ForecastAdapter(getActivity(), null, 0);
        weatherAdapter.setUseTodayLayout(mUseTodayLayout);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        mListView.setAdapter(weatherAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
//                ForecastAdapter adapter = (ForecastAdapter) adapterView.getAdapter();
//                adapter.setSelected(position,true);
//                adapter.notifyDataSetChanged();
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    String locationSetting = Utility.getPreferredLocation(getActivity());
//                    Intent intent = new Intent(getActivity(), DetailActivity.class)
//                            .setData(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
//                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
//                            ));
//                    startActivity(intent);
                    ((Callback) getActivity())
                            .onItemSelected(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
                            ));
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTION_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTION_KEY);
        }

        weatherAdapter.setUseTodayLayout(mUseTodayLayout);

        return rootView;
    }

    private void updateWeather() {
//        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity());
//
//        //Context context = getActivity();
//        //SharedPreferences userPref = context.getSharedPreferences("pref_general", Context.MODE_PRIVATE);
//
//        SharedPreferences userPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String zipCode = userPref.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
//        //fetchWeatherTask.execute("94040");
//        //Log.v("IN_OPTIONS", "Zipcode String: " + zipCode);
//        fetchWeatherTask.execute(zipCode);
//        Intent intent = new Intent(getActivity(), SunshineService.class);
//        intent.putExtra(SunshineService.LOCATION_QUERY_EXTRA,
//                Utility.getPreferredLocation(getActivity()));
//        getActivity().startService(intent);

//        Intent alarmIntent = new Intent(getActivity(), SunshineService.AlarmReceiver.class);
//        alarmIntent.putExtra(SunshineService.LOCATION_QUERY_EXTRA, Utility.getPreferredLocation(getActivity()));
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
//
//        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5*1000, pendingIntent);

        SunshineSyncAdapter.syncImmediately(getActivity());
    }

    void onLocationChanged() {
        updateWeather();
        getLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //updateWeather();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FORECAST_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String locationSetting = Utility.getPreferredLocation(getActivity());

        // Sort order: Ascending, by date.
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis()
        );


        return new CursorLoader(
                getActivity(),
                weatherForLocationUri,
                FORECAST_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in. (The framework will take care of closing
        // the old cursor once we return.)
        weatherAdapter.swapCursor(data);

        if (mPosition != NO_SELECTION_YET) {
            // If we don't need to restart the loader, and there's a desired position
            // to restore to, do so now.
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed. We need to make sure we are no
        // longer using it.
        weatherAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to NO_SELECTION_YET,
        // so check for that before storing.
        if (mPosition != NO_SELECTION_YET) {
            outState.putInt(SELECTION_KEY,mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;

        if (weatherAdapter != null) {
            weatherAdapter.setUseTodayLayout(mUseTodayLayout);
        }
    }
//    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
//
//        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
//
//        /* The date/time conversion code is going to be moved outside the asynctask later,
//         * so for convenience we're breaking it out into its own method now
//         * -- method obtained from udacity "Sunshine-Version-2" GitHub repository
//         */
//        private String getReadableDateString(long time) {
//            // Because the API returns a unix timestamp (measured in seconds),
//            // it must be converted to milliseconds in order to be converted to valid date.
//            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
//            return shortenedDateFormat.format(time);
//        }
//
//        /*
//         * Prepare the weather high/lows for presentation
//         * -- method obtained from udacity "Sunshine-Version-2" GitHub repository
//         */
//
//        private String formatHighLows(double high, double low, String unitType) {
//
//            //convert from metric to imperial
//            if (unitType.equals(getString(R.string.pref_temperature_unit_imperial))) {
//                high = (high * 1.8) + 32;
//                low = (low * 1.8) + 32;
//            }
//            else if (!unitType.equals(getString(R.string.pref_temperature_unit_metric))) {
//                Log.d(LOG_TAG, "Unit type not found: " + unitType);
//            }
//            //For presentation, assume the user doesn't care about tenths of a degree.
//            long roundedHigh = Math.round(high);
//            long roundedLow = Math.round(low);
//
//            String highLowStr = roundedHigh + "/" + roundedLow;
//            return highLowStr;
//        }
//
//        /*
//         * Take the String representing the complete forecast in JSON Format and
//         * pull out the data we need to construct the Strings needed for the wireframes.
//         *
//         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
//         * into an Object hierarchy for us.
//         * --method signature obtained from udacity "Sunshine-Version-2" GitHub repository
//         */
//        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
//            throws JSONException {
//
//
//            /*
//             * we want each string the array to be in the following format:
//             *
//             *     day - weatherCondition - maxTemperature/minTemperature
//             *
//             */
//
//            String[] data = new String[numDays];
//
//            //Calendar CurrentDay = Calendar.getInstance();
//            //C.add(Calendar.DATE,1); -> method to add a day to the calendar
//            //long time = CurrentDay.getTimeInMillis();
//            //String day = getReadableDateString(time);
//
//            //data[0] = getReadableDateString(time);
//
//            JSONObject forecastJsonObj = new JSONObject(forecastJsonStr);
//            JSONArray dataForecast = forecastJsonObj.getJSONArray("list");
//
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String unitType = sharedPreferences.getString(getString(R.string.pref_temperature_unit_key),
//                                    getString(R.string.pref_temperature_unit_metric));
//
//            for (int i = 0; i < numDays; i++) {
//
//                Calendar C = Calendar.getInstance();
//                C.add(Calendar.DATE,i);
//                long t = C.getTimeInMillis();
//                String day = getReadableDateString(t);
//                //data[i] = getReadableDateString(t);
//
//                JSONObject dataForecastObj = dataForecast.getJSONObject(i);
//
//                JSONObject temperatureObj = dataForecastObj.getJSONObject("temp");
//                double maxTemperature = temperatureObj.getDouble("max");
//                double minTemperature = temperatureObj.getDouble("min");
//                String temperature = formatHighLows(maxTemperature, minTemperature, unitType);
//
//                JSONArray weatherArr = dataForecastObj.getJSONArray("weather");
//                JSONObject weatherMain = weatherArr.getJSONObject(0);
//                String weatherCondition = weatherMain.getString("main");
//
//                String result = day + " - " + weatherCondition + " - " +
//                                    temperature;
//
//                data[i] = result;
//
//            }
//
//            /*for (String s: data) {
//                Log.v(LOG_TAG, "WeatherData is: " + s);
//            }*/
//
//
//            //Log.v(LOG_TAG, "Day is: " + day);
//
//            return data;
//        }
//
//
//        @Override
//        protected String[] doInBackground(String... params) {
//
//            //if string is empty then no postcode passed in
//            if (params.length == 0) {
//                return null;
//            }
//
//            String countryISO = "840";
//            String days = "7";
//            String q_val = params[0] + "," + countryISO;
//            String units = "metric";
//            String mode = "json";
//            String app_id = "put_your_API_Key_here";    //put API Key or will get FileNotFoundException
//
//
//            // These two need to be declared outside the try/catch
//            // so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            // Will contain the raw JSON response as a string.
//            String forecastJsonStr = null;
//
//            try {
//                // Construct the URL for the OpenWeatherMap query
//                // Possible parameters are avaiable at OWM's forecast API page, at
//                // http://openweathermap.org/API#forecast
//
//                Uri.Builder builder = new Uri.Builder();
//
//                builder.scheme("http")
//                        .authority("api.openweathermap.org")
//                        .appendPath("data")
//                        .appendPath("2.5")
//                        .appendPath("forecast")
//                        .appendPath("daily")
//                        .appendQueryParameter("q", q_val)
//                        .appendQueryParameter("mode", mode)
//                        .appendQueryParameter("units", units)
//                        .appendQueryParameter("cnt", days)
//                        .appendQueryParameter("appid", app_id);
//
//                String myUrl = builder.build().toString();
//                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94040,840&mode=json&units=metric&cnt=7&appid=insertAPIKEYHere");
//                URL url = new URL(myUrl);
//
//                Log.v(LOG_TAG, "Built URL from Uri.Builder " + myUrl);
//
//                // Create the request to OpenWeatherMap, and open the connection
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                forecastJsonStr = buffer.toString();
//
//                // to verify data returned is correct
//                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);
//
//            } catch (IOException e) {
//                Log.e("PlaceholderFragment", "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                return null;
//            } finally{
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("PlaceholderFragment", "Error closing stream", e);
//                    }
//                }
//            }
//
//            try {
//                int numOfDays = 7;
//                return getWeatherDataFromJson(forecastJsonStr,numOfDays);
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, e.getMessage(), e);
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//
//            if (result != null) {
//                weatherAdapter.clear();
//                for (String s: result) {
//                    weatherAdapter.add(s);
//                }
//            }
//        }
//    }


}