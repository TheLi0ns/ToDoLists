package io.github.theli0ns.todolists;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

public class TextnSpinnerDialog extends AlertDialog.Builder{

    private final Spinner spinner;
    private final EditText inputText;

    private final Context context;

    public TextnSpinnerDialog(@NonNull Context context, String title) {
        super(context);

        this.context = context;

        View v = LayoutInflater.from(context).inflate(R.layout.add_modify_dialog, null);

        this.spinner = v.findViewById(R.id.dialog_spinner);
        this.inputText = v.findViewById(R.id.dialog_inputText);

        this.setView(v);
        this.setTitle(title);
    }

    public void setSpinnerValues(String[] values){
        spinner.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, values));
    }

    public void setDefaultSpinnerPosition(int pos){
        spinner.setSelection(pos);
    }

    public int getSelectedSpinnerPosition(){
        return spinner.getSelectedItemPosition();
    }

    public String getInputtedText(){
        return inputText.getText().toString();
    }

    public void setDefaultText(String text){
        inputText.setText(text);
    }
}
