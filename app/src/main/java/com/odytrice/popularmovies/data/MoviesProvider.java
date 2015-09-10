package com.odytrice.popularmovies.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;


public class MoviesProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static final int MOVIES = 0;
    public static final int MOVIE = 1;
    private MoviesDbHelper _dbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES + "/#", MOVIE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        _dbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            // "movies"
            case MOVIES: {
                retCursor = getMovies(uri, projection, selection, selectionArgs, sortOrder);
                break;
            }
            // "movies/#"
            case MOVIE: {
                retCursor = getMovie(uri, projection);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getMovies(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return _dbHelper.getReadableDatabase().query(MoviesContract.MoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getMovie(Uri uri, String[] projection) {

        String selection = Database.Movies.MovieID + " = ?";

        String[] selectionArgs = new String[]{uri.getLastPathSegment()};

        return _dbHelper.getReadableDatabase().query(MoviesContract.MoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = uriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE;
            case MOVIES:
                return MoviesContract.MoviesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES: {
                long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MoviesContract.MoviesEntry.getMovieUri(values.getAsInteger(MoviesContract.MoviesEntry.Columns.MovieID));
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();

        int rowsAffected;

        //Only Allow Users to delete a single Entity at a Time
        selection = Database.Movies._ID + " = ?";
        selectionArgs = new String[]{uri.getLastPathSegment()};

        switch (uriMatcher.match(uri)) {
            case MOVIE: {
                rowsAffected = db.delete(MoviesContract.MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }


        if (rowsAffected != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();

        int rowsAffected;

        //Only Allow Users to delete a single Entity at a Time
        selection = Database.Movies.MovieID + " = ?";
        selectionArgs = new String[]{uri.getLastPathSegment()};

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                rowsAffected = db.update(MoviesContract.MoviesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (rowsAffected != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        _dbHelper.close();
        super.shutdown();
    }
}
