package com.odytrice.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by Ody on 8/23/2015.
 */
public class MoviesDbHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "popularmovies.db";

    MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + Database.Movies.class.getSimpleName() + " (" +
                Database.Movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Database.Movies.MovieID + " INTEGER NOT NULL, " +
                Database.Movies.Adult + " INTEGER NULL, " +
                Database.Movies.BackdropPath + " TEXT NULL," +
                Database.Movies.OriginalTitle + " TEXT NULL," +
                Database.Movies.Overview + " TEXT NULL," +
                Database.Movies.Popularity + " REAL NULL," +
                Database.Movies.PosterUrl + " TEXT NULL," +
                Database.Movies.ReleaseDate + " TEXT NULL," +
                Database.Movies.Title + " TEXT NULL," +
                Database.Movies.Video + " INTEGER NULL," +
                Database.Movies.VoteAverage + " REAL NULL," +
                Database.Movies.VoteCount + " INTEGER NULL," +
                Database.Movies.Favorite + " INTEGER NULL," +
        // per movieID, it's created a UNIQUE constraint with REPLACE strategy
        " UNIQUE (" + Database.Movies.MovieID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Database.Movies.class.getSimpleName());
        onCreate(db);
    }
}
