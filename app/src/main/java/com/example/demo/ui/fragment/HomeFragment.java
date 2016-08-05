package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.demo.common.ContractFragment;
import com.example.demo.common.CustomItemDecoration;
import com.example.demo.data.ModelItem;
import com.example.demo.data.ModelItemAdapter;
import com.example.demo.data.ModelItemData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends ContractFragment<HomeFragment.Contract>{

    // callback method implemented by hosting activity
    public interface Contract {
        void listItemClick(int position);
    }

    private RecyclerView mRecyclerView;
    private ModelItemAdapter mAdapter;
    private ModelItemAdapter.ModelItemClickListener mItemClickListener;

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_recycler, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator()); // ?? FIXME
        mRecyclerView.addItemDecoration(new CustomItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_margin),
                getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_margin)
        ));
        mItemClickListener = new ModelItemAdapter.ModelItemClickListener() {
            @Override
            public void onClick(int position) {
                getContract().listItemClick(position);
            }
        };
        List<ModelItem> items = new ArrayList<>(Arrays.asList(ModelItemData.items));
        mAdapter = new ModelItemAdapter(items, mItemClickListener);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }


}
