package com.example.demo.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.data.DatabaseContract;
import com.example.demo.data.ModelItemData;

import java.util.Random;

import timber.log.Timber;

public class HomeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MODEL_ITEM_LOADER = 0;

    private Toolbar mItemToolbar;
    private Uri mItemUri;
    private View mDetailContainer;
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
        mItemToolbar = (Toolbar) view.findViewById(R.id.model_item_toolbar);
        if (mItemToolbar != null) {
            mItemToolbar.inflateMenu(R.menu.menu_item);
            mItemToolbar.setOnMenuItemClickListener(menuClickListener);
        }

        mDetailContainer = view.findViewById(R.id.detail_container);
        mBackdrop = (ImageView) view.findViewById(R.id.item_backdrop);
        mName = (TextView) view.findViewById(R.id.item_name);
        mAddress = (TextView) view.findViewById(R.id.item_address);
        mUrl = (TextView) view.findViewById(R.id.item_url);
        mEmail = (TextView) view.findViewById(R.id.item_email);
        mPhone = (TextView) view.findViewById(R.id.item_phone);

        mItemUri = getArguments().getParcelable(Constants.MODEL_ITEM_URI);
        if (mItemUri != null) {
            mDetailContainer.setVisibility(View.VISIBLE);
            // initialize the loader
            getLoaderManager().initLoader(MODEL_ITEM_LOADER, null, this);
        } else {
            mDetailContainer.setVisibility(View.INVISIBLE);
            Timber.i("%s: mItemUri: null", Constants.LOG_TAG);
        }
        return view;
    }

    Toolbar.OnMenuItemClickListener menuClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    Utils.showToast(getActivity(), "Clicked on edit");
                    return true;
                // case R.id.action_delete:
                //    Utils.showToast(getActivity(), "Clicked on delete");
                //    return true;
                default:
                    return true;
            }
        }
    };


    // LoaderCallback impl
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

        data.moveToFirst();

        // update the ActionBar Title
        String name = data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_NAME));
        ((MainFragment)getParentFragment()).setPageTitle(name);

        // populate layout elements
        mDetailContainer.setVisibility(View.VISIBLE);
        mName.setText(name);
        mAddress.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_ADDRESS)));
        mUrl.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_URL)));
        mEmail.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_EMAIL)));
        mPhone.setText(data.getString(data.getColumnIndex(DatabaseContract.Model.COLUMN_PHONE)));
        mBackdrop.setImageResource(data.getInt(data.getColumnIndex(DatabaseContract.Model.COLUMN_BACKDROP)));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no-op
    }


}
