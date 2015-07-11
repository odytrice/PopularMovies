package com.odytrice.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.odytrice.popularmovies.R;

/**
 * Created by Ody on 7/11/2015.
 */
public class PreferenceUtils {

    public static String getSortOrder(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_movie_sort_key),context.getString(R.string.pref_movie_sort_default));
    }
}
