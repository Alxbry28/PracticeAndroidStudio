package com.example.practice.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.database.DbHelper;
import com.example.practice.helper.DateTimeHelper;
import com.example.practice.interfaces.IRespondStatusListener;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.person.AddEditPersonActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewPersonDialog extends AppCompatDialogFragment {
    private Context context;
    private Person person;
    private IRespondStatusListener iRespondStatusListener;
    public ViewPersonDialog(Context context, Person person) {
        this.context = context;
        this.person = person;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_person, null);

        TextView tvFullname, tvUsername, tvPassword, tvAge, tvSalary, tvEmploymentStatus, tvCreatedAt, tvUpdatedAt;
        tvFullname = view.findViewById(R.id.tvFullname);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvPassword = view.findViewById(R.id.tvPassword);

        tvAge = view.findViewById(R.id.tvAge);
        tvSalary = view.findViewById(R.id.tvSalary);
        tvEmploymentStatus = view.findViewById(R.id.tvEmploymentStatus);

        tvCreatedAt = view.findViewById(R.id.tvCreatedAt);
        tvUpdatedAt = view.findViewById(R.id.tvUpdatedAt);

        tvFullname.setText(person.getFullname());
        tvUsername.setText(person.getUsername());
        tvPassword.setText(person.getPassword());

        String createdAt = DateTimeHelper.formatDate("yyyy-MM-dd HH:mm:ss", "MMM dd, yyyy hh:mma", person.getCreatedDateTime());
        tvCreatedAt.setText(createdAt);

        String updatedAt = DateTimeHelper.formatDate("yyyy-MM-dd HH:mm:ss", "MMM dd, yyyy hh:mma", person.getUpdatedDateTime());
        tvUpdatedAt.setText(updatedAt);

        tvAge.setText("Age: " + String.valueOf(person.getAge()));
        tvSalary.setText("Salary: " + String.valueOf(person.getSalary()));


        tvEmploymentStatus.setText("Status: " + person.getEmploymentStatus());
        builder.setView(view).setTitle("Person Info");

        builder.setNegativeButton("Edit", (DialogInterface dialog, int which) -> {
//            Intent intent = new Intent(context, AddEditPersonActivity.class);
//            intent.putExtra("isEdit", true);
//            intent.putExtra("personId", String.valueOf(person.getId()));

            iRespondStatusListener.checkStatusWithResult(false, "edit");
            dialog.dismiss();
        });

        builder.setNeutralButton("Delete", (DialogInterface dialog, int which) -> {
//            PersonRepository personRepository = new PersonRepository(getPerson(), new DbHelper(context));
//            String message = personRepository.Delete() ? "success" : "failed";
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            iRespondStatusListener.checkStatusWithResult(false, "delete");
            dialog.dismiss();
        });

        builder.setPositiveButton("Close", (DialogInterface dialog, int which) -> {

            dialog.dismiss();

        });


        return builder.create();
    }

    public void getRespondStatus(IRespondStatusListener iRespondStatusListener) {
        this.iRespondStatusListener = iRespondStatusListener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
