package com.odytrice.popularmovies.models;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
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
}