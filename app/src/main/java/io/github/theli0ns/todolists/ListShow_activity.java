package io.github.theli0ns.todolists;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListShow_activity extends AppCompatActivity {

    static long list_ID;
    static String list_name;

    public static RecyclerView listItem_recyclerView;

    public static ListItems_Adapter listItems_adapter;
    public static List<ListItemRecord> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(list_name);

        listItems = MainActivity.db.selectAllListItemOnListId(list_ID);

        listItem_recyclerView = findViewById(R.id.ListItemsList);
        listItems_adapter = new ListItems_Adapter(
                listItems,
                listItemRecord -> {
                    listItemRecord.scrollState();
                    listItems_adapter.notifyDataSetChanged();
                },
                this);
        listItem_recyclerView.setAdapter(listItems_adapter);
        listItem_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void addNewListItem(View v){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new list item");
        View view1 = getLayoutInflater().inflate(R.layout.add_dialog, null);

        EditText listItemName_input = view1.findViewById(R.id.newlistName);

        Spinner state_picker_spinner = view1.findViewById(R.id.colorPicker);
        state_picker_spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ListItemRecord.States.getNamesArray()));


        builder.setView(view1);
        builder.setPositiveButton("add", (dialogInterface, i) -> {
            ListItemRecord newListItem = new ListItemRecord(list_ID, listItemName_input.getText().toString(),
                    state_picker_spinner.getSelectedItemPosition());

            long id = MainActivity.db.addNewListItem(newListItem);
            if(id != -1){
                newListItem.setID(id);
                listItems.add(newListItem);
                listItems_adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this, "Error adding new list item", Toast.LENGTH_LONG).show();
            }

        });

        dialog = builder.create();
        dialog.show();
    }

    public static void setList_ID(long list_ID) {
        ListShow_activity.list_ID = list_ID;
    }

    public static void setList_name(String list_name){
        ListShow_activity.list_name = list_name;
    }
}