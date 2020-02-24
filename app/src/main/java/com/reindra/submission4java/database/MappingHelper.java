package com.reindra.submission4java.database;

import android.database.Cursor;

import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;

import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.COUNTRY;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.OVERVIEW;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.POSTER;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.RATING;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.TITLE;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.YEAR;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns._ID;

public class MappingHelper {
    public static ArrayList<Movie> mapCursor(Cursor itemCursor){
        ArrayList<Movie> item = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(_ID));
            String tittle = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(POSTER));
            String rating = itemCursor.getString(itemCursor.getColumnIndexOrThrow(RATING));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW));
            String year = itemCursor.getString(itemCursor.getColumnIndexOrThrow(YEAR));
            String country = itemCursor.getString(itemCursor.getColumnIndexOrThrow(COUNTRY));

            item.add(new Movie(id, tittle, photo, rating, overview, year, country));
        }
        return item;

        }
    }
