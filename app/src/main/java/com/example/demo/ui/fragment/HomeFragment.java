package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class HomeFragment extends Fragment{

    // callback method implemented by hosting activity
//    public interface Contract {
//        void listItemClick(int position);
//    }

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new CustomItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_margin),
                getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_margin)
        ));
        final List<ModelItem> items = new ArrayList<>(Arrays.asList(ModelItemData.items));
        ModelItemAdapter.ModelItemClickListener itemClickListener = new ModelItemAdapter.ModelItemClickListener() {
            @Override
            public void onClick(int position) {
                // getContract().listItemClick(position);
                // propagate the call up to the hosting fragment
                ((MainFragment)getParentFragment()).listItemClick(position, items.get(position).getName());
            }
        };
        ModelItemAdapter adapter = new ModelItemAdapter(items, itemClickListener);
        recyclerView.setAdapter(adapter);

        return view;
    }


}
