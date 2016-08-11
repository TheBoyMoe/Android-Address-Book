package com.example.demo.data;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.data.adapter.ChoiceCapableAdapter;
import com.example.demo.data.adapter.SingleChoiceMode;

import java.util.List;

import timber.log.Timber;

import static com.example.demo.data.DatabaseContract.*;

public class ModelItemAdapter extends ChoiceCapableAdapter<ModelItemAdapter.ViewHolder>{

    // interface implemented by HomeFragment to respond to
    // user clicking on an item in the recycler view
    public interface ModelItemClickListener {
        void onClick(Uri modelItem);
        void onLongClick(Uri modelItem);
    }

    private Cursor mCursor = null;
    private ModelItemClickListener mListener;

    public ModelItemAdapter(RecyclerView recyclerView, ModelItemClickListener listener) {
        super(recyclerView, new SingleChoiceMode());
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            holder.setRowId(mCursor.getLong(mCursor.getColumnIndex(Model._ID)));
            holder.bindModelItem(mCursor);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener{

        ChoiceCapableAdapter mAdapter;
        View mItemView;
        ImageView mIcon;
        TextView mName;
        TextView mAddress;
        long mRowId;

        public ViewHolder(ChoiceCapableAdapter adapter, View itemView) {
            super(itemView);
            mAdapter = adapter;
            mItemView = itemView;
            mItemView.setOnClickListener(this);
            mItemView.setOnLongClickListener(this);
            mIcon = (ImageView) itemView.findViewById(R.id.model_icon);
            mName = (TextView) itemView.findViewById(R.id.model_name);
            mAddress = (TextView) itemView.findViewById(R.id.model_address);
        }

        public void bindModelItem(Cursor cursor) {
            String name = cursor.getString(cursor.getColumnIndex(Model.COLUMN_NAME));
            mName.setText(name);
            mAddress.setText(cursor.getString(cursor.getColumnIndex(Model.COLUMN_ADDRESS)));
            int color = cursor.getInt(cursor.getColumnIndex(Model.COLUMN_COLOR));
            TextDrawable letterDrawable = TextDrawable.builder().buildRound(String.valueOf(name.charAt(0)), color);
            mIcon.setImageDrawable(letterDrawable);

            setChecked(mAdapter.isChecked(getAdapterPosition())); // ??
        }

        @Override
        public void onClick(View view) {
            // handle checked status
            boolean isCheckedNow = mAdapter.isChecked(getAdapterPosition()); // ??
            // whatever item's checked state, reverse it
            mAdapter.onChecked(getAdapterPosition(), !isCheckedNow);
            mItemView.setActivated(!isCheckedNow);

            // propagate click upto fragment
            mListener.onClick(Model.buildItemUri(mRowId));
        }

        @Override
        public boolean onLongClick(View view) {
            // propagate long click upto the fragment
            mListener.onLongClick(Model.buildItemUri(mRowId));
            return true;
        }

        public void setChecked(boolean isChecked) {
            mItemView.setActivated(isChecked);
        }

        public void setRowId(long id) {
            mRowId = id;
        }

    }


}
