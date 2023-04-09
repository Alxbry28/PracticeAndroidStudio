package com.example.practice.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.practice.R;

import java.util.Arrays;

public class ChoicesDialog extends AppCompatDialogFragment {

    private Context context;
    private String chosen;
    private String[] choices;
    private ChoiceDialogListener listener;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            //Attaching listener from context.
            listener = (ChoiceDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Getting type of caring from array string from values/strings.xml
        choices = getResources().getStringArray(R.array.choices);

        //Creating alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Getting the index from the array type of caring
        int index = Arrays.asList(choices).indexOf(chosen);

        //Check if the selected index is existing.
        int selectedIndex = (index >= 0) ? index : -1;

        //Setting the alert dialog header text
        builder.setTitle("Choices");

        //Setting the okay button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.setChoice(chosen);
            }
        });

        //Setting the cancel button
        builder.setNegativeButton("Cancel", null);

        //For getting the chosen item
        builder.setSingleChoiceItems(choices, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chosen = choices[i];
                Toast.makeText(context, "Selected Choices: " + chosen, Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();  //Created alert dialog

    }

    public interface ChoiceDialogListener {

        void setChoice(String choice);


    }
    public void getChoice(ChoiceDialogListener listener){
        //For initialization of listener.
        this.listener = listener;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getChosen() {
        return chosen;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
