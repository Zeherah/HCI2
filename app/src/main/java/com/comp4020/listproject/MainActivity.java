package com.comp4020.listproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.attr.inputType;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> names = new ArrayList<String>();

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

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);


        lvMain.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                testClick(position,id);


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
    public void testClick(int position,long id)
    {
        String b = String.format("You clicked %s button "+ id,names.get(position));
        Toast.makeText(MainActivity.this,b, Toast.LENGTH_LONG).show();
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
                                String b = String.format("You added %s item to the list",item.getText());
                                Toast.makeText(MainActivity.this,b,Toast.LENGTH_LONG).show();
                                String a = item.getText().toString();
                                names.add(a);
                                //somehow need your array adapter here adapter.
                                //adapter.notifyDataSetChanged(); has to be called to update the main list.

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
}
