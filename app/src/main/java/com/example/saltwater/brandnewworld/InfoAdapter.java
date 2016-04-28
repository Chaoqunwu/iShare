package com.example.saltwater.brandnewworld;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.saltwater.brandnewworld.picSelect.ImageBucket;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
/**
 * Created by Saltwater on 2016/4/6.
 */
public class InfoAdapter extends ArrayAdapter<Map<String,Object>> {


    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_NUMBERGOOD = "numGood";
    public static final String KEY_NUMBERCOMMENT = "numComment";
    public static final String KEY_COMMENT = "comment";

    private int resourceId;
    List<Map<String,Object>> mData;
    private  Context lContext;
    protected ImageLoader imageLoader;
    ViewHolder viewHolder;

    public InfoAdapter(Context context,int textViewResourceId,List<Map<String,Object>> objects)
    {
        super(context,textViewResourceId,objects);
        lContext = context;
        mData = objects;
        resourceId = textViewResourceId;

    }

    @Override
    public View getView(final int position,View convertView,ViewGroup parent)
    {
        Map<String,Object> map = getItem(position);
        View view;


        //初始化组件
        if(convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) view.findViewById(R.id.list_avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.list_name);
            viewHolder.date = (TextView) view.findViewById(R.id.list_date);
            viewHolder.description = (TextView) view.findViewById(R.id.list_description);
            viewHolder.numGood = (TextView) view.findViewById(R.id.list_number_good);
            viewHolder.numComment = (TextView) view.findViewById(R.id.list_number_comment);
            viewHolder.pressGood = (ImageButton) view.findViewById(R.id.list_button_good);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(getContext()).load(mData.get(position).get(KEY_AVATAR).toString())
                .resize(80, 80).centerCrop()
                .placeholder(android.R.color.darker_gray)
                .transform(new CircleTransform())
                .into(viewHolder.avatar);
        //加载头像
//        imageLoader.displayImage(mData.get(position).get(KEY_AVATAR).toString(),viewHolder.avatar);

        viewHolder.name.setText(mData.get(position).get(KEY_NAME).toString());
        viewHolder.date.setText(mData.get(position).get(KEY_DATE).toString());

//        Picasso.with(getContext()).load((Integer) mData.get(position).get(KEY_IMAGE))
//                .resize(250,250).centerCrop()
//                .placeholder(android.R.color.darker_gray)
//                .into(viewHolder.image);
        //利用GridView存放照片
        ArrayList<String> tempList = (ArrayList<String>) mData.get(position).get(KEY_IMAGE) ;
        NoScrollGridView grid = (NoScrollGridView) view.findViewById(R.id.item_gridview);
        ImageAdapter imageAdapter = new ImageAdapter(lContext,tempList);
        grid.setAdapter(imageAdapter);
        Utility.setGridViewHeightBasedOnChildren(grid);
        imageAdapter.notifyDataSetChanged();

        viewHolder.description.setText(mData.get(position).get(KEY_DESCRIPTION).toString());
        viewHolder.numGood.setText(mData.get(position).get(KEY_NUMBERGOOD).toString());
//        viewHolder.numComment.setText(mData.get(position).get(KEY_NUMBERCOMMENT).toString());

//        View.OnTouchListener listener = new View.OnTouchListener()
//        {
//            private boolean image_record_out;
//
//        }



        return view;
    }



    class ViewHolder {
        ImageView avatar; //头像
        TextView name; //名字
        TextView date; //日期

        TextView description; //描述
        TextView numGood; //点赞人数
        TextView numComment; //评论人数

        ImageView pressGood;

    }




}

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mDrawableList;
    private LayoutInflater mInflater;
    protected ImageLoader imageLoader;
    public ImageAdapter(Context context,List<String> drawableList) {
        mContext = context;
        mDrawableList = drawableList;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mDrawableList.size();
    }

    public Object getItem(int position) {
        return mDrawableList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position,View convertView,ViewGroup parent) {
        GridViewHolder gridViewHolder = new GridViewHolder();
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.grid_item_pic,null);
            gridViewHolder.image = (ImageView) convertView.findViewById(R.id.item_pic);
            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load("file://"+mDrawableList.get(position).toString())
                .resize(250, 250).centerCrop()
                .placeholder(android.R.color.darker_gray)
                .into(gridViewHolder.image);

//        imageLoader.displayImage("file://"+mDrawableList.get(position).toString(), gridViewHolder.image);
//        gridViewHolder.image.setImageBitmap(mDrawableList.get(position));

        return convertView;
    }

    class GridViewHolder {
        ImageView image;
    }


}
