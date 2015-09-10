package com.odytrice.popularmovies.fragments;

import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.adapters.MovieTilesAdapter;
import com.odytrice.popularmovies.data.MoviesContract;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.tasks.Action;
import com.odytrice.popularmovies.tasks.FetchMoviesTask;
import com.odytrice.popularmovies.utils.PreferenceUtils;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    MovieTilesAdapter _movieAdapter;

    int MOVIES_LOADER = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movieTiles);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }


        _movieAdapter = new MovieTilesAdapter(getActivity(), null, 0);
        gridView.setAdapter(_movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    Movie movie = new Movie(cursor);
                    Uri movieUri = MoviesContract.MoviesEntry.getMovieUri(movie.movie_id);
                    ((CallBack) getActivity()).onMovieSelection(movieUri);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PreferenceUtils.getSortOrder(getActivity());

        Uri moviesUri = MoviesContract.MoviesEntry.getMoviesUri();

        return new CursorLoader(getActivity(), moviesUri, null, null, null, sortOrder + " DESC LIMIT 20");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _movieAdapter.swapCursor(null);
    }

    public void onSettingChanged() {
        //Fectch New Data and Refresh UI when Done
        FetchMoviesTask fetchTask = new FetchMoviesTask(getActivity(), new Action<Void>() {
            @Override
            public void Invoke(Void result) {
                Toast.makeText(getActivity(), "Refreshing UI", Toast.LENGTH_SHORT).show();
                getLoaderManager().restartLoader(MOVIES_LOADER, null, MoviesFragment.this);
            }
        });
        Toast.makeText(getActivity(), "Fetching Movie Data", Toast.LENGTH_LONG).show();
        fetchTask.execute();
    }

    public interface CallBack {
        void onMovieSelection(Uri movieUri);
    }
}
