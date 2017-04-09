package com.comp4020.listproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by brett on 07/04/17.
 */

public class AddItemActivity extends Activity implements OnClickListener {
    private Button btn_save;
    private Button btn_cancel;
    private EditText edit_name,edit_last;
    private Spinner add_spinner;

    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    private String name;
    private String level;
    private boolean isUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        btn_save=(Button)findViewById(R.id.add_btn);
        btn_cancel=(Button)findViewById(R.id.cancel_btn);
        edit_name=(EditText)findViewById(R.id.item_name_editTxt);
        add_spinner=(Spinner)findViewById(R.id.add_spinner);
        //edit_last=(EditText)find
        // mHelper = new DBHelper(this);
        //lvMain.setClickable(true);

        //???
        //itemList.setLongClickable(true);
        //ViewById(R.id.last_editTxt);

        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            name=getIntent().getExtras().getString("name");
            //level=getIntent().getExtras().getInt("level");
            edit_name.setText(name);
            //edit_last.setText(level);

        }

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //add_spinner.setOnItemSelectedListener(this);

        mHelper=new DBHelper(this);

    }

    // saveButton click event
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.add_btn:
                name=edit_name.getText().toString().trim();
                level=add_spinner.getSelectedItem().toString().trim();
                saveData();
                break;
            case R.id.cancel_btn:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * save data into SQLite
     */
    private void saveData(){
        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DBHelper.NAME,name);
        values.put(DBHelper.LEVEL,level );

        System.out.println("");
        if(isUpdate)
        {
            //update database with new data
            dataBase.update(DBHelper.TB_NAME, values, DBHelper.NAME+"="+name, null);
        }
        else
        {
            //insert data into database
            dataBase.insert(DBHelper.TB_NAME, null, values);
        }
        //close database
        dataBase.close();
        finish();

    }

}

