package com.example.demo.ui.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.demo.R;
import com.example.demo.common.ContractFragment;
import com.example.demo.common.Utils;

public class ModelItemFragment extends ContractFragment<ModelItemFragment.Contract>{

    private EditText mName;
    private EditText mAddress;
    private EditText mUrl;
    private EditText mEmail;
    private EditText mPhone;

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
        mName = (EditText) view.findViewById(R.id.model_item_name);
        mAddress = (EditText) view.findViewById(R.id.model_item_address);
        mUrl = (EditText) view.findViewById(R.id.model_item_url);
        mEmail = (EditText) view.findViewById(R.id.model_item_email);
        mPhone = (EditText) view.findViewById(R.id.model_item_phone);

        return view;
    }

    View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = mName.getText().toString();
            if (name.isEmpty()) {
                ContentValues values = Utils.setModelItemValues(
                        name,
                        mAddress.getText().toString(),
                        mUrl.getText().toString(),
                        mEmail.getText().toString(),
                        mPhone.getText().toString()
                );
                getContract().saveModelItem(values);
            } else {
                getContract().quit();
            }
        }
    };


}
