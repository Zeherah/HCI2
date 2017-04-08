package com.comp4020.listproject;

/**
 * Created by brett on 07/04/17.
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private ArrayList<String> name;
    private ArrayList<String> level;

    //mHelper = new DBHelper(this);
    //ArrayAdapter<String> dataAdapter;

    //SpinnerAdapter = new SpinnerAdapter();
    public DisplayAdapter(Context c, ArrayList<String> name, ArrayList<String> level) {
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
        //int lvl;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.list_item, null);
            mHolder = new Holder();
            mHolder.txt_name = (TextView) child.findViewById(R.id.list_item_string);
            //mHolder.txt_level = (TextView) child.findViewById(R.id.list_item_level);

            //spinner element
            mHolder.spinner = (Spinner) child.findViewById(R.id.spinner);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }


        mHolder.txt_name.setText(name.get(pos));
        //mHolder.txt_level.setText(level.get(pos).toString());
        Resources res = mContext.getResources();
        //Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, res.getStringArray(R.array.spinner_options));


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mHolder.spinner.setAdapter(dataAdapter);
        int selInt = -1;

        switch(level.get(pos)){
            case "High":
                selInt = 0;
                break;
            case "Medium":
                selInt = 1;
                break;
            case "Low":
                selInt = 2;
                break;
            case "Very low":
                selInt = 3;
                break;
            default:
                break;
        }

        mHolder.spinner.setSelection(selInt);
        if(selInt == 0){
            mHolder.spinner.setBackgroundResource(R.drawable.icon_green_th);
        }else if(selInt == 1){
            mHolder.spinner.setBackgroundResource(R.drawable.icon_yellow_th);

        }else if(selInt == 2){
            mHolder.spinner.setBackgroundResource(R.drawable.icon_orange_th);

        }else{
            mHolder.spinner.setBackgroundResource(R.drawable.icon_red_th);

        }

        //notifyDataSetChanged();

        //switch(level.get(pos)) {
            //case 0: mHolder.spinner.setBackgroundResource(R.drawable.icon_red_th);
                //break;
            //case 1: mHolder.spinner.setBackgroundResource(R.drawable.icon_yellow_th);
               // break;
           // case 2: mHolder.spinner.setBackgroundResource(R.drawable.icon_orange_th);
           //     break;
           // case 3: mHolder.spinner.setBackgroundResource(R.drawable.icon_red_th);
          //      break;
          //  default:
          //      break;
       // }

        mHolder.spinner.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        //List<String> categories = new ArrayList<String>();
        //categories.add("High");
        //categories.add("Medium");
        //categories.add("Low");
        //categories.add("Very low");

        //Resources res = mContext.getResources();
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, res.getStringArray(R.array.spinner_options));


        //dat/home/brett/Downloads/SqliteDemoaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //mHolder.spinner.setAdapter(dataAdapter);

       // ((TextView) mHolder.spinner.getChildAt(0)).setTextColor(Color.RED);
        //lvl = level.get(pos);
        //Toast.makeText(parent.getContext(), "Selected: " + lvl, Toast.LENGTH_LONG).show();
        //int selPos = mHolder.spinner.getSelectedItemPosition();



        return child;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        DBHelper mHelper = new DBHelper(mContext);
        SQLiteDatabase dataBase = mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        //values.put(DBHelper.NAME,name.get());
        //values.put(DBHelper.LEVEL,level );

        //Bad but effective method to get name of selected item?
        View parentView = (View) parent.getParent();
        TextView txtView = (TextView) parentView.findViewById(R.id.list_item_string);
        String sel = txtView.getText().toString();
        Toast.makeText(parent.getContext(), "Selected: " + sel, Toast.LENGTH_LONG).show();
    //YES!
        //now access databuse using this as key and update color


        switch(item) {
            case "High":
                parent.setBackgroundResource(R.drawable.icon_green_th);
                break;
            case "Medium":
                parent.setBackgroundResource(R.drawable.icon_yellow_th);
                break;
            case "Low":
                parent.setBackgroundResource(R.drawable.icon_orange_th);
                break;
            case "Very low":
                parent.setBackgroundResource(R.drawable.icon_red_th);
                break;
            default:
                break;
        }
        values.put(DBHelper.NAME, sel);
        values.put(DBHelper.LEVEL, item);
        dataBase.update(DBHelper.TB_NAME, values, DBHelper.NAME+"='"+sel+"'", null);
        //dataBase.rawQuery("UPDATE "+DBHelper.TB_NAME+" SET "+DBHelper.NAME+"='"+sel+"', "+DBHelper.LEVEL+"='"+item+"' WHERE "+DBHelper.NAME+"='"+sel+"'", null);
        dataBase.close();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public class Holder {

        TextView txt_name;
        TextView txt_level;
        Spinner spinner;

    }

}
