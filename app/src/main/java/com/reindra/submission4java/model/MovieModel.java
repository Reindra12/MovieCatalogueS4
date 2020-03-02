package com.reindra.submission4java.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.reindra.submission4java.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieModel extends ViewModel {
    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<Movie>> ListMovies = new MutableLiveData<>();

    public void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie items = new Movie();
                        items.setId(movie.getInt("id"));
                        items.setTitle(movie.getString("title"));
                        items.setDate(movie.getString("release_date"));
                        items.setRating(movie.getString("vote_average"));
                        items.setCountry(movie.getString("original_language"));
                        items.setOverview(movie.getString("overview"));
                        items.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                        listItems.add(items);
                    }
                    ListMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());

                }
            }

            @Override

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public void searchdatamovie(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> item = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list = jsonObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(movies.getInt("id"));
                        movie.setTitle(movies.getString("title"));
                        movie.setRating(movies.getString("vote_average"));
                        movie.setDate(movies.getString("release_date"));
                        movie.setOverview(movies.getString("overview"));
                        movie.setCountry(movies.getString("original_language"));
                        movie.setPhoto("https://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                        item.add(movie);
                    }
                    ListMovies.postValue(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public LiveData<ArrayList<Movie>> getMovie() {
        return ListMovies;

    }

    public void setTV() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie items = new Movie();
                        items.setId(movie.getInt("id"));
                        items.setTitle(movie.getString("name"));
                        items.setOverview(movie.getString("overview"));
                        items.setDate(movie.getString("first_air_date"));
                        items.setRating(movie.getString("vote_average"));
                        items.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                        listItems.add(items);
                    }
                    ListMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());

                }
            }

            @Override

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }
    public void searchdatatv(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> item = new ArrayList<>();
        String url = " https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list = jsonObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(movies.getInt("id"));
                        movie.setTitle(movies.getString("name"));
                        movie.setOverview(movies.getString("overview"));
                        movie.setDate(movies.getString("first_air_date"));
                        movie.setCountry(movies.getString("original_language"));
                        movie.setRating(movies.getString("vote_average"));
                        movie.setPhoto("https://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                        item.add(movie);
                    }
                    ListMovies.postValue(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public LiveData<ArrayList<Movie>> getTV() {
        return ListMovies;

    }

}