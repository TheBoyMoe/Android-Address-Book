package com.example.demo.data;

import java.util.Locale;

public class ModelItem {

    private String mTitle;
    private String mDescription;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, %s", getTitle(), getDescription());
    }


}
