package com.example.saltwater.brandnewworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ChaoQun on 2016/4/9.
 */
public class HomeFragment extends Fragment {

    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home_fragment, container, false);
        return parentView;
    }
}

