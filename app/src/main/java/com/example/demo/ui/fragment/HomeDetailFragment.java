package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.data.ModelItem;
import com.example.demo.data.ModelItemData;

public class HomeDetailFragment extends Fragment{

    public HomeDetailFragment() {}

    public static HomeDetailFragment newInstance(int position) {
        HomeDetailFragment fragment = new HomeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.LIST_ITEM_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_detail, container, false);
        ImageView backdrop = (ImageView) view.findViewById(R.id.item_backdrop);
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView address = (TextView) view.findViewById(R.id.item_address);
        TextView url = (TextView) view.findViewById(R.id.item_url);
        TextView email = (TextView) view.findViewById(R.id.item_email);
        TextView phone = (TextView) view.findViewById(R.id.item_phone);

        int position = getArguments().getInt(Constants.LIST_ITEM_POSITION, 0);
        ModelItem item = ModelItemData.items[position];
        backdrop.setImageResource(ModelItemData.getImageDrawable(position));
        name.setText(item.getName());
        address.setText(item.getAddress());
        url.setText(item.getUrl());
        email.setText(item.getEmail());
        phone.setText(item.getPhone());

        return view;
    }


}
