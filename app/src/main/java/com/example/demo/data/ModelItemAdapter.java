package com.example.demo.data;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.demo.R;
import com.example.demo.data.adapter.ChoiceCapableAdapter;
import com.example.demo.data.adapter.SingleChoiceMode;

import java.util.List;

import timber.log.Timber;

public class ModelItemAdapter extends ChoiceCapableAdapter<ModelItemAdapter.ViewHolder>{

    // interface implemented by HomeFragment to respond to
    // user clicking on an item in the recycler view
    public interface ModelItemClickListener {
        void onClick(int position);
    }

    private ModelItemClickListener mListener;
    private List<ModelItem> mItems;

    public ModelItemAdapter(RecyclerView recyclerView, List<ModelItem> items, ModelItemClickListener listener) {
        super(recyclerView, new SingleChoiceMode());
        mItems = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindModelItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ChoiceCapableAdapter mAdapter;
        View mItemView;
        ImageView mIcon;
        TextView mName;
        TextView mAddress;

        public ViewHolder(ChoiceCapableAdapter adapter, View itemView) {
            super(itemView);
            mAdapter = adapter;
            mItemView = itemView;
            mItemView.setOnClickListener(this);
            mIcon = (ImageView) itemView.findViewById(R.id.model_icon);
            mName = (TextView) itemView.findViewById(R.id.model_name);
            mAddress = (TextView) itemView.findViewById(R.id.model_address);
        }

        public void bindModelItem(ModelItem item) {
            mName.setText(item.getName());
            mAddress.setText(item.getAddress());
            // generate image drawable
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable letterDrawable = TextDrawable.builder()
                    .buildRound(String.valueOf(item.getName().charAt(0)), generator.getRandomColor());
            mIcon.setImageDrawable(letterDrawable);

            setChecked(mAdapter.isChecked(getAdapterPosition()));
        }

        @Override
        public void onClick(View view) {
            // handle checked status
            boolean isCheckedNow = mAdapter.isChecked(getAdapterPosition());
            // whatever item's checked state, reverse it
            mAdapter.onChecked(getAdapterPosition(), !isCheckedNow);
            mItemView.setActivated(!isCheckedNow);

            // propagate upto fragment
            mListener.onClick(getAdapterPosition());
        }

        public void setChecked(boolean isChecked) {
            mItemView.setActivated(isChecked);
        }

    }


}
