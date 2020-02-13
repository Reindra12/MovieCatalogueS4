package com.reindra.submission4java.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.reindra.submission4java.database.DatabaseContract.TVColumns.DATE;
import static com.reindra.submission4java.database.DatabaseContract.TABLE_TV;
import static com.reindra.submission4java.database.DatabaseContract.TVColumns.OVERVIEW;
import static com.reindra.submission4java.database.DatabaseContract.TVColumns.PHOTO;
import static com.reindra.submission4java.database.DatabaseContract.TVColumns.RATING;
import static com.reindra.submission4java.database.DatabaseContract.TVColumns.TITLE;

public class TVHelper {
    private static final String DB_TABLE = TABLE_TV;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static TVHelper INSTANCE;

    private TVHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TVHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVHelper(context);


                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getdataTV() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DB_TABLE, null, null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movie.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(PHOTO, movie.getPhoto());
        args.put(RATING, movie.getRating());
        args.put(DATE, movie.getDate());
        return database.insert(DB_TABLE, null, args);
    }
    /*public long insert(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(DATE, movie.getDate());
        args.put(OVERVIEW, movie.getOverview());
        args.put(PHOTO, movie.getPhoto());
        args.put(RATING, movie.getRating());
        return database.insert(DB_TABLE, null, args);
    }*/


    public int delete(int id) {
        return database.delete(DB_TABLE, _ID + " = '" + id + "'", null);
    }

    public boolean getAllTV(int id) {
        String query = "SELECT * FROM " + DB_TABLE + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }
        cursor.close();
        return exist;
    }
}

