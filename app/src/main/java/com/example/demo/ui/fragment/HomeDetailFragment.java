package com.example.demo.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.data.DatabaseContract;
import com.example.demo.data.ModelItemData;

import java.util.Random;

public class HomeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MODEL_ITEM_LOADER = 0;

    private Uri mItemUri;
    private ImageView mBackdrop;
    private TextView mName;
    private TextView mAddress;
    private TextView mUrl;
    private TextView mEmail;
    private TextView mPhone;

    public HomeDetailFragment() {}

    public static HomeDetailFragment newInstance(Uri uri) {
        HomeDetailFragment fragment = new HomeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.MODEL_ITEM_URI, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_detail, container, false);
        mBackdrop = (ImageView) view.findViewById(R.id.item_backdrop);
        mName = (TextView) view.findViewById(R.id.item_name);
        mAddress = (TextView) view.findViewById(R.id.item_address);
        mUrl = (TextView) view.findViewById(R.id.item_url);
        mEmail = (TextView) view.findViewById(R.id.item_email);
        mPhone = (TextView) view.findViewById(R.id.item_phone);

        mItemUri = getArguments().getParcelable(Constants.MODEL_ITEM_URI);
        if (mItemUri != null) {
            // initialize the loader
            getLoaderManager().initLoader(MODEL_ITEM_LOADER, null, this);
        }

        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // retrieve the record, returning a cursor
        CursorLoader loader;
        switch (id) {
            case MODEL_ITEM_LOADER:
                loader = new CursorLoader(getActivity(), mItemUri, null, null, null, null); // CHECK
                break;
            default:
                loader = null;
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // update the ActionBar Title
        String name = data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_NAME));
        ((MainFragment)getParentFragment()).setPageTitle(name);

        // populate layout elements
        Random generator = new Random();
        mBackdrop.setImageResource(ModelItemData.getImageDrawable(Math.round(generator.nextInt(8))));
        mName.setText(name);
        mAddress.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_ADDRESS)));
        mUrl.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_URL)));
        mEmail.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_EMAIL)));
        mPhone.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_PHONE)));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no-op
    }


}
