package com.odytrice.popularmovies.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.odytrice.popularmovies.R;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.DateTimeUtility;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    Movie _movie;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //Get Movie Information
        Object extraObject = getActivity().getIntent().getParcelableExtra("Movie");
        if (extraObject instanceof Movie) {
            _movie   = (Movie) extraObject;
        }

        UpdateUI(rootView,_movie);
        return rootView;
    }

    private void UpdateUI(View root,Movie movie) {
        if(movie != null){
            ((TextView)root.findViewById(R.id.txt_movie_title)).setText(movie.title);

            String averageVotes = getString(R.string.txt_average_votes) + " " + movie.vote_average;
            String releasedString = getString(R.string.txt_released) + " " + DateTimeUtility.formatDate(movie.release_date);
            String popularity = getString(R.string.txt_popularity) + " " + Math.round(movie.popularity * 100.0) / 100.0;


            ((TextView)root.findViewById(R.id.txt_movie_average)).setText(averageVotes);
            ((TextView)root.findViewById(R.id.txt_popularity)).setText(popularity);


            ((TextView)root.findViewById(R.id.txt_movie_release)).setText(releasedString);
            ((TextView)root.findViewById(R.id.txt_movie_summary)).setText(movie.overview);

            ImageView imageView = (ImageView)root.findViewById(R.id.img_movie_poster);
            Picasso.with(getActivity())
                    .load(movie.poster_url)
                    .placeholder(R.mipmap.loader)
                    .error(R.mipmap.loader_error)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    }
}
