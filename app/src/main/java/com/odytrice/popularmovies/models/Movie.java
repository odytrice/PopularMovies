package com.odytrice.popularmovies.models;

public class Movie {
    public int _id;
    public String imageUrl;

    public Movie(int id, String url){
        _id = id;
        imageUrl = url;
    }
}
