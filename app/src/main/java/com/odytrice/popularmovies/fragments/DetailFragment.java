package com.odytrice.popularmovies.fragments;

import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.data.MoviesContract;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.DateTimeUtility;
import com.odytrice.popularmovies.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 2;
    public static final String DETAIL_URI = "DetailUri";
    private Uri mUri;

    private View mRootView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            mUri = args.getParcelable(DETAIL_URI);
        } else {
            String sort_order = PreferenceUtils.getSortOrder(getActivity());
            Cursor defaultCursor = getActivity().getContentResolver().query(MoviesContract.MoviesEntry.getMoviesUri(), null, null, null, sort_order + " DESC LIMIT 1");
            if (defaultCursor != null && defaultCursor.moveToFirst())
                UpdateUI(new Movie(defaultCursor));
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void UpdateUI(Movie movie) {
        if (movie != null) {
            ((TextView) mRootView.findViewById(R.id.txt_movie_title)).setText(movie.title);

            String averageVotes = getString(R.string.txt_average_votes) + " " + movie.vote_average;
            String releasedString = getString(R.string.txt_released) + " " + DateTimeUtility.formatDate(movie.release_date);
            String popularity = getString(R.string.txt_popularity) + " " + Math.round(movie.popularity * 100.0) / 100.0;


            ((TextView) mRootView.findViewById(R.id.txt_movie_average)).setText(averageVotes);
            ((TextView) mRootView.findViewById(R.id.txt_popularity)).setText(popularity);


            ((TextView) mRootView.findViewById(R.id.txt_movie_release)).setText(releasedString);
            ((TextView) mRootView.findViewById(R.id.txt_movie_summary)).setText(movie.overview);

            ImageView posterImage = (ImageView) mRootView.findViewById(R.id.img_movie_poster);
            Picasso.with(getActivity())
                    .load("file://" + getActivity().getFilesDir() + "/images" + movie.poster_url)   //.placeholder(R.mipmap.loader)
                    .error(R.mipmap.loader_error)
                    .fit()
                    .centerCrop()
                    .into(posterImage);

            ImageView backdropImage = (ImageView) mRootView.findViewById(R.id.img_backdrop);
            Picasso.with(getActivity())
                    .load("file://" + getActivity().getFilesDir() + "/images" + movie.backdrop_path)   //.placeholder(R.mipmap.loader)
                    .error(R.mipmap.loader_error)
                    .fit()
                    .centerCrop()
                    .into(backdropImage);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null)
            return new CursorLoader(getActivity(), mUri, null, null, null, null);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            UpdateUI(new Movie(data));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public interface CallBack {

    }
}
