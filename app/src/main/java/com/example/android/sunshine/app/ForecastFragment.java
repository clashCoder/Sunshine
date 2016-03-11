package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by itachiuchiha on 3/1/16.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends android.support.v4.app.Fragment {


    private ArrayAdapter<String> weatherAdapter;        //weather adapter for weather forecast
    private ListView listView;
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //create array of "fake" data


        ArrayList<String> weatherList = new ArrayList<String>();
        weatherList.add("Today - Sunny - 88/63");
        weatherList.add("Tomorrow - Foggy - 70/46");
        weatherList.add("Weds - Cloudy - 72/63");
        weatherList.add("Thurs - Rainy - 64/51");
        weatherList.add("Fri - Foggy - 70/46");
        weatherList.add("Sat - Sunny - 76/68");
        weatherList.add("Sun - Sunny - 82/63");

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
        weatherAdapter = new ArrayAdapter<String>(
                //the current content(this fragment's parent activity)
                getActivity(),
                //ID of list item layout
                R.layout.list_item_forecast,
                //ID of textview to populate
                R.id.list_item_forecast_textview,
                //weather forecast data
                new ArrayList<String>());

        //listView = (ListView) this.findViewById(R.id.listview_forecast);
        //above does not work because we are not currently in a View object but a fragment object
        //so to get appropriate view we must get the root view from the fragment layout
        listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(weatherAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });

        return rootView;
    }

    private void updateWeather() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();

        //Context context = getActivity();
        //SharedPreferences userPref = context.getSharedPreferences("pref_general", Context.MODE_PRIVATE);

        SharedPreferences userPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String zipCode = userPref.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        //fetchWeatherTask.execute("94040");
        Log.v("IN_OPTIONS", "Zipcode String: " + zipCode);
        fetchWeatherTask.execute(zipCode);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now
         * -- method obtained from udacity "Sunshine-Version-2" GitHub repository
         */
        private String getReadableDateString(long time) {
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /*
         * Prepare the weather high/lows for presentation
         * -- method obtained from udacity "Sunshine-Version-2" GitHub repository
         */

        private String formatHighLows(double high, double low) {
            //For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /*
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         * --method signature obtained from udacity "Sunshine-Version-2" GitHub repository
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {


            /*
             * we want each string the array to be in the following format:
             *
             *     day - weatherCondition - maxTemperature/minTemperature
             *
             */

            String[] data = new String[numDays];

            //Calendar CurrentDay = Calendar.getInstance();
            //C.add(Calendar.DATE,1); -> method to add a day to the calendar
            //long time = CurrentDay.getTimeInMillis();
            //String day = getReadableDateString(time);

            //data[0] = getReadableDateString(time);

            JSONObject forecastJsonObj = new JSONObject(forecastJsonStr);
            JSONArray dataForecast = forecastJsonObj.getJSONArray("list");

            for (int i = 0; i < numDays; i++) {

                Calendar C = Calendar.getInstance();
                C.add(Calendar.DATE,i);
                long t = C.getTimeInMillis();
                String day = getReadableDateString(t);
                //data[i] = getReadableDateString(t);

                JSONObject dataForecastObj = dataForecast.getJSONObject(i);

                JSONObject temperatureObj = dataForecastObj.getJSONObject("temp");
                double maxTemperature = temperatureObj.getDouble("max");
                double minTemperature = temperatureObj.getDouble("min");
                String temperature = formatHighLows(maxTemperature, minTemperature);

                JSONArray weatherArr = dataForecastObj.getJSONArray("weather");
                JSONObject weatherMain = weatherArr.getJSONObject(0);
                String weatherCondition = weatherMain.getString("main");

                String result = day + " - " + weatherCondition + " - " +
                                    temperature;

                data[i] = result;

            }

            /*for (String s: data) {
                Log.v(LOG_TAG, "WeatherData is: " + s);
            }*/


            //Log.v(LOG_TAG, "Day is: " + day);

            return data;
        }


        @Override
        protected String[] doInBackground(String... params) {

            //if string is empty then no postcode passed in
            if (params.length == 0) {
                return null;
            }

            String countryISO = "840";
            String days = "7";
            String q_val = params[0] + "," + countryISO;
            String units = "metric";
            String mode = "json";
            String app_id = "put_your_API_Key_here";    //put API Key or will get FileNotFoundException


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                Uri.Builder builder = new Uri.Builder();

                builder.scheme("http")
                        .authority("api.openweathermap.org")
                        .appendPath("data")
                        .appendPath("2.5")
                        .appendPath("forecast")
                        .appendPath("daily")
                        .appendQueryParameter("q", q_val)
                        .appendQueryParameter("mode", mode)
                        .appendQueryParameter("units", units)
                        .appendQueryParameter("cnt", days)
                        .appendQueryParameter("appid", app_id);

                String myUrl = builder.build().toString();
                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94040,840&mode=json&units=metric&cnt=7&appid=insertAPIKEYHere");
                URL url = new URL(myUrl);

                Log.v(LOG_TAG, "Built URL from Uri.Builder " + myUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                // to verify data returned is correct
                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                int numOfDays = 7;
                return getWeatherDataFromJson(forecastJsonStr,numOfDays);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {

            if (result != null) {
                weatherAdapter.clear();
                for (String s: result) {
                    weatherAdapter.add(s);
                }
            }
        }
    }

}