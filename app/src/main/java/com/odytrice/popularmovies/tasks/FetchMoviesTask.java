package com.odytrice.popularmovies.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.odytrice.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {
    private Action<List<Movie>> _result;
    private final static String API_KEY = "4d3c9166c993a76b9b5ed64ca04e8ce4";

    private String _sortOrder;

    public FetchMoviesTask(String sortOrder, Action<List<Movie>> result) {
        _sortOrder = sortOrder;
        _result = result;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        //TODO: Call Background Service and Return Movies
        HttpURLConnection urlConnection = null;
        try {
            URL url = buildUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return parseMoviesFromJson(ReadStream(in));
        } catch (Exception ex) {
            Log.e(FetchMoviesTask.class.toString(), "Error Fetching Data: " + ex.getMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    @NonNull
    private URL buildUrl() throws MalformedURLException {

        String movieApiUrl = "http://api.themoviedb.org/3/discover/movie";

        Uri builtUri = Uri.parse(movieApiUrl).buildUpon()
                .appendQueryParameter("sort_by", _sortOrder + ".desc")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return new URL(builtUri.toString());
    }

    private List<Movie> parseMoviesFromJson(String jsonString) throws JSONException, ParseException {

        List<Movie> movies = new ArrayList<>();

        final String POSTER_URL_PATH = "http://image.tmdb.org/t/p/w342/";

        JSONObject response = new JSONObject(jsonString);

        JSONArray results = response.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            Movie movie = new Movie();
            JSONObject movieJson = results.getJSONObject(i);
            movie.adult = movieJson.getBoolean("adult");
            movie.backdrop_path = movieJson.getString("backdrop_path");
            movie.id = movieJson.getInt("id");
            movie.original_language = movieJson.getString("original_language");
            movie.original_title = movieJson.getString("original_title");
            movie.overview = movieJson.getString("overview");
            movie.release_date = parseDate(movieJson.getString("release_date"));
            movie.poster_path = POSTER_URL_PATH + movieJson.getString("poster_path");
            movie.popularity = movieJson.getDouble("popularity");
            movie.title = movieJson.getString("title");
            movie.video = movieJson.getBoolean("video");
            movie.vote_average = movieJson.getDouble("vote_average");
            movie.vote_count = movieJson.getInt("vote_count");
            movie.genre_ids = parseJsonIDs(movieJson.getJSONArray("genre_ids"));
            movies.add(movie);
        }

        return movies;
    }

    private Date parseDate(String dateString) {
        Date date = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(dateString);
        }catch (ParseException pex){

        }
        return date;
    }

    private int[] parseJsonIDs(JSONArray genre_ids) throws JSONException {
        int[] ints = new int[genre_ids.length()];
        for (int i = 0; i < genre_ids.length(); i++) {
            ints[i] = genre_ids.getInt(i);
        }
        return ints;
    }

    private String ReadStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        _result.Invoke(movies);
    }
}