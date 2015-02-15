package yer.at.sunshine.app;

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

;


public class DetailActivity extends ActionBarActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static String forecast ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
        Log.d(LOG_TAG, "onCreate() ");
        Intent detailedIntent = getIntent();
         forecast = detailedIntent.getStringExtra("forecast");
        Log.d(LOG_TAG, "onCreate() forecast: " + forecast );



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(LOG_TAG, "onOptionsItemSelected()  "  );
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_detailsettings) {
            Log.d(LOG_TAG, "onOptionsItemSelected() action_settings : " + id );
            Intent settingsIntent = new Intent(DetailActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private final String LOG_TAG = DetailFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mForecastStr;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // TextView detailedText = (TextView) rootView.findViewById(R.id.detailedText);
            // detailedText.setText(forecast);

            Intent myIntent = getActivity().getIntent();
            if (myIntent != null && myIntent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = myIntent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.detailedText))
                        .setText(mForecastStr);
            }
            return rootView;
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    mForecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater myInflater) {

            Log.d(LOG_TAG, "onCreateOptionsMenu() ");

            // Inflate the menu; this adds items to the action bar if it is present.
            myInflater.inflate(R.menu.detailfragment, menu);

            // Find the MenuItem that we know has the ShareActionProvider
            MenuItem myItem = menu.findItem(R.id.action_share);

            // Get its ShareActionProvider
            ShareActionProvider mShareActionProvider;
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(myItem);


            // Connect the dots: give the ShareActionProvider its Share Intent
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG, "onCreateOptionsMenu() ");
            }


            return ;
        }

    }
}
