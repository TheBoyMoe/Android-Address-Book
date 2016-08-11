package com.example.demo.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * References:
 * [1] Android 6 App Development Fundamentals I and II by Paul Deitel (AddressBook App)
 */
public class DatabaseContract {

    // identify the content provider
    public static final String AUTHORITY = "com.example.demo.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // define database table and column names (base columns adds _id column, used ti identify each record)
    public static final class Model implements BaseColumns {

        // define the table name
        public static final String TABLE_NAME = "items";

        // define the column names
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_COLOR = "color";

        // identify the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // identify an individual record within the table
        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
