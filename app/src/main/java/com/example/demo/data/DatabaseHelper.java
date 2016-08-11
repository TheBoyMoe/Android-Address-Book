package com.example.demo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.demo.data.DatabaseContract.Model;

/**
 * References:
 * [1] Android 6 App Development Fundamentals I and II by Paul Deitel (AddressBook App)
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "model_database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // define the table in the database
        final String CREATE_MODEL_TABLE =
                "CREATE TABLE " + Model.TABLE_NAME + "("
                + Model._ID + " integer primary key, "
                + Model.COLUMN_NAME + " TEXT, "
                + Model.COLUMN_ADDRESS + " TEXT, "
                + Model.COLUMN_URL + " TEXT, "
                + Model.COLUMN_EMAIL + " TEXT, "
                + Model.COLUMN_PHONE + " TEXT, "
                + Model.COLUMN_BACKDROP + " INTEGER, "
                + Model.COLUMN_COLOR + " INTEGER);";
        db.execSQL(CREATE_MODEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete the current table if a newer version of the database schema is available
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Model.TABLE_NAME);
            onCreate(db);
        }
    }


}
