package com.example.practice.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.database.DbHelper;
import com.example.practice.dialogs.ViewPersonDialog;
import com.example.practice.helper.DateTimeHelper;
import com.example.practice.interfaces.IRespondStatusListener;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.person.AddEditPersonActivity;

import java.util.List;

public class PersonRCVAdapter extends RecyclerView.Adapter<PersonRCVAdapter.PersonRCVHolder> {
    private List<Person> personList;
    private Context context;
    private Activity activity;

    private FragmentManager fragmentManager;

    public PersonRCVAdapter() {
    }

    public PersonRCVAdapter(List<Person> personList, Context context, Activity activity) {
        this.personList = personList;
        this.context = context;
        this.activity = activity;
    }

    public class PersonRCVHolder extends RecyclerView.ViewHolder {
        TextView tvFullname, tvUsername, tvPassword, tvAge, tvSalary,  tvEmploymentStatus, tvCreatedAt, tvUpdatedAt;
        Button btnView, btnEdit, btnDelete;

        public PersonRCVHolder(@NonNull View itemView) {
            super(itemView);
            tvFullname = itemView.findViewById(R.id.tvFullname);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvPassword = itemView.findViewById(R.id.tvPassword);

            tvAge = itemView.findViewById(R.id.tvAge);
            tvSalary = itemView.findViewById(R.id.tvSalary);

            tvEmploymentStatus = itemView.findViewById(R.id.tvEmploymentStatus);

            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvUpdatedAt = itemView.findViewById(R.id.tvUpdatedAt);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnView = itemView.findViewById(R.id.btnView);
        }

    }

    @NonNull
    @Override
    public PersonRCVAdapter.PersonRCVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_person_item, parent, false);
        return new PersonRCVAdapter.PersonRCVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonRCVAdapter.PersonRCVHolder holder, int position) {
        Person person = personList.get(position);
        holder.tvFullname.setText(person.getFullname());
        holder.tvUsername.setText(person.getUsername());
        holder.tvPassword.setText(person.getPassword());

        String createdAt = DateTimeHelper.formatDate("yyyy-MM-dd HH:mm:ss", "MMM dd, yyyy hh:mma", person.getCreatedDateTime());
        holder.tvCreatedAt.setText(createdAt);

        String updatedAt = DateTimeHelper.formatDate("yyyy-MM-dd HH:mm:ss", "MMM dd, yyyy hh:mma", person.getUpdatedDateTime());
        holder.tvUpdatedAt.setText(updatedAt);

        holder.tvAge.setText("Age: " + String.valueOf(person.getAge()));
        holder.tvSalary.setText("Salary: " + String.valueOf(person.getSalary()));
        holder.tvEmploymentStatus.setText("Status: " + person.getEmploymentStatus());

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPersonDialog viewPersonDialog = new ViewPersonDialog(context, person);
                viewPersonDialog.show(fragmentManager, "VIEW_PERSON_INFO");
                viewPersonDialog.getRespondStatus(new IRespondStatusListener() {
                    @Override
                    public void checkStatusWithResult(boolean status, String res) {
                        switch (res) {
                            case "edit":
                                holder.btnEdit.performClick();
                                break;
                            case "delete":
                                holder.btnDelete.performClick();
                                break;
                        }
                    }
                });
                Toast.makeText(context, "View: " + person.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditPersonActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("personId", String.valueOf(person.getId()));
                context.startActivity(intent);
                activity.finish();
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener((View view) -> {
            // Lambda expression ay applicable lang kapag isa lang yung function.

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete");
            builder.setMessage("Are you sure you want to delete this?");
            builder.setPositiveButton("No", (DialogInterface dialog, int which) -> {
                dialog.dismiss();
            });

            builder.setNegativeButton("Yes", (DialogInterface dialog, int which) -> {
                PersonRepository personRepository = new PersonRepository(person, new DbHelper(context));
                String message = personRepository.Delete() ? "success" : "failed";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                personList = personRepository.GetAll();
                notifyDataSetChanged();

                dialog.dismiss();
            });
            builder.show();

        });

    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


}
