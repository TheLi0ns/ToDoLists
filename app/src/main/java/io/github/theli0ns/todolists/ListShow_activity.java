package io.github.theli0ns.todolists;

import static io.github.theli0ns.todolists.MainActivity.db;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
        TextnSpinnerDialog dialog = new TextnSpinnerDialog(this, "Enter new list item");
        dialog.setSpinnerValues(ListItemRecord.States.getNamesArray());

        dialog.setPositiveButton("add", (dialogInterface, i) -> {
            String name = dialog.getInputtedText();

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Error name not inserted", Toast.LENGTH_LONG).show();
                return;
            }

            ListItemRecord newListItem = new ListItemRecord(list_ID, name,
                    dialog.getSelectedSpinnerPosition());

            long id = db.addNewListItem(newListItem);
            if(id != -1){
                newListItem.setID(id);
                listItems.add(newListItem);
                listItems_adapter.notifyItemInserted(listItems.size()-1);
            }else{
                Toast.makeText(this, "Error adding new list item", Toast.LENGTH_LONG).show();
            }

        });

        dialog.create().show();
    }

    public static void setList_ID(long list_ID) {
        ListShow_activity.list_ID = list_ID;
    }

    public static void setList_name(String list_name){
        ListShow_activity.list_name = list_name;
    }

    @Override
    public void onItemClicked(ListItemRecord listItemRecord) {
        int pos = listItems.indexOf(listItemRecord);
        listItemRecord.scrollState();
        if(db.updateListItem(listItemRecord) == 1){
            listItems_adapter.notifyItemChanged(pos);
        }else {
            Toast.makeText(this, "Error changing state", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onItemLongClicked(ListItemRecord listItemRecord) {
        int pos = listItems.indexOf(listItemRecord);

        TextnSpinnerDialog dialog = new TextnSpinnerDialog(this, "Modify list item");
        dialog.setDefaultText(listItemRecord.getText());
        dialog.setSpinnerValues(ListItemRecord.States.getNamesArray());
        dialog.setDefaultSpinnerPosition(listItemRecord.getState());

        dialog.setPositiveButton("ok", (dialogInterface, i) -> {

            String name = dialog.getInputtedText();

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Error name not inserted", Toast.LENGTH_LONG).show();
                return;
            }

            listItemRecord.setText(name);
            listItemRecord.setState(dialog.getSelectedSpinnerPosition());

            if(db.updateListItem(listItemRecord) == 1){
                listItems.set(pos, listItemRecord);
                listItems_adapter.notifyItemChanged(pos);
            }else{
                Toast.makeText(this, "Error modifying list item", Toast.LENGTH_LONG).show();
            }

        });

        dialog.create().show();
        return true;
    }
}