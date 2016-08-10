package com.example.demo.ui.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.ContractFragment;
import com.example.demo.common.Utils;

import timber.log.Timber;

public class ModelItemFragment extends ContractFragment<ModelItemFragment.Contract>{

    private TextInputLayout mName;
    private TextInputLayout mAddress;
    private TextInputLayout mUrl;
    private TextInputLayout mEmail;
    private TextInputLayout mPhone;

    public interface Contract {
        void saveModelItem(ContentValues values);
        void quit();
    }

    public ModelItemFragment() {}

    public static ModelItemFragment newInstance() {
        return new ModelItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model_item, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            Utils.setupToolbar(getActivity(), toolbar);
            toolbar.setNavigationOnClickListener(navigationOnClickListener);
        }
        ImageView backdrop = (ImageView) view.findViewById(R.id.item_backdrop);
        mName = (TextInputLayout) view.findViewById(R.id.name_input_layout);
        mAddress = (TextInputLayout) view.findViewById(R.id.address_input_layout);
        mUrl = (TextInputLayout) view.findViewById(R.id.url_input_layout);
        mEmail = (TextInputLayout) view.findViewById(R.id.email_input_layout);
        mPhone = (TextInputLayout) view.findViewById(R.id.phone_layout_input);

        return view;
    }

    View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            String name = (mName.getEditText().getText() != null) ? mName.getEditText().getText().toString() : "";
            if (!name.isEmpty()) {
                ContentValues values = Utils.setModelItemValues(
                        name,
                        mAddress.getEditText().getText() != null ? mAddress.getEditText().getText().toString() : "",
                        mUrl.getEditText().getText() != null ? mUrl.getEditText().getText().toString() : "",
                        mEmail.getEditText().getText() != null ? mEmail.getEditText().getText().toString() : "",
                        mPhone.getEditText().getText() != null ? mPhone.getEditText().getText().toString() : ""
                );
                getContract().saveModelItem(values);
            } else {
                getContract().quit();
            }
        }
    };


}

