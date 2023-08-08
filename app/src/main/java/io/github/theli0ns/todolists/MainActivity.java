package io.github.theli0ns.todolists;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListSelectListener{

    public static DatabaseManager db;

    public static RecyclerView lists_recyclerView;

    public static Lists_adapter lists_adapter;
    public static List<ListRecord> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this);

        lists = db.selectAllLists();

        lists_recyclerView = findViewById(R.id.Lists_recyclerView);
        lists_adapter = new Lists_adapter(lists, this, this);
        lists_recyclerView.setAdapter(lists_adapter);
    }

    public void addNewList(View v){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new list");
        View view1 = getLayoutInflater().inflate(R.layout.add_modify_dialog, null);

        EditText listName_input = view1.findViewById(R.id.dialog_inputText);

        Spinner color_picker_spinner = view1.findViewById(R.id.dialog_spinner);
        color_picker_spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ListColors.getNamesArray()));


        builder.setView(view1);
        builder.setPositiveButton("add", (dialogInterface, i) -> {
            ListRecord newList = new ListRecord(listName_input.getText().toString(),
                    ListColors.values()[color_picker_spinner.getSelectedItemPosition()]);

            long id = db.addNewList(newList);
            if(id != -1){
                newList.setID(id);
                lists.add(newList);
                lists_adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this, "Error adding new list", Toast.LENGTH_LONG).show();
            }

        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClicked(ListRecord listRecord) {
        Intent intent = new Intent(MainActivity.this, ListShow_activity.class);
        ListShow_activity.setList_ID(listRecord.getID());
        ListShow_activity.setList_name(listRecord.getName());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClicked(ListRecord listRecord) {
        int pos = lists.indexOf(listRecord);

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modify list");
        View view1 = getLayoutInflater().inflate(R.layout.add_modify_dialog, null);

        EditText listName_input = view1.findViewById(R.id.dialog_inputText);
        listName_input.setText(listRecord.getName());

        Spinner color_picker_spinner = view1.findViewById(R.id.dialog_spinner);
        color_picker_spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ListColors.getNamesArray()));
        color_picker_spinner.setSelection(Arrays.asList(ListColors.values()).indexOf(listRecord.getColor()));


        builder.setView(view1);
        builder.setPositiveButton("ok", (dialogInterface, i) -> {
            listRecord.setName(listName_input.getText().toString());
            listRecord.setColor(ListColors.values()[color_picker_spinner.getSelectedItemPosition()]);

            if(db.updateList(listRecord) == 1){
                lists.set(pos, listRecord);
                lists_adapter.notifyItemChanged(pos);
            }else{
                Toast.makeText(this, "Error modifying list", Toast.LENGTH_LONG).show();
            }

        });

        dialog = builder.create();
        dialog.show();

        return true;
    }
}