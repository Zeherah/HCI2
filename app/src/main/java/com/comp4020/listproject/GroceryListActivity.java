package com.comp4020.listproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity{

    //public static final String SELECTED_ITEMS = "com.comp4020.listproject.MESSAGE";
    String item;
    EditText editItem;
    Button addGroceryButton;
    Button addInventoryButton;
    //ListView lvGrocery;
    //ArrayList<String> items;
    //ArrayAdapter<String> aa;

    private DBHelper mHelper;


    private SQLiteDatabase dataBase;
    private ArrayList<String> item_name = new ArrayList<String>();
    private ListView itemList;

    private AlertDialog.Builder build;

    SearchView sv;

    String searchItem = "";

    ArrayList<String> selectedItems = new ArrayList<String>();
    //List<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        sv = (SearchView) findViewById(R.id.gsearch);
        editItem = (EditText) findViewById(R.id.edit_item);

        itemList = (ListView) findViewById(R.id.lvGroceryList);
        itemList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addGroceryButton = (Button) findViewById(R.id.add_grocery_button);
        addInventoryButton = (Button) findViewById(R.id.add_inventory_button);
        //lvGrocery = (ListView) findViewById(R.id.lvGroceryList);

        mHelper = new DBHelper(this);
        //lvMain.setClickable(true);
        itemList.setLongClickable(true);
        //items = new ArrayList<String>();
        //aa = new ArrayAdapter<String>(this, R.layout.check_list_item, items);

        addGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = editItem.getText().toString();
                //name=edit_name.getText().toString().trim();
                //level=add_spinner.getSelectedItem().toString().trim();
                saveData();

                //items.add(item);
                //aa.notifyDataSetChanged();
                editItem.setText("");
                displayData(searchItem);

            }
        });

        addInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int i;
                ContentValues values;
                for(i=0; i<selectedItems.size(); i++){
                    dataBase.delete(
                            DBHelper.gTB_NAME,
                            DBHelper.gNAME + "='"
                                    + selectedItems.get(i)+"'", null);

                    values=new ContentValues();

                    values.put(DBHelper.NAME,selectedItems.get(i));
                    values.put(DBHelper.LEVEL,"High");
                    dataBase.insert(DBHelper.TB_NAME, null, values);

                }
                selectedItems.clear();
                //dataBase.close();

                finish();

            }
        });
        //long click to delete data
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

                build = new AlertDialog.Builder(GroceryListActivity.this);
                build.setTitle("Delete " + item_name.get(arg2));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText( getApplicationContext(),
                                item_name.get(arg2) + " is deleted.", Toast.LENGTH_LONG).show();

                        dataBase.delete(
                                DBHelper.gTB_NAME,
                                DBHelper.gNAME + "='"
                                        + item_name.get(arg2)+"'", null);
                        displayData(searchItem);
                        dialog.cancel();
                    }
                });

                build.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if(selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                displayData(newText);
                return false;
            }
        });

    }

    /**
     * save data into SQLite
     */
    private void saveData(){
        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DBHelper.gNAME,item);
        //values.put(DBHelper.LEVEL,level );

        //insert data into database
        dataBase.insert(DBHelper.gTB_NAME, null, values);

        //close database
        dataBase.close();
        /*
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                displayData(newText);
                return false;
            }
        });
    */
    }
    @Override
    protected void onResume() {
        displayData(searchItem);
        super.onResume();
    }
    /**
     * displays data from SQLite
     */
    private void displayData(String searchTerm) {
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor;

        if(searchTerm != null && searchTerm.length()>0){
            String sql="SELECT * FROM "+DBHelper.gTB_NAME+" WHERE "+DBHelper.gNAME+" LIKE '%"+searchTerm+"%'";
            mCursor=dataBase.rawQuery(sql,null);
        }else{

            mCursor = dataBase.rawQuery("SELECT * FROM " + DBHelper.gTB_NAME, null);
        }

        item_name.clear();

        if (mCursor.moveToFirst()) {
            do {
                item_name.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.gNAME)));


            } while (mCursor.moveToNext());
        }
        groceryDisplayAdapter disadpt = new groceryDisplayAdapter(GroceryListActivity.this, item_name);
        itemList.setAdapter(disadpt);
        mCursor.close();
    }
/*
    public void itemDelete(final int position)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(String.format("Remove \"%s\" from the list?", items.get(position)));

        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String b = String.format("You removed \"%s\"",items.get(position));

                        items.remove(position);
                        aa.notifyDataSetChanged();// has to be called to update the main list.

                        Toast.makeText(GroceryListActivity.this,b, Toast.LENGTH_LONG).show();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
*/

    //Called when the user taps the Add To Grocery List button
    /*
    public void sendSelectedItems(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        int i;
        ContentValues values;
        for(i=0; i<selectedItems.size(); i++){
            dataBase.delete(
                    DBHelper.gTB_NAME,
                    DBHelper.gNAME + "='"
                            + selectedItems.get(i)+"'", null);

            values=new ContentValues();

            values.put(DBHelper.NAME,selectedItems.get(i));
            values.put(DBHelper.LEVEL,"High");
            dataBase.insert(DBHelper.TB_NAME, null, values);

        }
        selectedItems.clear();
        //dataBase.close();

        startActivity(intent);
    }
*/
/*
public void addItem(View view){
        EditText edit_item = (EditText) findViewById(R.id.edit_item);
        String item = edit_item.getText().toString();
        items.add(item);
        ListView lvGroceryList = (ListView) findViewById(R.id.lvGroceryList);
        lvGroceryList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, items));
    }
*/

}
