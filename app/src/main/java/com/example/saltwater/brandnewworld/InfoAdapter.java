package com.example.saltwater.brandnewworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
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

    private int resourceId;
    List<Map<String,Object>> mData;

    public InfoAdapter(Context context,int textViewResourceId,List<Map<String,Object>> objects)
    {
        super(context,textViewResourceId,objects);
        mData = objects;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        Map<String,Object> map = getItem(position);
        View view;
        ViewHolder viewHolder;

        //初始化组件
        if(convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) view.findViewById(R.id.list_avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.list_name);
            viewHolder.date = (TextView) view.findViewById(R.id.list_date);
            viewHolder.image = (ImageView) view.findViewById(R.id.list_image);
            viewHolder.description = (TextView) view.findViewById(R.id.list_description);
            viewHolder.numGood = (TextView) view.findViewById(R.id.list_number_good);
            viewHolder.numComment = (TextView) view.findViewById(R.id.list_number_comment);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(getContext()).load((Integer) mData.get(position).get(KEY_AVATAR))
                .resize(40, 40).centerCrop()
                .placeholder(android.R.color.darker_gray)
                .transform(new CircleTransform())
                .into(viewHolder.avatar);
        viewHolder.name.setText(mData.get(position).get(KEY_NAME).toString());
        viewHolder.date.setText(mData.get(position).get(KEY_DATE).toString());

        Picasso.with(getContext()).load((Integer) mData.get(position).get(KEY_IMAGE))
                .resize(250,250).centerCrop()
                .placeholder(android.R.color.darker_gray)
                .into(viewHolder.image);

        viewHolder.description.setText(mData.get(position).get(KEY_DESCRIPTION).toString());
        viewHolder.numGood.setText(mData.get(position).get(KEY_NUMBERGOOD).toString());
        viewHolder.numComment.setText(mData.get(position).get(KEY_NUMBERCOMMENT).toString());



        return view;
    }

    class ViewHolder {
        ImageView avatar; //头像
        TextView name; //名字
        TextView date; //日期

        ImageView image; //配图
        TextView description; //描述
        TextView numGood; //点赞人数
        TextView numComment; //评论人数

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