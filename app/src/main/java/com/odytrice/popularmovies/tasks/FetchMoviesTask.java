package com.odytrice.popularmovies.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.odytrice.popularmovies.data.MoviesContract;
import com.odytrice.popularmovies.models.Movie;
import com.odytrice.popularmovies.utils.DateTimeUtility;
import com.odytrice.popularmovies.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FetchMoviesTask extends AsyncTask<Void, Void, Void> {
    private Action<Void> _result;
    private final static String API_KEY = "4d3c9166c993a76b9b5ed64ca04e8ce4";

    private Context _context;

    public FetchMoviesTask(Context context, Action<Void> result) {
        _context = context;
        _result = result;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //TODO: Call Background Service and Return Movies
        HttpURLConnection urlConnection = null;
        try {
            URL url = buildUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            List<Movie> movies = parseMoviesFromJson(ReadStream(in));
            FetchImages(movies, 3);
            SaveMovies(movies);
        } catch (MalformedURLException e) {
            Log.e(FetchMoviesTask.class.toString(), "Error Malformed Url: " + e.getMessage());
        } catch (ParseException e) {
            Log.e(FetchMoviesTask.class.toString(), "Error Parsing Json Data: " + e.getMessage());
        } catch (JSONException e) {
            Log.e(FetchMoviesTask.class.toString(), "Error Parsing Json Data: " + e.getMessage());
        } catch (IOException e) {
            Log.e(FetchMoviesTask.class.toString(), "Network Error: " + e.getMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    private void FetchImages(List<Movie> movies, int index) {
        for (Movie movie : movies) {
            //Download Image File
            String[] sizes = new String[]{"w92", "w154", "w185", "w342", "w500", "w780", "original"};
            final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/" + sizes[index] + "/";

            //Download Poster
            Bitmap image = downloadImage(BASE_IMAGE_URL + movie.poster_url);
            saveImage(movie.poster_url, image);


            //Download Backdrop
            Bitmap bdImage = downloadImage(BASE_IMAGE_URL + movie.backdrop_path);
            saveImage(movie.poster_url, bdImage);
        }
    }

    private Bitmap downloadImage(String _url) {
        //Prepare to download image
        URL url;
        InputStream in;
        BufferedInputStream buf;

        try {
            url = new URL(_url);
            in = url.openStream();
            // Read the inputstream
            buf = new BufferedInputStream(in);
            // Convert the BufferedInputStream to a Bitmap
            Bitmap bMap = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }
            return bMap;
        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }
        return null;
    }

    private void saveImage(String urlPath, Bitmap bmp) {
        try {
            //Compress BMP to JPEG
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            //Create File if not exists
            File imageDir = new File(_context.getFilesDir() + "/images");
            if (!imageDir.exists()) imageDir.mkdir();
            File file = new File(imageDir.getPath() + urlPath);
            file.createNewFile();

            //Write Image to the File
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SaveMovies(List<Movie> movies) {
        //TODO: Save Movies to the Database
        Vector<ContentValues> cvVector = new Vector<>();
        for (Movie movie : movies) {
            cvVector.add(movie.toContentValues());
        }

        ContentValues[] cvArray = new ContentValues[cvVector.size()];
        cvVector.toArray(cvArray);
        _context.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.getMoviesUri(), cvArray);
    }

    @NonNull
    private URL buildUrl() throws MalformedURLException {

        String movieApiUrl = "http://api.themoviedb.org/3/discover/movie";

        String sort_order = PreferenceUtils.getSortOrder(_context);

        //Change Sort Popularity to Default if Favorite
        if (sort_order == "favorite") sort_order = "popularity";

        Uri builtUri = Uri.parse(movieApiUrl).buildUpon()
                .appendQueryParameter("sort_by", sort_order + ".desc")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return new URL(builtUri.toString());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        _result.Invoke(aVoid);
    }

    private List<Movie> parseMoviesFromJson(String jsonString) throws JSONException, ParseException {

        List<Movie> movies = new ArrayList<>();

        JSONObject response = new JSONObject(jsonString);

        JSONArray results = response.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            Movie movie = new Movie();
            JSONObject movieJson = results.getJSONObject(i);
            movie.adult = movieJson.getBoolean("adult");
            movie.backdrop_path = movieJson.getString("backdrop_path");
            movie.movie_id = movieJson.getInt("id");
            movie.original_title = movieJson.getString("original_title");
            movie.overview = movieJson.getString("overview");
            movie.release_date = DateTimeUtility.parseDate(movieJson.getString("release_date"));
            movie.poster_url = movieJson.getString("poster_path");
            movie.popularity = movieJson.getDouble("popularity");
            movie.title = movieJson.getString("title");
            movie.video = movieJson.getBoolean("video");
            movie.vote_average = movieJson.getDouble("vote_average");
            movie.vote_count = movieJson.getInt("vote_count");
            movies.add(movie);
        }
        return movies;
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
}