package com.example.demo.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.ui.fragment.ModelItemFragment;

public class ModelItemActivity extends AppCompatActivity implements
        ModelItemFragment.Contract{

    // impl contract methods
    @Override
    public void saveModelItem(ContentValues values) {
        // TODO save to database

        finish();
    }

    @Override
    public void quit() {
        finish();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ModelItemActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_item);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, ModelItemFragment.newInstance())
                    .commit();
        }
    }
}
