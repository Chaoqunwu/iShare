package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Saltwater on 2016/4/24.
 */
public class NewsDetailActivity extends Activity {

    InfoMap infoMap;
    InfoMap userInfo;
    //后面计划commentList将从服务器获取 此处暂且本地初始化
    List<Map<String,String>> commentList;
    private String userType;
    private LinearLayout infoItem;
    private ImageView avatar;
    private TextView name;
    private TextView date;
    private NoScrollGridView image;
    private TextView description;
    private TextView numGood;
    private TextView numComment;
    private ListView mListView;
    private CommentAdapter adapter;
    private MaterialRefreshLayout mTest;
    private Map<String,String> commentMap;
    private Map<String,String> commentMap2;
    private Button sendComment;
    private EditText editComment;
    private ImageView sendSomething;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_main);
        //====================================================
        //初始化ImageLoader
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        //====================================================
        //各种初始化
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        infoMap = (InfoMap) bundle.getSerializable("listInfo");
        userInfo = (InfoMap) bundle.getSerializable("userInfo");
        sendSomething = (ImageView) findViewById(R.id.send_message);
        sendComment = (Button) findViewById(R.id.send_comment);
        editComment = (EditText) findViewById(R.id.edit_comment);
        infoItem = (LinearLayout) findViewById(R.id.info_details);
        avatar = (ImageView) infoItem.findViewById(R.id.list_avatar);
        name = (TextView) infoItem.findViewById(R.id.list_name);
        date = (TextView) infoItem.findViewById(R.id.list_date);
        description = (TextView) infoItem.findViewById(R.id.list_description);
        numGood = (TextView) infoItem.findViewById(R.id.list_number_good);
        numComment = (TextView) infoItem.findViewById(R.id.list_number_comment);
        userType = intent.getStringExtra("userType");
        image = (NoScrollGridView) findViewById(R.id.item_gridview);
        //初始化完毕

        //各种载入
//        imageLoader.displayImage(infoMap.get(InfoAdapter.KEY_AVATAR).toString(),avatar);
        Picasso.with(this).load(infoMap.get(InfoAdapter.KEY_AVATAR).toString())
                .resize(80, 80).centerCrop()
                .placeholder(android.R.color.darker_gray)
                .transform(new CircleTransform())
                .into(avatar);

        name.setText(infoMap.get(InfoAdapter.KEY_NAME).toString());
        date.setText(infoMap.get(InfoAdapter.KEY_DATE).toString());

        List<String> listTemp = (List<String>) infoMap.get(InfoAdapter.KEY_IMAGE);
        ImageAdapter imageAdapter = new ImageAdapter(this,(ArrayList<String>)listTemp);
        image.setAdapter(imageAdapter);


        description.setText(infoMap.get(InfoAdapter.KEY_DESCRIPTION).toString());
        numGood.setText(infoMap.get(InfoAdapter.KEY_NUMBERGOOD).toString());
//        numComment.setText(infoMap.get(InfoAdapter.KEY_NUMBERCOMMENT).toString());
        //载入完毕

        sendSomething.setVisibility(View.GONE);

        //加载评论 评论以一个List来保存
        //读取评论
        //初始化commentMap——测试用
        commentMap = new HashMap<>();
        commentMap2 = new HashMap<>();
        commentList = new ArrayList<>();
        commentMap.put(CommentAdapter.KEY_COMMENTNAME, "戴爸爸");
        commentMap.put(CommentAdapter.KEY_COMMENT, "吼啊!");
        commentList.add(commentMap);
        commentMap2.put(CommentAdapter.KEY_COMMENTNAME, "张爸爸");
        commentMap2.put(CommentAdapter.KEY_COMMENT, "吼啊!");
        commentList.add(commentMap2);
        adapter = new CommentAdapter(NewsDetailActivity.this,R.layout.comment_item,commentList);
        mListView = (ListView) findViewById(R.id.comment_list_view);
        mListView.setAdapter(adapter);

        Utility.setListViewHeightBasedOnChildren(mListView);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = editComment.getText().toString();
                Map<String, String> mTemp = new HashMap<String, String>();
                mTemp.put(CommentAdapter.KEY_COMMENTNAME, userInfo.get(InfoAdapter.KEY_NAME).toString());
                mTemp.put(CommentAdapter.KEY_COMMENT, temp);
                commentList.add(mTemp);
                editComment.setText("");
                adapter.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(mListView);
            }
        });

        //设置评论区上拉下拉
        mTest = (MaterialRefreshLayout) findViewById(R.id.comment_refresh);
        mTest.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...

                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...

                materialRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    public static void actionStart(Context context,String type,InfoMap map,InfoMap userInfo)
    {
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra("userType", type);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listInfo",map);
        bundle.putSerializable("userInfo",userInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
