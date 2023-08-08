package io.github.theli0ns.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
        TextnSpinnerDialog dialog = new TextnSpinnerDialog(this, "Enter new list");
        dialog.setSpinnerValues(ListColors.getNamesArray());

        dialog.setPositiveButton("add", (dialogInterface, i) -> {
            String name = dialog.getInputtedText();

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Error name not inserted", Toast.LENGTH_LONG).show();
                return;
            }

            ListRecord newList = new ListRecord(name,
                    ListColors.values()[dialog.getSelectedSpinnerPosition()]);

            long id = db.addNewList(newList);
            if(id != -1){
                newList.setID(id);
                lists.add(newList);
                lists_adapter.notifyItemInserted(lists.size()-1);
            }else{
                Toast.makeText(this, "Error adding new list", Toast.LENGTH_LONG).show();
            }
        });

        dialog.create().show();
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

        TextnSpinnerDialog dialog = new TextnSpinnerDialog(this, "Modify list");

        dialog.setDefaultText(listRecord.getName());
        dialog.setSpinnerValues(ListColors.getNamesArray());
        dialog.setDefaultSpinnerPosition(Arrays.asList(ListColors.values()).indexOf(listRecord.getColor()));

        dialog.setPositiveButton("ok", (dialogInterface, i) -> {
            String name = dialog.getInputtedText();

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Error name not inserted", Toast.LENGTH_LONG).show();
                return;
            }

            listRecord.setName(name);
            listRecord.setColor(ListColors.values()[dialog.getSelectedSpinnerPosition()]);

            if(db.updateList(listRecord) == 1){
                lists.set(pos, listRecord);
                lists_adapter.notifyItemChanged(pos);
            }else{
                Toast.makeText(this, "Error modifying list", Toast.LENGTH_LONG).show();
            }
        });

        dialog.create().show();
        return true;
    }
}