package com.odytrice.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ody on 8/23/2015.
 */
// This class represents the Contract between the consumers of the Provider and the Content Provider
// itself. Note that Content Provider entries do not necessarily map to the database schema

public class MoviesContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.odytrice.popularmovies";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MoviesEntry {

        public static String TABLE_NAME = Database.Movies.class.getSimpleName();

        public static final class Columns implements BaseColumns {
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

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static Uri getMoviesUri() {
            return CONTENT_URI;
        }

        public static Uri getMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
