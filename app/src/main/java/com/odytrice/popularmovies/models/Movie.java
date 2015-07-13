package com.odytrice.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Movie implements Parcelable {
    public int id;
    public boolean adult;
    public String backdrop_path;
    public int[] genre_ids;
    public String original_language;
    public String original_title;
    public String overview;
    public Date release_date;
    public String poster_url;
    public double popularity;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;

    public Movie(){

    }

    protected Movie(Parcel in) {
        id = in.readInt();
        backdrop_path = in.readString();
        genre_ids = in.createIntArray();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        poster_url = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        vote_average = in.readDouble();
        vote_count = in.readInt();
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
        dest.writeString(backdrop_path);
        dest.writeIntArray(genre_ids);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(poster_url);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
    }
}