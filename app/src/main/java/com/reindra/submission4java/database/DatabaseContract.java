package com.reindra.submission4java.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIES = "Movies";
    static String TABLE_TV = "TVShow";

    public static final class MoviesColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String YEAR = "date";
        public static String POSTER = "photo";
        public static String RATING = "rating";
        public static String OVERVIEW = "overview";
        public static String COUNTRY = "country";
    }

    public static final class TVColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String PHOTO = "photo";
        public static String RATING = "rating";
        public static String DATE = "date";

    }
}
