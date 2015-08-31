package com.odytrice.popularmovies.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.odytrice.popularmovies.data.Database;
import com.odytrice.popularmovies.data.MoviesContract;
import com.odytrice.popularmovies.utils.DateTimeUtility;

import java.util.Date;

public class Movie implements Parcelable {

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


    public int id;
    public int movie_id;
    public boolean adult;
    public String backdrop_path;
    public String original_title;
    public String overview;
    public Date release_date;
    public String poster_url;
    public double popularity;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public int favorite;

    public Movie() {

    }

    public Movie(Cursor cursor) {
        id = cursor.getInt(0);
        movie_id = cursor.getInt(1);
        adult = cursor.getInt(2) > 0;
        backdrop_path = cursor.getString(3);
        original_title = cursor.getString(4);
        overview = cursor.getString(5);
        popularity = cursor.getDouble(6);
        poster_url = cursor.getString(7);
        release_date = DateTimeUtility.parseDate(cursor.getString(8));
        title = cursor.getString(9);
        video = cursor.getInt(10) > 0;
        vote_average = cursor.getDouble(11);
        vote_count = cursor.getInt(12);
        favorite = cursor.getInt(13);
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        movie_id = in.readInt();
        backdrop_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        poster_url = in.readString();
        release_date = DateTimeUtility.parseDate(in.readString());
        popularity = in.readDouble();
        title = in.readString();
        vote_average = in.readDouble();
        vote_count = in.readInt();
        favorite = in.readInt();
    }

    protected Movie(ContentValues contentValues) {
        id = contentValues.getAsInteger(MoviesContract.MoviesEntry.Columns._ID);
        movie_id = contentValues.getAsInteger(MoviesContract.MoviesEntry.Columns.MovieID);
        backdrop_path = contentValues.getAsString(MoviesContract.MoviesEntry.Columns.BackdropPath);
        original_title = contentValues.getAsString(MoviesContract.MoviesEntry.Columns.OriginalTitle);
        overview = contentValues.getAsString(MoviesContract.MoviesEntry.Columns.Overview);
        release_date = DateTimeUtility.parseDate(contentValues.getAsString(MoviesContract.MoviesEntry.Columns.ReleaseDate));
        poster_url = contentValues.getAsString(MoviesContract.MoviesEntry.Columns.PosterUrl);
        popularity = contentValues.getAsDouble(MoviesContract.MoviesEntry.Columns.Popularity);
        title = contentValues.getAsString(MoviesContract.MoviesEntry.Columns.Title);
        vote_average = contentValues.getAsDouble(MoviesContract.MoviesEntry.Columns.VoteAverage);
        vote_count = contentValues.getAsInteger(MoviesContract.MoviesEntry.Columns.VoteCount);
        favorite = contentValues.getAsInteger(MoviesContract.MoviesEntry.Columns.Favorite);
        video = contentValues.getAsBoolean(MoviesContract.MoviesEntry.Columns.Video);
        adult = contentValues.getAsBoolean(MoviesContract.MoviesEntry.Columns.Adult);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movie_id);
        dest.writeString(backdrop_path);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(poster_url);
        dest.writeString( DateTimeUtility.formatDate(release_date));
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
        dest.writeInt(favorite);
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.Columns.MovieID, movie_id);
        contentValues.put(MoviesContract.MoviesEntry.Columns.BackdropPath, backdrop_path);
        contentValues.put(MoviesContract.MoviesEntry.Columns.OriginalTitle, original_title);
        contentValues.put(MoviesContract.MoviesEntry.Columns.Overview, overview);
        contentValues.put(MoviesContract.MoviesEntry.Columns.PosterUrl, poster_url);
        contentValues.put(MoviesContract.MoviesEntry.Columns.Popularity, popularity);
        contentValues.put(MoviesContract.MoviesEntry.Columns.ReleaseDate, DateTimeUtility.formatDate(release_date));
        contentValues.put(MoviesContract.MoviesEntry.Columns.Title, title);
        contentValues.put(MoviesContract.MoviesEntry.Columns.VoteAverage, vote_average);
        contentValues.put(MoviesContract.MoviesEntry.Columns.VoteCount, vote_count);
        contentValues.put(MoviesContract.MoviesEntry.Columns.Favorite, favorite);
        contentValues.put(MoviesContract.MoviesEntry.Columns.Video, video);
        contentValues.put(MoviesContract.MoviesEntry.Columns.Adult, adult);
        return contentValues;
    }
}