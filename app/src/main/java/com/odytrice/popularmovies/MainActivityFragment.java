package com.odytrice.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.odytrice.popularmovies.adapters.MovieTilesAdapter;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.tasks.FetchMoviesTask;
import com.odytrice.popularmovies.tasks.Action;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    MovieTilesAdapter _movieAdapter;

    List<Movie> _movies;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movieTiles);
        _movieAdapter = new MovieTilesAdapter(getActivity(), _movies);
        gridView.setAdapter(_movieAdapter);

        FetchMoviesTask fetchTask = new FetchMoviesTask(new Action<List<Movie>>() {
            @Override
            public void Invoke(List<Movie> result) {
                if (result != null) {
                    _movies.clear();
                    _movies.addAll(result);
                    _movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "An Error Occured Retrieving Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fetchTask.execute();
        return rootView;
    }
}
