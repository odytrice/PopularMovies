package com.odytrice.popularmovies.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.SquaredImageView;
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
        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 820));
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        //Get Movie at Position
        if (position < 0 || position >= mMovies.size()) return null;
        Movie movie = mMovies.get(position);

        Picasso.with(getContext())
                .load(movie.poster_path)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder_error)
                .fit()
                .centerCrop()
                .into(imageView);
        return imageView;
    }
}
