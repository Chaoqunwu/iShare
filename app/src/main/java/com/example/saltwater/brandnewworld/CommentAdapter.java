package com.example.saltwater.brandnewworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Saltwater on 2016/4/24.
 */
public class CommentAdapter extends ArrayAdapter<Map<String,String>> {
    public static final String KEY_COMMENTNAME = "commentname";
    public static final String KEY_COMMENT = "comment";

    private int resourceId;
    List<Map<String,String>> mData;

    public CommentAdapter(Context context,int textViewResourceId,List<Map<String,String>> objects)
    {
        super(context,textViewResourceId,objects);
        mData = objects;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        Map<String,String> map = getItem(position);
        View view;
        ViewHolder viewHolder;
        //初始化组件
        if(convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.commentName = (TextView) view.findViewById(R.id.comment_name);
            viewHolder.comment = (TextView) view.findViewById(R.id.comment);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.commentName.setText(mData.get(position).get(KEY_COMMENTNAME).toString() + ": ");
        viewHolder.comment.setText(mData.get(position).get(KEY_COMMENT).toString());

        return view;
    }

    class ViewHolder {
        TextView commentName; //名字
        TextView comment; //日期

    }
}
