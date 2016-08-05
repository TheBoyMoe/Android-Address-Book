package com.example.demo.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.demo.R;

import java.util.List;

public class ModelItemAdapter extends RecyclerView.Adapter<ModelItemAdapter.ViewHolder>{

    // interface implemented by HomeFragment to respond to
    // user clicking on an item in the recycler view
    public interface ModelItemClickListener {
        void onClick(int position);
    }

    private ModelItemClickListener mListener;
    private List<ModelItem> mItems;

    public ModelItemAdapter(List<ModelItem> items, ModelItemClickListener listener) {
        mItems = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindModelItem(mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mIcon;
        TextView mName;
        TextView mAddress;
        int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mIcon = (ImageView) itemView.findViewById(R.id.model_icon);
            mName = (TextView) itemView.findViewById(R.id.model_name);
            mAddress = (TextView) itemView.findViewById(R.id.model_address);
        }

        public void bindModelItem(ModelItem item, int position) {
            // Timber.i("%s item bound: %d", Constants.LOG_TAG, position);
            mPosition = position;
            mName.setText(item.getName());
            mAddress.setText(item.getAddress());
            // generate image drawable
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable letterDrawable = TextDrawable.builder()
                    .buildRound(String.valueOf(item.getName().charAt(0)), generator.getRandomColor());
            mIcon.setImageDrawable(letterDrawable);
        }

        @Override
        public void onClick(View view) {
            // Timber.i("%s item clicked: %d", Constants.LOG_TAG, mPosition); // ?? why does this have the correct position
            // propagate upto fragment
            mListener.onClick(mPosition);
        }

    }


}
