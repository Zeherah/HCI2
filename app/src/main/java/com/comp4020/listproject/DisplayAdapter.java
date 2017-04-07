package com.comp4020.listproject;

/**
 * Created by brett on 07/04/17.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> name;
    private ArrayList<Integer> level;


    public DisplayAdapter(Context c, ArrayList<String> name, ArrayList<Integer> level) {
        this.mContext = c;

        this.name = name;
        this.level = level;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return name.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.list_item, null);
            mHolder = new Holder();
            mHolder.txt_name = (TextView) child.findViewById(R.id.list_item_string);
            mHolder.txt_level = (TextView) child.findViewById(R.id.list_item_level);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_name.setText(name.get(pos));
        mHolder.txt_level.setText(level.get(pos).toString());

        return child;
    }

    public class Holder {

        TextView txt_name;
        TextView txt_level;
    }


}
