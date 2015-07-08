package com.odytrice.popularmovies.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        SquaredImageView imageView;

        if (convertView == null) {
            //Get Movie at Position
            if (position < 0 || position >= mMovies.size()) return null;
            Movie movie = mMovies.get(position);

            imageView = new SquaredImageView(getContext());

            imageView.setPadding(0,0,0,0);

            Picasso.with(getContext())
                    .load(movie.imageUrl)
                    .placeholder(R.mipmap.placeholder)
                    .error(R.mipmap.placeholder_error)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView = (SquaredImageView) convertView;
        }
        return imageView;
    }
}
