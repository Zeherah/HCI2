package com.comp4020.listproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
//import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v4.app.DialogFragment;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SearchView;
import org.xmlpull.v1.XmlSerializer;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{//}, SearchView.OnQueryTextListener {

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> selectedItems;
    //SearchView editsearch;
    //android.support.v7.widget.SearchView  editsearch;// = (android.support.v7.widget.SearchView) findViewById(R.id.searchView);
    xmlData xml;
    // создаем адаптер
    //MyCustomAdapter adapter;
    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;
    TextView tvDate;

    ListView lvMain;
    CustomAdapter adapter;
    ArrayList<Item> items = new ArrayList<>();
    //DBAdapter dbAdapter = new DBAdapter(this);
    EditText nameEditText;
    Button saveBtn,retrieveBtn;
    private DBHelper mHelper;


    private SQLiteDatabase dataBase;
    private ArrayList<String> item_name = new ArrayList<String>();

    private ArrayList<Integer> item_level = new ArrayList<Integer>();
    private ListView itemList;

    private AlertDialog.Builder build;
    SearchView sv;

    String searchItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SearchView) findViewById(R.id.search);
        itemList = (ListView) findViewById(R.id.lvMain) ;
        mHelper = new DBHelper(this);

        //lvMain.setClickable(true);
        itemList.setLongClickable(true);
        //lvMain.setItemsCanFocus(true);

        //add new record
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                i.putExtra("update", false);
                startActivity(i);

            }
        });

        //long click to delete data
        itemList.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

                build = new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Delete " + item_name.get(arg2) + " " + item_level.get(arg2));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText( getApplicationContext(),
                                item_name.get(arg2) + " "
                                        + item_level.get(arg2)
                                        + " is deleted.", Toast.LENGTH_LONG).show();

                        dataBase.delete(
                                DBHelper.TB_NAME,
                                DBHelper.NAME + "='"
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

        /*
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(GroceryListActivity.EXTRA_MESSAGE);
        names.add(message);
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        names.add("Milk");
        names.add("Sugar");
        names.add("Juice");
        names.add("Cat Food");
        */
        //dbAdapter.openDB();
        //dbAdapter.add("Coffee", 0);
        //dbAdapter.add("Milk", 0);
        //dbAdapter.add("Sugar", 0);
        //dbAdapter.add("Tea", 0);
        //dbAdapter.closeDB();
/*
        lvMain = (ListView) findViewById(R.id.lvMain);
        editsearch = (SearchView) findViewById(R.id.search);
        adapter = new CustomAdapter(this, items);
        EditText editText;

        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                getItems(newText);
                return false;
            }
        });
*/


        //xml = new xmlData(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // находим список
        ///ListView lvMain = (ListView) findViewById(R.id.lvMain);
        //lvMain.setClickable(true);
        //lvMain.setLongClickable(true);
        //lvMain.setItemsCanFocus(true);
        //adapter = new MyCustomAdapter(this, R.layout.list_item, names);
        //adapter = new MyCustomAdapter(this, R.layout.list_item, xml.getList());

//        // создаем адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, names);

/*
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                itemDelete(position);


                //Intent newActivity = new Intent(this, superleague.class);
                //startActivity(newActivity);

                //  Intent newActivity = new Intent(view.getContext(),agones.class);
                //     startActivity(newActivity);
                return true;
            }
        });
*/
/*

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showDetails(position);


                //Intent newActivity = new Intent(this, superleague.class);
                //startActivity(newActivity);

                //  Intent newActivity = new Intent(view.getContext(),agones.class);
                //     startActivity(newActivity);

            }
        });
*/



        //lvMain.setAdapter(adapter);


        Bundle b = getIntent().getExtras();
        //ArrayList<String> selectedItems;
        //selectedItems = getIntent().getExtras().getStringArrayList("SELECTED_ITEMS");
        if(b != null){
            selectedItems = (ArrayList<String>)b.getStringArrayList("SELECTED_ITEMS");
            for(int i=0; i<selectedItems.size();i++){
                xml.add(selectedItems.get(i));
                adapter.notifyDataSetChanged();
            }
        }

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
            String sql="SELECT * FROM "+DBHelper.TB_NAME+" WHERE "+DBHelper.NAME+" LIKE '%"+searchTerm+"%'";
            mCursor=dataBase.rawQuery(sql,null);
        }else{

            mCursor = dataBase.rawQuery("SELECT * FROM " + DBHelper.TB_NAME, null);
        }

        item_name.clear();
        item_level.clear();
        if (mCursor.moveToFirst()) {
            do {
                item_name.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.NAME)));
                item_level.add(Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DBHelper.LEVEL))));

            } while (mCursor.moveToNext());
        }
        DisplayAdapter disadpt = new DisplayAdapter(MainActivity.this, item_name, item_level);
        itemList.setAdapter(disadpt);
        mCursor.close();
    }






    /*
    public void getItems(String searchTerm) {
        items.clear();
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Item i=null;
        Cursor c=db.retrieve(searchTerm);
        while (c.moveToNext())
        {
            String name=c.getString(0);
            int level=c.getInt(1);
            i=new Item();
            i.setName(name);
            i.setLevel(level);
            items.add(i);
        }
        db.closeDB();
        lvMain.setAdapter(adapter);
    }

*/

    /*
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }*/
    public void update_Adapter()
    {
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        int a;
        //return true;
    }
