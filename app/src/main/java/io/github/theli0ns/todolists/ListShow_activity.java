package io.github.theli0ns.todolists;

import static io.github.theli0ns.todolists.MainActivity.db;

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

public class ListShow_activity extends AppCompatActivity implements ListItemSelectListener{

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

        listItems = db.selectAllListItemOnListId(list_ID);

        listItem_recyclerView = findViewById(R.id.ListItemsList);
        listItems_adapter = new ListItems_Adapter(listItems, this, this);
        listItem_recyclerView.setAdapter(listItems_adapter);
        listItem_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void addNewListItem(View v){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new list item");
        View view1 = getLayoutInflater().inflate(R.layout.add_modify_dialog, null);

        EditText listItemName_input = view1.findViewById(R.id.dialog_inputText);

        Spinner state_picker_spinner = view1.findViewById(R.id.dialog_spinner);
        state_picker_spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ListItemRecord.States.getNamesArray()));


        builder.setView(view1);
        builder.setPositiveButton("add", (dialogInterface, i) -> {
            ListItemRecord newListItem = new ListItemRecord(list_ID, listItemName_input.getText().toString(),
                    state_picker_spinner.getSelectedItemPosition());

            long id = db.addNewListItem(newListItem);
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

    @Override
    public void onItemClicked(ListItemRecord listItemRecord) {
        listItemRecord.scrollState();
        listItems_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClicked(ListItemRecord listItemRecord) {
        int pos = listItems.indexOf(listItemRecord);

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modify list item");
        View view1 = getLayoutInflater().inflate(R.layout.add_modify_dialog, null);

        EditText listItemName_input = view1.findViewById(R.id.dialog_inputText);
        listItemName_input.setText(listItemRecord.getText());

        Spinner state_picker_spinner = view1.findViewById(R.id.dialog_spinner);
        state_picker_spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ListItemRecord.States.getNamesArray()));
        state_picker_spinner.setSelection(listItemRecord.getState());


        builder.setView(view1);
        builder.setPositiveButton("ok", (dialogInterface, i) -> {
            listItemRecord.setText(listItemName_input.getText().toString());
            listItemRecord.setState(state_picker_spinner.getSelectedItemPosition());

            if(db.updateListItem(listItemRecord) == 1){
                listItems.set(pos, listItemRecord);
                listItems_adapter.notifyItemChanged(pos);
            }else{
                Toast.makeText(this, "Error modifying list item", Toast.LENGTH_LONG).show();
            }

        });

        dialog = builder.create();
        dialog.show();

        return true;
    }
}