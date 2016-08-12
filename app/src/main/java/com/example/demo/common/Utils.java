package com.example.demo.common;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.demo.R;
import com.example.demo.data.DatabaseContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private Utils() {
        throw new AssertionError();
    }

    // hide the keyboard on executing search
    public static void hideKeyboard(Activity activity, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbarSticky(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
    }

    // Check that a network connection is available
    public  static boolean isClientConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static Long generateCustomId() {
        // define an id based on the date time stamp
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        String pattern = "yyyyMMddHHmmssSS"; // pattern used to sort objects
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());

        return Long.valueOf(formatter.format(new Date()));
    }

    public static void loadPreviewWithGlide(Context context, String previewPath, ImageView view) {
        Glide.with(context)
                .load(previewPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void setupToolbar(Activity activity, Toolbar toolbar) {
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)activity).getSupportActionBar();
        if (actionBar != null) {
            // hide title by default
            actionBar.setDisplayShowTitleEnabled(false);
            // set navigation icon & toolbar background color
            toolbar.setNavigationIcon(R.drawable.ic_back_dark);
            toolbar.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
    }

    public static ContentValues setModelItemValues(String name,
              String address, String url, String email, String phone, int backdrop, int color) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Model.COLUMN_NAME, name);
        values.put(DatabaseContract.Model.COLUMN_ADDRESS, address);
        values.put(DatabaseContract.Model.COLUMN_URL, url);
        values.put(DatabaseContract.Model.COLUMN_EMAIL, email);
        values.put(DatabaseContract.Model.COLUMN_PHONE, phone);
        values.put(DatabaseContract.Model.COLUMN_BACKDROP, backdrop);
        values.put(DatabaseContract.Model.COLUMN_COLOR, color);
        return values;
    }

    public static ContentValues setModelItemValues(String name,
              String address, String url, String email, String phone) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Model.COLUMN_NAME, name);
        values.put(DatabaseContract.Model.COLUMN_ADDRESS, address);
        values.put(DatabaseContract.Model.COLUMN_URL, url);
        values.put(DatabaseContract.Model.COLUMN_EMAIL, email);
        values.put(DatabaseContract.Model.COLUMN_PHONE, phone);
        return values;
    }


}
