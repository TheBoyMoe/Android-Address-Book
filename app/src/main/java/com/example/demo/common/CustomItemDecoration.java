package com.example.demo.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomItemDecoration extends RecyclerView.ItemDecoration{

    private int mVertical;
    private int mHorizontal;

    public CustomItemDecoration(int vertical, int horizontal) {
        mVertical = vertical;
        mHorizontal = horizontal;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = mHorizontal;
        outRect.right = mHorizontal;
        outRect.bottom = mVertical;
        outRect.top = mVertical;
    }

}

