package com.odytrice.popularmovies.adapters;

import android.content.Context;
import android.text.style.LineHeightSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.TileImageView;
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

        TileImageView imageView;
        if (convertView == null) {
            imageView = new TileImageView(getContext());
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (TileImageView) convertView;
        }

        //Get Movie at Position
        if (position < 0 || position >= mMovies.size()) return null;
        Movie movie = mMovies.get(position);

        Picasso.with(getContext())
                .load(movie.poster_url)
                .placeholder(R.mipmap.loader)
                .error(R.mipmap.loader_error)
                .fit()
                .centerCrop()
                .into(imageView);
        return imageView;
    }
}
