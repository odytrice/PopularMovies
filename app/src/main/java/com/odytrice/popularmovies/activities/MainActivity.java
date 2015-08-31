package com.odytrice.popularmovies.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.fragments.MainActivityFragment;
import com.odytrice.popularmovies.utils.PreferenceUtils;


public class MainActivity extends ActionBarActivity {

    private String mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instructs Android to Set Default Values if they did not exist
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mSortOrder = PreferenceUtils.getSortOrder(this);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check to see if Sort Order has changed
        if(mSortOrder != PreferenceUtils.getSortOrder(this)){
            //Notify Fragment that Sort Order has Changed
            MainActivityFragment fragment = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.movies_fragment);
            if(fragment != null){
                fragment.onSettingChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
