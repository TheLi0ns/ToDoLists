package io.github.theli0ns.todolists;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShowTextDialog extends AlertDialog.Builder {

    public ShowTextDialog(@NonNull Context context, @Nullable String title, @NonNull String text) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.list_item_text_dialog, null);

        TextView textView = v.findViewById(R.id.ListItemDialog_text);
        textView.setText(text);

        this.setNeutralButton("Ok", null);

        this.setView(v);
        this.setTitle(title);
    }
}
