package com.example.saltwater.brandnewworld;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saltwater on 2016/4/23.
 */
public class InfoMap implements Serializable {
    private Map<String,Object> map;

    public InfoMap() {map = new HashMap<String,Object>();}

    public void put(String s,Object o)
    {
        map.put(s,o);
    }

    public Object get(String s)
    {
        return map.get(s);
    }
}
