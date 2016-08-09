package com.example.demo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.demo.R;
import com.example.demo.data.DatabaseContract.Model;

/**
 * References:
 * [1] Android 6 App Development Fundamentals I and II by Paul Deitel (AddressBook App)
 *     Content Provider class allows the database to be accessed asynchronously
 */
@SuppressWarnings("ConstantConditions")
public class DatabaseContentProvider extends ContentProvider{

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ONE_ITEM = 1; // access to individual record
    private static final int ALL_ITEMS = 2; // access to all records
    private DatabaseHelper mHelper; // used to access the database

    // configure the URI's the ContentProvider can respond to, returning an integer constant
    // which can be used to retrieve different record(s) based on the uri
    static {
        // return a record with the specified id
        mUriMatcher.addURI(DatabaseContract.AUTHORITY, Model.TABLE_NAME + "/#", ONE_ITEM);
        // return all table records
        mUriMatcher.addURI(DatabaseContract.AUTHORITY, Model.TABLE_NAME, ALL_ITEMS);
    }

    @Override
    public boolean onCreate() {
        mHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // not used in this app
        return null;
    }

    @Override
    public Cursor query(@NonNull Uri uri,       // record(s) to return
                        String[] projection,    // set of columns to retrieve
                        String selection,       // where clause, selection criteria
                        String[] args,          // values to substitute into the selection string where ever there are any placeholders(?)
                        String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Model.TABLE_NAME); // specific table to query

        // determine the specific record(s) to return
        switch(mUriMatcher.match(uri)) {
            case ONE_ITEM:
                // append a where cause to return the record whose id is equal to last path segment
                builder.appendWhere(Model._ID + "=" +uri.getLastPathSegment());
                break;
            case ALL_ITEMS:
                // no need to do anything
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri) + uri);
        }
        // build & execute the query, returning a cursor
        Cursor cursor = builder.query(mHelper.getReadableDatabase(),
                                projection, selection, args, null, null, sortOrder);

        // watch for any changes to the data that this cursor refers to
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Uri newUri = null;

        // the uri used will match that of the table, the record's uri is generated when the record is inserted
        switch (mUriMatcher.match(uri)) {
            case ALL_ITEMS: // will match the uri of the table
                long rowId = mHelper.getWritableDatabase().insert(Model.TABLE_NAME, null, contentValues);
                if (rowId > 0) {
                    newUri = Model.buildItemUri(rowId); // generate the uri based on the record's id

                    // notify any registered observers that the database has changed
                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    // insert failed
                    throw new SQLException(getContext().getString(R.string.database_insert_failed) + uri);
                }
                break;
            default:
                // uri not valid
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_insert_uri) + uri);
        }

        // return the uri of the newly inserted record
        return newUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] args) {
        int numberOfRowsUpdated; // 1 if successful, 0 otherwise
        // we're only updating individual records
        switch (mUriMatcher.match(uri)) {
            case ONE_ITEM:
                // retrieve the id of the specific record
                String id = uri.getLastPathSegment();
                numberOfRowsUpdated = mHelper.getWritableDatabase().update(
                        Model.TABLE_NAME,
                        contentValues,
                        Model._ID + "=" +id,    // where clause
                        args);                  // selection arguments
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_update_uri) + uri);
        }
        // notify any observers that the database has been updated
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numberOfRowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] args) {
        int numberOfRowsDeleted = 0;

        switch (mUriMatcher.match(uri)) {
            case ONE_ITEM:
                String id = uri.getLastPathSegment();
                numberOfRowsDeleted = mHelper.getWritableDatabase().delete(
                        Model.TABLE_NAME, Model._ID + "=" + id, args);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_delete_uri));
        }

        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }



}
