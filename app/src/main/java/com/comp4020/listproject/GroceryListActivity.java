package com.comp4020.listproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity {

    public static final String SELECTED_ITEMS = "com.comp4020.listproject.MESSAGE";

    EditText editItem;
    Button addGroceryButton;
    ListView lvGrocery;
    ArrayList<String> items;
    ArrayAdapter<String> aa;

    ArrayList<String> selectedItems = new ArrayList<String>();
    //List<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        editItem = (EditText) findViewById(R.id.edit_item);
        addGroceryButton = (Button) findViewById(R.id.add_grocery_button);
        lvGrocery = (ListView) findViewById(R.id.lvGroceryList);
        lvGrocery.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        items = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, R.layout.check_list_item, items);



        lvGrocery.setAdapter(aa);
        //set OnItemClickListener
        lvGrocery.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if(selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });


        lvGrocery.setLongClickable(true);
        lvGrocery.setItemsCanFocus(true);
        

        lvGrocery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                itemDelete(position);

                return true;
            }
        });


        /*
        lvGrocery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = items.get(i);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
            }
        });
*/
        addGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editItem.getText().toString();
                items.add(item);
                aa.notifyDataSetChanged();
                editItem.setText("");

            }
        });

    }
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

    /** Called when the user taps the Add To Grocery List button */
    public void sendSelectedItems(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_item);
        //String message = editText.getText().toString();
        //Bundle bundle = new Bundle();
        //bundle.putStringArrayList("SELECTED_ITEMS", selectedItems);
        //intent.putExtra(SELECTED_ITEMS, selectedItems);
        intent.putExtra("SELECTED_ITEMS", selectedItems);
        startActivity(intent);
    }

    public void addItem(View view){
        EditText edit_item = (EditText) findViewById(R.id.edit_item);
        String item = edit_item.getText().toString();
        items.add(item);
        ListView lvGroceryList = (ListView) findViewById(R.id.lvGroceryList);
        lvGroceryList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, items));
    }


}
