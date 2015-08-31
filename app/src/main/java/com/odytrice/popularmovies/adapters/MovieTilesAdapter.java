package com.odytrice.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.text.style.LineHeightSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.TileImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieTilesAdapter extends CursorAdapter {

    public MovieTilesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        TileImageView imageView;
        imageView = new TileImageView(context);
        imageView.setPadding(0, 0, 0, 0);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TileImageView imageView = (TileImageView) view;

        Movie movie = new Movie(cursor);

        //Get Movie at Position
        Picasso.with(context)
                .load("file://"+context.getFilesDir() + "/images" + movie.poster_url)
                .error(R.mipmap.loader_error)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
