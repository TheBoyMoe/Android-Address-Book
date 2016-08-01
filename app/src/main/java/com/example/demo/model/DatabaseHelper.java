package com.example.demo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.demo.model.DatabaseContract.Model;

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
                + Model.COLUMN_TITLE + " TEXT, "
                + Model.COLUMN_DESCRIPTION + " TEXT);";
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
