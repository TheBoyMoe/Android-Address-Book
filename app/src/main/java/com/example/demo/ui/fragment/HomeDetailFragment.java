package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.demo.common.BaseFragment;

public class HomeDetailFragment extends BaseFragment{

    public HomeDetailFragment() {}

    public static HomeDetailFragment newInstance() {
        return new HomeDetailFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextPlaceholder.setText("Home detail fragment");
    }

}
