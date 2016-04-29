package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.saltwater.brandnewworld.picSelect.Bimp;
import com.example.saltwater.brandnewworld.picSelect.PublishedActivity;

public class NewsActivity extends Activity{

    //测试用map
    Map<String,Object> map;
    Map<String,Object> map2;
    Map<String,Object> map3;
    InfoMap tempMap;
    List<Map<String,String>> commentList;

    //mlist用于储存数据
    List<Map<String,Object>> mList;
    ListView mListView;
    InfoAdapter adapter;
    ImageView editMessage;

    //拖动刷新layout
    MaterialRefreshLayout mTest;

    //本地用户数据
    String userType;
    InfoMap userInfo;
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

//        //初始化commentMap——测试用
//        commentMap = new HashMap<>();
//        commentList = new ArrayList<>();
//        commentMap.put(CommentAdapter.KEY_COMMENTNAME,"戴爸爸");
//        commentMap.put(CommentAdapter.KEY_COMMENT,"吼啊!");
//        commentList.add(commentMap);

        //是否隐藏发送、删除按钮
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        if(userType.equals(UserType.PARENT)) {
            editMessage.setVisibility(View.GONE);
            adapter = new InfoAdapter(NewsActivity.this,R.layout.info_item,mList);
        } else {
            adapter = new InfoAdapter(NewsActivity.this,R.layout.info_item,mList);
        }

        //获取本地用户数据
        Bundle bundle = intent.getExtras();
        userInfo = (InfoMap) bundle.getSerializable("userInfo");


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
                List<String> listTemp = new ArrayList<String>();
                listTemp.add("/1.png");
                map2.put(InfoAdapter.KEY_AVATAR, "file:///storage/emulated/0/tieba/qq.jpg");
                map2.put(InfoAdapter.KEY_NAME, "蠢比吴浩");
                map2.put(InfoAdapter.KEY_DATE, "2016.4.6");
                map2.put(InfoAdapter.KEY_IMAGE, listTemp);
                map2.put(InfoAdapter.KEY_DESCRIPTION, "蠢比");
                map2.put(InfoAdapter.KEY_NUMBERGOOD, 100);
                map2.put(InfoAdapter.KEY_NUMBERCOMMENT, 100);


                mList.add(0, map2);
                adapter.notifyDataSetChanged();

                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                List<String> listTemp = new ArrayList<String>();
                listTemp.add("/1.png");
                //测试用初始化 后期向服务器发出请求
                map3.put(InfoAdapter.KEY_AVATAR, "file:///storage/emulated/0/tieba/qq.jpg");
                map3.put(InfoAdapter.KEY_NAME, "蠢比吴浩");
                map3.put(InfoAdapter.KEY_DATE, "2016.4.6");
                map3.put(InfoAdapter.KEY_IMAGE, listTemp);
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

                Intent intent2 = new Intent(NewsActivity.this,PublishedActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("userInfo", userInfo);
                intent2.putExtras(bundle2);
                startActivityForResult(intent2, 1);

                //PublishedActivity.actionStart(NewsActivity.this,userType,userInfo);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //此处map为当前ListView的Item的信息 用于初始化
                InfoMap map = new InfoMap(mList.get(position));
                NewsDetailActivity.actionStart(NewsActivity.this, userType, map, userInfo);
            }
        });

    }

    private void init()
    {
        //测试用数据
        List<String> listTemp = new ArrayList<String>();
        listTemp.add("/storage/emulated/0/tieba/qq.jpg");
//        List<Bitmap> listTemp = new ArrayList<Bitmap>();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testxiaoxin);
//        listTemp.add(bitmap);
        map.put(InfoAdapter.KEY_AVATAR, "file:///storage/emulated/0/tieba/qq.jpg");
        map.put(InfoAdapter.KEY_NAME,"天才戴家唯");
        map.put(InfoAdapter.KEY_DATE, "2016.4.6");
        map.put(InfoAdapter.KEY_IMAGE,listTemp);
        map.put(InfoAdapter.KEY_DESCRIPTION,"人大代表心系民生，每年两会期间所提建议都呼应着老百姓最热切的期望和诉求。2015年广州市两会期间，防范新型信息诈骗以及放宽公积金提取条件这两大民生热点问题都被广州市人大代表写进建议，并在闭会之后继续跟进。至今代表建议解决或落实了吗？老百姓的诉求有没有回音？记者近日追访获悉，相关建议办理已有可喜进展。");
        map.put(InfoAdapter.KEY_NUMBERGOOD,100);
        map.put(InfoAdapter.KEY_NUMBERCOMMENT, 100);

        mList.add(map);
    }

    public static void actionStart(Context context,String type,InfoMap userInfo)
    {
        Intent intent = new Intent(context,NewsActivity.class);
        intent.putExtra("userType", type);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userInfo", userInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode == RESULT_OK) {
            tempMap = (InfoMap) data.getExtras().get("infoMap");
            mList.add(0, tempMap.getMap());
            adapter.notifyDataSetChanged();
        }
    }
}