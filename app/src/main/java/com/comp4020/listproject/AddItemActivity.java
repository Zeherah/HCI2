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

/**
 * Created by brett on 07/04/17.
 */

public class AddItemActivity extends Activity implements OnClickListener {
    private Button btn_save;
    private EditText edit_first,edit_last;
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    private String name;
    private int level;
    private boolean isUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        btn_save=(Button)findViewById(R.id.save_btn);
        edit_first=(EditText)findViewById(R.id.frst_editTxt);
        edit_last=(EditText)findViewById(R.id.last_editTxt);

        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            name=getIntent().getExtras().getString("name");
            level=getIntent().getExtras().getInt("level");
            edit_first.setText(name);
            edit_last.setText(level);

        }

        btn_save.setOnClickListener(this);

        mHelper=new DBHelper(this);

    }

    // saveButton click event
    public void onClick(View v) {
        name=edit_first.getText().toString().trim();
        level=Integer.parseInt(edit_last.getText().toString().trim());
        if(name.length()>0 && (level>=0 && level < 4))
        {
            saveData();
        }
        else
        {
            AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddItemActivity.this);
            alertBuilder.setTitle("Invalid Data");
            alertBuilder.setMessage("Please, Enter valid data");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertBuilder.create().show();
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

