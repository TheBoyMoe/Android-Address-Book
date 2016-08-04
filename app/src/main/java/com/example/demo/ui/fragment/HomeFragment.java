package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.common.ContractFragment;

public class HomeFragment extends ContractFragment<HomeFragment.Contract>{

    public interface Contract {
        void listItemClick();
    }

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_placeholder, container, false);
        TextView tv = (TextView) view.findViewById(R.id.text_place_holder);
        tv.setText(getString(R.string.home_fragment));
        tv.setOnClickListener(loadFragment);
        return view;
    }

    View.OnClickListener loadFragment = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getContract().listItemClick();
        }
    };

}
