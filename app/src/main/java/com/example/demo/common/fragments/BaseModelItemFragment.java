package com.example.demo.common.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.demo.common.Utils;

public class BaseModelItemFragment extends Fragment{

    protected View mView;
    protected TextInputLayout mName;
    protected TextInputLayout mAddress;
    protected TextInputLayout mUrl;
    protected TextInputLayout mEmail;
    protected TextInputLayout mPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_model_item, container, false);

        mName = (TextInputLayout) mView.findViewById(R.id.name_input_layout);
        mAddress = (TextInputLayout) mView.findViewById(R.id.address_input_layout);
        mUrl = (TextInputLayout) mView.findViewById(R.id.url_input_layout);
        mEmail = (TextInputLayout) mView.findViewById(R.id.email_input_layout);
        mPhone = (TextInputLayout) mView.findViewById(R.id.phone_layout_input);

        return mView;
    }

    @SuppressWarnings("ConstantConditions")
    protected ContentValues getEditTextValues() {
        return Utils.setModelItemValues(
                mName.getEditText().getText() != null ? mName.getEditText().getText().toString() : "",
                mAddress.getEditText().getText() != null ? mAddress.getEditText().getText().toString() : "",
                mUrl.getEditText().getText() != null ? mUrl.getEditText().getText().toString() : "",
                mEmail.getEditText().getText() != null ? mEmail.getEditText().getText().toString() : "",
                mPhone.getEditText().getText() != null ? mPhone.getEditText().getText().toString() : ""
        );

    }


}
