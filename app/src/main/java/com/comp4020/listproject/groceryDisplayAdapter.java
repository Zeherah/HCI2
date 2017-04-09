package com.comp4020.listproject;

/**
 * Created by brett on 08/04/17.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class groceryDisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> gname;


    public groceryDisplayAdapter(Context c, ArrayList<String> name) {
        this.mContext = c;

        this.gname = name;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return gname.size();
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
            child = layoutInflater.inflate(R.layout.check_list_item, null);
            mHolder = new Holder();
            mHolder.txt_name = (CheckedTextView) child.findViewById(R.id.txt_title);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }

        mHolder.txt_name.setText(gname.get(pos));


        return child;
    }

    public class Holder {

        TextView txt_name;


    }

}
