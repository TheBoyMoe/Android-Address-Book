package com.example.demo.data;

import java.util.Locale;

public class ModelItem {

    private String mName;
    private String mAddress;
    private String mUrl;
    private String mEmail;
    private String mPhone;

    public ModelItem(String name, String address, String url, String email, String phone) {
        mName = name;
        mAddress = address;
        mUrl = url;
        mEmail = email;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, %s", getName(), getAddress());
    }


}
