package com.reindra.favoritemovieapps.database;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.reindra.favoritemovieapps.model.Movie;

import java.util.ArrayList;

import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.OVERVIEW;
import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.POSTER;
import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.RATING;
import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.TITLE;
import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.YEAR;

public class MappingHelper {
    public static ArrayList<Movie> mapCursor(Cursor itemCursor) {
        ArrayList<Movie> items = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(BaseColumns._ID));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(POSTER));
            String tittle = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE));
            String rating = itemCursor.getString(itemCursor.getColumnIndexOrThrow(RATING));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW));

            items.add(new Movie(id, photo, tittle, rating, overview));

        }
        return items;

    }

    public static ArrayList<Movie> mapCursorTV(Cursor cursoritem) {
        ArrayList<Movie> items = new ArrayList<>();
        while (cursoritem.moveToNext()) {
            int id = cursoritem.getInt(cursoritem.getColumnIndexOrThrow(BaseColumns._ID));
            String photo = cursoritem.getString(cursoritem.getColumnIndexOrThrow(POSTER));
            String title = cursoritem.getString(cursoritem.getColumnIndexOrThrow(TITLE));
            String date = cursoritem.getString(cursoritem.getColumnIndexOrThrow(YEAR));
            String rating = cursoritem.getString(cursoritem.getColumnIndexOrThrow(RATING));
            String overview = cursoritem.getString(cursoritem.getColumnIndexOrThrow(OVERVIEW));

            items.add(new Movie(id, photo, title, date, overview, rating));
        }
        return items;
    }
}