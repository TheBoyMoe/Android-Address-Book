package com.example.demo.common.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.data.DatabaseContract;
import com.example.demo.data.ModelItemData;

import java.util.Random;

import timber.log.Timber;

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

    protected ContentValues getEditTextValues() {
        // generate random backdrop image, save with item
        Random generator = new Random();
        int image = ModelItemData.getImageDrawable(generator.nextInt(8));
        // generate material color , save with item
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();

        return Utils.setModelItemValues(
                // return content values when saving item values for first time
                getEditTextValue(mName),
                getEditTextValue(mAddress),
                getEditTextValue(mUrl),
                getEditTextValue(mEmail),
                getEditTextValue(mPhone),
                image, color);
    }

    protected ContentValues getUpdatedEditTextValues() {
        // return content values when updating item values
        return Utils.setModelItemValues(
                getEditTextValue(mName),
                getEditTextValue(mAddress),
                getEditTextValue(mUrl),
                getEditTextValue(mEmail),
                getEditTextValue(mPhone)
        );
    }

    @SuppressWarnings("ConstantConditions")
    private String getEditTextValue(TextInputLayout view) {
        return view.getEditText().getText() != null ? view.getEditText().getText().toString() : "";
    }

    protected String setEditTextValues(Cursor cursor) {
        String  name = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            name = getFieldValue(cursor, DatabaseContract.Model.COLUMN_NAME);
            setFieldValue(mName, name);
            setFieldValue(mAddress, getFieldValue(cursor, DatabaseContract.Model.COLUMN_ADDRESS));
            setFieldValue(mUrl, getFieldValue(cursor, DatabaseContract.Model.COLUMN_URL));
            setFieldValue(mEmail, getFieldValue(cursor, DatabaseContract.Model.COLUMN_EMAIL));
            setFieldValue(mPhone, getFieldValue(cursor, DatabaseContract.Model.COLUMN_PHONE));
        }
        return name;
    }

    private String getFieldValue(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    @SuppressWarnings("ConstantConditions")
    private void setFieldValue(TextInputLayout field, String value) {
        if (value != null && !value.isEmpty()) {
            field.getEditText().setText(value);
        }
    }

}
