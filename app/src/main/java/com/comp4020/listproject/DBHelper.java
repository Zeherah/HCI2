package com.comp4020.listproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brett on 07/04/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    //COLUMNS
    static final String NAME="name";
    static final String LEVEL="level";
    //DB
    static final String DB_NAME="item_info_DB";
    static final String TB_NAME="item_TB";
    static final int DB_VERSION=1;
    //CREATE TB
    static final String CREATE_TB="CREATE TABLE item_TB(name TEXT PRIMARY KEY,"
            + "level TEXT);";
    //DROP TB
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(CREATE_TB);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TB);
        onCreate(db);
    }
}