//    public void itemDelete(int position,long id)
//    {
//        String b = String.format("You removed %s button "+ id,names.get(position));
//
//        names.remove(position);
//        adapter.notifyDataSetChanged();// has to be called to update the main list.
//
//        Toast.makeText(MainActivity.this,b, Toast.LENGTH_LONG).show();
//    }
    public void itemDelete(final int position)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(String.format("Remove \"%s\" from the list?", xml.getName(position)));

        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String b = String.format("You removed \"%s\"",xml.getName(position));
                        xml.remove(position);
                        //names.remove(position);
                        adapter.notifyDataSetChanged();// has to be called to update the main list.

                        Toast.makeText(MainActivity.this,b, Toast.LENGTH_LONG).show();
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
/*
    public void showDetails (final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(String.format("Details", xml.getName(position)));
        alertDialogBuilder.setMessage(String.format("Item name: %s",xml.getName(position))+"\n"+"Urgency: High");


        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       arg0.cancel();
                    }
                });

        alertDialogBuilder.setNeutralButton("Set Reminder",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDatePickerDialog(findViewById(android.R.id.content));//finds current view
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
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current date as the default date in the picker
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        // Create a new instance of DatePickerDialog and return it
//        return new DatePickerDialog(this, this, year, month, day);
//    }
//
//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        // Do something with the date chosen by the user
//    }
//    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CAMERA_BUTTON);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_CAMERA));
            sendOrderedBroadcast(intent, null);
        } else if (id == R.id.nav_grocery_list){
            Intent intent = new Intent(MainActivity.this, GroceryListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View textEntryView = factory.inflate(R.layout.add_dialog,null);
        LinearLayout ll = new LinearLayout(this);

        alertDialogBuilder.setTitle("Add item to list");
        //alertDialogBuilder.setView(textEntryView);
        final EditText item =  new EditText(this);
        item.setHint("Item");
        item.setFocusable(true);
        item.setClickable(true);
        item.setFocusableInTouchMode(true);
        item.setSelectAllOnFocus(true);
        item.setSingleLine(true);
       // item.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(item);
        alertDialogBuilder.setView(ll);

                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //String a = item.getText();
                                if(!item.getText().toString().isEmpty()) {
                                    String b = String.format("You added %s item to the list", item.getText());
                                    Toast.makeText(MainActivity.this, b, Toast.LENGTH_LONG).show();
                                    String a = item.getText().toString();
                                    dbAdapter.openDB();
                                    dbAdapter.add(item.getText().toString(),0);
                                    dbAdapter.closeDB();
                                    this.getItems()
                                    //xml.add(a);
                                    //names.add(a);
                                    //somehow need your array adapter here adapter.
                                    adapter.notifyDataSetChanged();// has to be called to update the main list.
                                }
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }


    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }





    public class MyCustomAdapter extends ArrayAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private ArrayList<String> arrayList;
        private Context context;

        public MyCustomAdapter(Context context, int list_item, ArrayList<String> list) {
            super(context, list_item, list);
            this.list = list;
            this.context = context;
            this.arrayList = new ArrayList<String>();
            this.arrayList.addAll(list);
        }
        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();
            if (charText.length() == 0) {
                list.addAll(arrayList);
            } else {
                for (String wp : arrayList) {
                    if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                        list.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, null);
            }

            //Handle TextView and display string from your list
            TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
            listItemText.setText(list.get(position));
            //listItemText.setClickable(true);

            listItemText.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    showDetails(position);

                }
            });

            listItemText.setOnLongClickListener(new View.OnLongClickListener (){
                public boolean onLongClick(View v) {
//                    String b = String.format("Long pressed on %s item",list.get(position));
                    itemDelete(position);
//                    Toast.makeText(MainActivity.this,b, Toast.LENGTH_LONG).show();
                    return true;
                }

            });
            return view;
//            //Handle buttons and add onClickListeners
//            ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButton);
//
//            imageButton.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    //do something
//
//                    ((ImageButton)v).setImageResource(R.drawable.icon_red_th);
////                    list.remove(position); //or some other task
////                    notifyDataSetChanged();
//                }
//            });
/*remove spinner temoporaily
            Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
//            SpinnerAdapter = new SpinnerAdapter();
            List<String> levels = new ArrayList<String>();
            levels.add("High");nameTxtnameTxt
            levels.add("Medium-high");
            levels.add("Medium");
            levels.add("Low");

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, levels);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            int sel = xml.getLevel(position);
            if(sel < 4)
                spinner.setSelection(sel);
            else
                spinner.setSelection(0);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        xml.setLevel(position,0);
                        ((Spinner) parent).setBackground(getDrawable(R.drawable.icon_green_th));
                    }else if(position == 1){
                        xml.setLevel(position,1);
                        ((Spinner) parent).setBackground(getDrawable(R.drawable.icon_yellow_th));
                    }else if(position == 2){
                        xml.setLevel(position, 2);
                        ((Spinner) parent).setBackground(getDrawable(R.drawable.icon_orange_th));
                    }else if(position == 3){
                        xml.setLevel(position,3);
                        ((Spinner) parent).setBackground(getDrawable(R.drawable.icon_red_th));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    */
}
