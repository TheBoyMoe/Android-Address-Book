package com.example.demo.ui.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.demo.R;
import com.example.demo.common.Utils;
import com.example.demo.common.fragments.ContractFragment;

public class ModelItemFragment extends ContractFragment<ModelItemFragment.Contract> {

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
        super.onCreateView(inflater, container, savedInstanceState);
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            Utils.setupToolbar(getActivity(), toolbar);
            toolbar.setNavigationOnClickListener(navigationOnClickListener);
        }
        return mView;
    }

    View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            String name = (mName.getEditText().getText() != null) ? mName.getEditText().getText().toString() : "";
            if (!name.isEmpty()) {
                ContentValues values = getEditTextValues();
                getContract().saveModelItem(values);
            } else {
                getContract().quit();
            }
        }
    };


}

