package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saltwater.brandnewworld.picSelect.PublishedActivity;
import com.example.saltwater.brandnewworld.picSelect.TestPicActivity;

/**
 * Created by ChaoQun on 2016/4/12.
 */
public class NewsFragment extends Fragment {

    private ImageView imageView1;
    private String userType;
    //private ImageView imageView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.news_fragment,container,false);
        imageView1 = (ImageView) view.findViewById(R.id.img1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstActivity activity = (FirstActivity) getActivity();
                NewsActivity.actionStart(getActivity(),activity.getUserType(),activity.getUserInfo());
            }
        });
//        imageView2 = (ImageView) view.findViewById(R.id.img2);
//        imageView2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                PublishedActivity.actionStart(getActivity());
//            }
//        });

        return view;
    }
}
