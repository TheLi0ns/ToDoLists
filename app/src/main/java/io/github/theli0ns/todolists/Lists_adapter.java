package io.github.theli0ns.todolists;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Lists_adapter extends RecyclerView.Adapter<Lists_adapter.ViewHolder> {

    private List<ListRecord> lists;
    private Context context;
    private ListSelectListener listSelectListener;

    public Lists_adapter(List<ListRecord> lists, ListSelectListener listSelectListener, Context context) {
        this.lists = lists;
        this.listSelectListener = listSelectListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_viewholder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListRecord list = lists.get(position);

        holder.name_textview.setText(list.getName());
        holder.cardView.setCardBackgroundColor(Color.parseColor(list.getColor().code));

        holder.deleteList_btn.setOnClickListener(view -> {
            if(MainActivity.db.deleteList(list)){
                MainActivity.lists.remove(list);
                MainActivity.lists_adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(context, "Error removing list", Toast.LENGTH_LONG).show();
            }
        });

        holder.cardView.setOnClickListener(view -> listSelectListener.onItemClicked(list));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_textview;
        CardView cardView;
        Button deleteList_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textview = itemView.findViewById(R.id.ListName);
            cardView = itemView.findViewById(R.id.Card);
            deleteList_btn = itemView.findViewById(R.id.deleteListBtn);
        }
    }
}
