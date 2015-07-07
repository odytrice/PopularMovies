package com.odytrice.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.odytrice.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieTilesAdapter extends ArrayAdapter<Movie> {

    private List<Movie> mMovies;

    public MovieTilesAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        mMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null) {
            //Get Movie at Position
            if (position < 0 || position >= mMovies.size()) return null;
            Movie movie = mMovies.get(position);

            imageView = new ImageView(getContext());
            Picasso.with(getContext()).load(movie.imageUrl).into(imageView);
        }
        else{
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
