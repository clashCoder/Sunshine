package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        ArrayAdapter<String> weatherAdapter;        //weather adapter for weather forecast
        ListView listView;

        public PlaceholderFragment() {
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
                    weatherList);

            //listView = (ListView) this.findViewById(R.id.listview_forecast);
            //above does not work because we are not currently in a View object but a fragment object
            //so to get appropriate view we must get the root view from the fragment layout
            listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(weatherAdapter);


            return rootView;
        }
    }
}
