package com.odytrice.popularmovies.tasks;

import android.os.AsyncTask;

import com.odytrice.popularmovies.models.Movie;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    Action<List<Movie>> _result;
    private final static String API_KEY = "4d3c9166c993a76b9b5ed64ca04e8ce4";

    public FetchMoviesTask(Action<List<Movie>> result) {
        _result = result;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        //TODO: Call Background Service and Return Movies
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return parseMoviesFromJson(ReadStream(in));
        } catch (Exception ex) {

        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    private List<Movie> parseMoviesFromJson(String jsonString) {
        return null;
    }

    private String ReadStream(InputStream inputStream) {
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        _result.Invoke(movies);
    }
}