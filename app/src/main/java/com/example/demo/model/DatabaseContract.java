package com.example.demo.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    // identify the content provider
    public static final String AUTHORITY = "com.example.demo.model";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // define database table and column names (base columns adds _id column, used ti identify each record)
    public static final class Item implements BaseColumns {

        // define the table name
        public static final String TABLE_NAME = "items";

        // define the column names
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";

        // identify the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // identify an individual record within the table
        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
