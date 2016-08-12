package com.example.demo.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.common.fragments.ContractFragment;

import timber.log.Timber;

public class ModelItemFragment extends ContractFragment<ModelItemFragment.Contract>
        implements LoaderManager.LoaderCallbacks<Cursor>{


    public interface Contract {
        void saveModelItem(ContentValues values);
        void updateModelItem(Uri itemUri, ContentValues values);
        void quit();
    }

    private Uri mItemUri;
    private String mNameText;

    public ModelItemFragment() {}

    public static ModelItemFragment newInstance() {
        return new ModelItemFragment();
    }

    public static ModelItemFragment newInstance(Uri uri) {
        ModelItemFragment fragment = new ModelItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.MODEL_ITEM_URI, uri);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            mItemUri = getArguments().getParcelable(Constants.MODEL_ITEM_URI);
        }
        return mView;
    }

    View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            ContentValues values = null;
            // show message to user with regards to success/failure of operation
            String name = (mName.getEditText().getText() != null) ? mName.getEditText().getText().toString() : "";
            if (!name.isEmpty() && name.length() > 2) { // name needs to be 3 or more chars
                if (mNameText != null) { // update an existing item
                    values = getUpdatedEditTextValues();
                    getContract().updateModelItem(mItemUri, values);
                } else { // save a new item
                    values = getEditTextValues();
                    getContract().saveModelItem(values);
                }
            }
            else { // nothing to save
                getContract().quit();
            }
        }
    };


    // impl Loader Callback
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mItemUri != null) {
            // initialize the cursor loader when the hosting activity is created
            getLoaderManager().initLoader(Constants.MODEL_ITEM_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // retrieve the record for the submitted uri
        CursorLoader loader;
        switch (id) {
            case Constants.MODEL_ITEM_LOADER:
                loader = new CursorLoader(getActivity(), mItemUri, null, null, null, null);
                break;
            default:
                loader = null;
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // populate element fields
        mNameText = setEditTextValues(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no-op
    }


}

