package com.example.demo.common.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;

import timber.log.Timber;


public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{

    public interface CustomDialogFragmentListener {
        void onItemDeleted();
    }

    private CustomDialogFragmentListener mListener;
    private Uri mItemUri;

    public CustomDialogFragment() {}

    public static CustomDialogFragment newInstance(Uri uri) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.MODEL_ITEM_URI, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_delete, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        (view.findViewById(R.id.dialog_positive_btn)).setOnClickListener(this);
        (view.findViewById(R.id.dialog_negative_btn)).setOnClickListener(this);
        mItemUri = getArguments().getParcelable(Constants.MODEL_ITEM_URI);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_positive_btn:
                if (mItemUri != null) {
                    // delete the item from the database
                    int numOfRows = getActivity().getContentResolver().delete(mItemUri, null, null);
                    if (numOfRows > 0) {
                        confirmItemDeletion(true);
                    }
                }
                break;
        }
        dismiss();
    }


    // let MainFragment know deletion has occurred
    private void confirmItemDeletion(boolean itemDeleted) {
        Intent intent = new Intent();
        intent.putExtra(Constants.CONFIRM_ITEM_DELETION, itemDeleted);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Constants.REQUEST_CODE_ITEM_DELETION, intent);
    }

}
