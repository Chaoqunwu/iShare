package com.example.saltwater.brandnewworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.Map;

/**
 * Created by ChaoQun on 2016/4/9.
 */
public class FirstActivity extends FragmentActivity implements View.OnClickListener{


    private ResideMenu resideMenu;
    private FirstActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemNews;
    private ResideMenuItem itemSettings;
    private String userType;
    private InfoMap userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_main);
        mContext=this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());

        //获取之前Activity的数据
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        Bundle bundle = getIntent().getExtras();
        userInfo = (InfoMap)bundle.getSerializable("userInfo");
        Log.d("fuuck",userInfo.get("name").toString());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.bule);
        resideMenu.attachToActivity(this);
//        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "主页");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "个人信息");
        itemNews = new ResideMenuItem(this, R.drawable.icon_calendar, "动态");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "设置");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemNews.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemNews, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }
        else if (view == itemProfile){
            changeFragment(new ProfilesFragment());
        }
        else if (view == itemNews){
                changeFragment(new NewsFragment());
        }
//        else if (view == itemSettings){
//            changeFragment(new SettingsFragment());
//        }

        resideMenu.closeMenu();
    }

//    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
//        @Override
//        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
//        }
//    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }


    public static void actionStart(Context context,String type,InfoMap map)
    {
        Intent intent = new Intent(context,FirstActivity.class);
        intent.putExtra("userType", type);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userInfo", map);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String getUserType()
    {
        return userType;
    }

    public InfoMap getUserInfo() {return userInfo;}
}
