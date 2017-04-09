package com.comp4020.listproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Created by brett on 07/04/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    //COLUMNS
    static final String NAME="name";
    static final String LEVEL="level";
    //grocery list db
    static final String gNAME="gname";


    //DB
    static final String DB_NAME="item_info_DB";

    //tables
    static final String TB_NAME="item_TB";
    static final String gTB_NAME="grocery_TB";

    static final int DB_VERSION=1;
    //CREATE TB
    static final String CREATE_TB="CREATE TABLE item_TB(name TEXT PRIMARY KEY,"
            + "level TEXT);";

    static final String gCREATE_TB="CREATE TABLE grocery_TB(gname TEXT PRIMARY KEY);";

    //DROP TB
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;
    static final String gDROP_TB="DROP TABLE IF EXISTS "+gTB_NAME;


    String[] groceryTestData = {"apple", "orange", "strawberry", "banana", "grape",
    "peach", "pear", "cherry", "pineapple", "grapefruit",
    "mango", "avocado", "kiwi", "watermelon", "lemon",
    "cantaloupe", "apricot", "papaya", "blackberry", "berry"};

    String[] inventoryTestData = {"common fig", "tangerine", "pomegranate", "coconut", "date palm",
    "cranberry", "lychee", "persimmon", "passion fruit", "gooseberry",
    "kumquat", "durian", "carambola", "pomelo", "olive",
    "quince", "purple mangosteen", "cherimoya", "prune", "pitaya",
    "loquat", "soursop", "asian pear", "jujube", "boysenberry",
    "tangelo", "longan", "horned melon", "raspberry", "lime",
    "guava", "potato", "carrot", "spinach", "broccoli",
    "pea", "cabbage", "tomato", "onion", "cauliflower",
    "maize", "garden asparagus", "lettuce", "kale", "cucumber",
    "celery", "radish", "turnip", "eggplant", "artichoke"};

    String[] levelChoice = {"High", "Medium", "Low", "Very low"};

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(CREATE_TB);
            db.execSQL(gCREATE_TB);
            ContentValues values=new ContentValues();
            //dataBase.insert(DBHelper.TB_NAME, null, values);
            //values.put(DBHelper.NAME,name);
            //values.put(DBHelper.LEVEL,level );
            String tstName;
            String tstLevel;
            Random rn = new Random();
            for(int i=0; i<groceryTestData.length; i++){
                values=new ContentValues();
                values.put(DBHelper.gNAME,groceryTestData[i]);
                db.insert(DBHelper.gTB_NAME, null, values);
            }
            for(int i=0; i<inventoryTestData.length; i++){
                values=new ContentValues();
                values.put(DBHelper.NAME,inventoryTestData[i]);
                values.put(DBHelper.LEVEL,levelChoice[rn.nextInt(4)]);
                db.insert(DBHelper.TB_NAME, null, values);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TB);
        db.execSQL(gDROP_TB);
        onCreate(db);
    }
}