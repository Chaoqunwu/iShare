package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.saltwater.brandnewworld.picSelect.PublishedActivity;

public class NewsActivity extends Activity{

    //测试用map
    Map<String,Object> map;
    Map<String,Object> map2;
    Map<String,Object> map3;


    //mlist用于储存数据
    List<Map<String,Object>> mList;
    ListView mListView;
    InfoAdapter adapter;
    ImageView editMessage;

    //拖动刷新layout
    MaterialRefreshLayout mTest;


    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        //初始化测试用map
        map = new HashMap<>();
        map2 = new HashMap<>();
        map3 = new HashMap<>();
        //初始化储存数据的list
        mList = new ArrayList<>();
        editMessage = (ImageView) findViewById(R.id.send_message);

        //是否隐藏发送、删除按钮
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        if(userType.equals(UserType.PARENT)) {
            editMessage.setVisibility(View.GONE);
            adapter = new InfoAdapter(NewsActivity.this,R.layout.info_item_p,mList);
        } else {
            adapter = new InfoAdapter(NewsActivity.this,R.layout.info_item,mList);
        }


        //初始化mList，后期用于向服务器提出请求获得初始信息
        init();

        mTest = (MaterialRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.list_view);

        //初始化ListView
        mListView.setAdapter(adapter);

        //设置下拉上划刷新事件
        mTest.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...

                //测试用初始化 后期向服务器发出请求
                map2.put(InfoAdapter.KEY_AVATAR, R.mipmap.ic_launcher);
                map2.put(InfoAdapter.KEY_NAME, "蠢比吴浩");
                map2.put(InfoAdapter.KEY_DATE, "2016.4.6");
                map2.put(InfoAdapter.KEY_IMAGE, R.mipmap.ic_launcher);
                map2.put(InfoAdapter.KEY_DESCRIPTION, "蠢比");
                map2.put(InfoAdapter.KEY_NUMBERGOOD, 100);
                map2.put(InfoAdapter.KEY_NUMBERCOMMENT, 100);


                mList.add(0,map2);
                adapter.notifyDataSetChanged();

                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...

                //测试用初始化 后期向服务器发出请求
                map3.put(InfoAdapter.KEY_AVATAR, R.mipmap.ic_launcher);
                map3.put(InfoAdapter.KEY_NAME, "蠢比吴浩");
                map3.put(InfoAdapter.KEY_DATE, "2016.4.6");
                map3.put(InfoAdapter.KEY_IMAGE, R.mipmap.ic_launcher);
                map3.put(InfoAdapter.KEY_DESCRIPTION, "蠢比");
                map3.put(InfoAdapter.KEY_NUMBERGOOD, 100);
                map3.put(InfoAdapter.KEY_NUMBERCOMMENT, 100);


                mList.add(map3);
                adapter.notifyDataSetChanged();

                materialRefreshLayout.finishRefreshLoadMore();
            }
        });


        editMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishedActivity.actionStart(NewsActivity.this);
            }
        });

    }

    private void init()
    {
        //测试用数据
        map.put(InfoAdapter.KEY_AVATAR, R.drawable.testxiaoxin);
        map.put(InfoAdapter.KEY_NAME,"天才戴家唯");
        map.put(InfoAdapter.KEY_DATE, "2016.4.6");
        map.put(InfoAdapter.KEY_IMAGE,R.drawable.testxiaoxin);
        map.put(InfoAdapter.KEY_DESCRIPTION,"人大代表心系民生，每年两会期间所提建议都呼应着老百姓最热切的期望和诉求。2015年广州市两会期间，防范新型信息诈骗以及放宽公积金提取条件这两大民生热点问题都被广州市人大代表写进建议，并在闭会之后继续跟进。至今代表建议解决或落实了吗？老百姓的诉求有没有回音？记者近日追访获悉，相关建议办理已有可喜进展。");
        map.put(InfoAdapter.KEY_NUMBERGOOD,100);
        map.put(InfoAdapter.KEY_NUMBERCOMMENT, 100);

        mList.add(map);
    }

    public static void actionStart(Context context,String type)
    {
        Intent intent = new Intent(context,NewsActivity.class);
        intent.putExtra("userType",type);
        context.startActivity(intent);
    }
}
