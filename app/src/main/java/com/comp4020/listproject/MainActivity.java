package com.comp4020.listproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> names = new ArrayList<String>();

    // создаем адаптер
    MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        names.add("Milk");
        names.add("Sugar");
        names.add("Juice");
        names.add("Cat Food");

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
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setLongClickable(true);
        lvMain.setItemsCanFocus(true);
        adapter = new MyCustomAdapter(this, R.layout.list_item, names);

//        // создаем адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, names);

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                itemDelete(position,id);


                //Intent newActivity = new Intent(this, superleague.class);
                //startActivity(newActivity);

                //  Intent newActivity = new Intent(view.getContext(),agones.class);
                //     startActivity(newActivity);
                return true;
            }
        });

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);



    }
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
    public void itemDelete(final int position, final long id)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(String.format("Remove \"%s\" from the list?", names.get(position)));

        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String b = String.format("You removed \"%s\"",names.get(position));

                        names.remove(position);
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

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
                                    names.add(a);
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
    public class MyCustomAdapter extends ArrayAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private Context context;

        public MyCustomAdapter(Context context, int list_item, ArrayList<String> list) {
            super(context, list_item, list);
            this.list = list;
            this.context = context;
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

            listItemText.setOnLongClickListener(new View.OnLongClickListener (){
                public boolean onLongClick(View v) {
                    String b = String.format("Long pressed on %s item",list.get(position));
                    Toast.makeText(MainActivity.this,b, Toast.LENGTH_LONG).show();
                    return true;
                }

            });
            //Handle buttons and add onClickListeners
            ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButton);

            imageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    list.remove(position); //or some other task
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }
}
