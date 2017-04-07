package com.comp4020.listproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by brett on 07/04/17.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Item> items;
    LayoutInflater inflater;
    public CustomAdapter(Context c, ArrayList<Item> items) {
        this.c = c;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_item,parent,false);
        }
        TextView nameTxt= (TextView) convertView.findViewById(R.id.list_item_string);
        nameTxt.setText(items.get(position).getName());
        final int pos=position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,items.get(pos).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
