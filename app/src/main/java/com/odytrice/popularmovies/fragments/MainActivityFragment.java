package com.odytrice.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.activities.DetailActivity;
import com.odytrice.popularmovies.adapters.MovieTilesAdapter;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.tasks.FetchMoviesTask;
import com.odytrice.popularmovies.tasks.Action;
import com.odytrice.popularmovies.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    MovieTilesAdapter _movieAdapter;

    List<Movie> _movies;

    public MainActivityFragment() {
        _movies = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movieTiles);
        _movieAdapter = new MovieTilesAdapter(getActivity(), _movies);
        gridView.setAdapter(_movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = _movies.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Movie", movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {

        String sortOrder = PreferenceUtils.getSortOrder(getActivity());

        FetchMoviesTask fetchTask = new FetchMoviesTask(sortOrder, new Action<List<Movie>>() {
            @Override
            public void Invoke(List<Movie> result) {
                if (result != null) {
                    _movies.clear();
                    _movies.addAll(result);
                    _movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), R.string.fetch_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fetchTask.execute();
    }
}
