package com.odytrice.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Ody on 8/23/2015.
 */
// This class represents the Database schema to be used by it's single client ie the Content
// Provider.
public class Database {

    public class Movies implements BaseColumns {
        public static final String MovieID = "id";
        public static final String Adult = "adult";
        public static final String BackdropPath = "backdrop_path";
        public static final String OriginalTitle = "original_title";
        public final static String Overview = "overview";
        public final static String ReleaseDate = "release_date" ;
        public final static String PosterUrl = "poster_url";
        public final static String Popularity = "popularity";
        public final static String Title = "title";
        public final static String Video = "video";
        public final static String VoteAverage = "vote_average";
        public final static String VoteCount = "vote_count";
        public final static String Favorite = "favorite";
    }
}
