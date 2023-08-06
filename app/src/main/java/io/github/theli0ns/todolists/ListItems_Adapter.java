package io.github.theli0ns.todolists;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItems_Adapter extends RecyclerView.Adapter<ListItems_Adapter.ViewHolder>{

    private List<ListItemRecord> listItems;
    private Context context;
    private ListItemSelectListener listItemSelectListener;

    public ListItems_Adapter(List<ListItemRecord> listItems, ListItemSelectListener listItemSelectListener, Context context) {
        this.listItems = listItems;
        this.listItemSelectListener = listItemSelectListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_viewholder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemRecord listItem = listItems.get(position);

        holder.listItemName_txtview.setText(listItem.getText());
        holder.state_txtview.setText(listItem.getStateString());

        holder.deleteListItem_btn.setOnClickListener(view -> {
            if(MainActivity.db.deleteListItem(listItem)){
                ListShow_activity.listItems.remove(listItem);
                ListShow_activity.listItems_adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(context, "Error removing list item", Toast.LENGTH_LONG).show();
            }
        });

        holder.state_txtview.setOnClickListener(view -> listItemSelectListener.onItemClicked(listItem));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText listItemName_txtview;
        TextView state_txtview;
        Button deleteListItem_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemName_txtview = itemView.findViewById(R.id.ListItemName);
            state_txtview = itemView.findViewById(R.id.stateName);
            deleteListItem_btn = itemView.findViewById(R.id.deleteListItemBtn);
        }
    }
}
