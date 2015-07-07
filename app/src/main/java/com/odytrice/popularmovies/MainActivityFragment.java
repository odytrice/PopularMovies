package com.odytrice.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.odytrice.popularmovies.adapters.MovieTilesAdapter;
import com.odytrice.popularmovies.models.Movie;

import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    MovieTilesAdapter mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movieTiles);

        Movie[] movies = {
                new Movie(1, "http://i.imgur.com/DvpvklR.png"),
                new Movie(2, "http://i.imgur.com/DvpvklR.png")
        };


        mMovieAdapter = new MovieTilesAdapter(getActivity(), Arrays.asList(movies));

        gridView.setAdapter(mMovieAdapter);

        return rootView;
    }
}
