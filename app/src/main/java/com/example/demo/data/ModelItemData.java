package com.example.demo.data;

import com.example.demo.R;

public class ModelItemData {

    public static int getImageDrawable(int position) {
        switch (position) {

            default:
            case 0:
                return R.drawable.image_01;
            case 1:
                return R.drawable.image_02;
            case 2:
                return R.drawable.image_03;
            case 3:
                return R.drawable.image_04;
            case 4:
                return R.drawable.image_05;
            case 5:
                return R.drawable.image_06;
            case 6:
                return R.drawable.image_07;
            case 7:
                return R.drawable.image_08;
        }
    }

}
