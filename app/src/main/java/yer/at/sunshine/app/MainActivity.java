package yer.at.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "onCreate() ");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
        myContext = getApplicationContext();
        Log.d(LOG_TAG, "onCreate() finished ");
    }

    public boolean onCreateOptionsMenuDelegate(Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenuDelegate()  ");
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()  ");
        return onCreateOptionsMenuDelegate(menu, getMenuInflater());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(LOG_TAG, "onOptionsItemSelected() ");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main_settings) {
            Log.d(LOG_TAG, "onOptionsItemSelected() action_main_settings : " + id);

            Intent settingsIntent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        if (id == R.id.action_main_map) {
            Log.d(LOG_TAG, "onOptionsItemSelected() action_main_map : " + id);
            openPreferredLocationInMap();
            return true;
        }

        Log.d(LOG_TAG, "onOptionsItemSelected() id unknown: " + id );

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {

        Log.d(LOG_TAG, "openPreferredLocationInMap() ");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String myLocation = sharedPref.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", myLocation).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "openPreferredLocationInMap() couldn't call map-app " + myLocation);
        }

    }
}
