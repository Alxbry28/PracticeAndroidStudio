package com.example.practice.views.person;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.practice.R;
import com.example.practice.adapters.PersonRCVAdapter;
import com.example.practice.database.DbHelper;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnAddPerson;
    private PersonRepository personRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        personRepository = new PersonRepository(new DbHelper(PersonListActivity.this));
        List<Person> personList = personRepository.GetAll();

        //Mock data
//        personList.add(new Person("Juan", "Rizal", "Dela Cruz", "juan", "password"));
//        personList.add(new Person("Mark", "Rizal", "Dela Cruz", "juan", "password"));
//        personList.add(new Person("Felipe", "Rizal", "Dela Cruz", "juan", "password"));

        recyclerView = findViewById(R.id.rcPersons);
        PersonRCVAdapter personRCVAdapter = new PersonRCVAdapter(personList, PersonListActivity.this, PersonListActivity.this);
        personRCVAdapter.setFragmentManager(getSupportFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(PersonListActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(personRCVAdapter);

        btnAddPerson = findViewById(R.id.btnAdd);
        btnAddPerson.setOnClickListener(v -> {
            Intent intent = new Intent(PersonListActivity.this, AddEditPersonActivity.class);
            startActivity(intent);
            finish();
        });

        back();
    }

    private void back() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                logout();
            }
        });
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonListActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", (DialogInterface dialog, int which) -> {

            dialog.dismiss();
            startActivity(new Intent(PersonListActivity.this, LoginActivity.class));
            finish();

        });

        builder.setNegativeButton("No", (DialogInterface dialog, int which) -> {
            dialog.dismiss();
            dialog.cancel();

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}