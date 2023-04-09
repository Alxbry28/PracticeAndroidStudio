package com.example.practice.views.person;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.practice.database.DbHelper;
import com.example.practice.databinding.ActivityAddEditPersonBinding;
import com.example.practice.dialogs.ChoicesDialog;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.auth.RegisterActivity;

public class AddEditPersonActivity extends AppCompatActivity {

    private ActivityAddEditPersonBinding binding;
    private boolean isEdit = false;
    private PersonRepository personRepository;
    private Person person;
    private String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //with binding enabled sa gradle (Module:app).

        /*
         * buildFeatures {
         *    viewBinding true
         * }
         * */

        super.onCreate(savedInstanceState);
        binding = ActivityAddEditPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                startActivity(new Intent(AddEditPersonActivity.this, PersonListActivity.class));
                finish();
            }
        });

        person = new Person();
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            personId = getIntent().getStringExtra("personId");
            person.setId(Integer.parseInt(personId));
            Toast.makeText(this, "personId: " + personId, Toast.LENGTH_SHORT).show();

            personRepository = new PersonRepository(person, new DbHelper(AddEditPersonActivity.this));
            person = personRepository.GetById();

            binding.etFirstname.setText(person.getFirstname());
            binding.etMiddlename.setText(person.getMiddlename());
            binding.etLastname.setText(person.getLastname());
            binding.etUsername.setText(person.getUsername());
            binding.etPassword.setText(person.getPassword());
            binding.etAge.setText(String.valueOf(person.getAge()));
            binding.etSalary.setText(String.valueOf(person.getSalary()));
            binding.etEmploymentStatus.setText(person.getEmploymentStatus());

            binding.etUsername.setEnabled(false);
        }

        binding.etEmploymentStatus.setFocusable(false);
        binding.etEmploymentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoicesDialog choicesDialog = new ChoicesDialog();
                choicesDialog.setContext(AddEditPersonActivity.this);
                choicesDialog.setChosen(binding.etEmploymentStatus.getText().toString());
                choicesDialog.show(getSupportFragmentManager(),"SHOW_CHOICES");
                choicesDialog.getChoice(new ChoicesDialog.ChoiceDialogListener() {
                    @Override
                    public void setChoice(String choice) {
                        binding.etEmploymentStatus.setText(choice);
                    }
                });
            }
        });

        binding.btnSave.setOnClickListener(v -> {

            person.setFirstname(binding.etFirstname.getText().toString());
            person.setMiddlename(binding.etMiddlename.getText().toString());
            person.setLastname(binding.etLastname.getText().toString());
            person.setPassword(binding.etPassword.getText().toString());
            person.setUsername(binding.etUsername.getText().toString());
            person.setSalary(Double.parseDouble(binding.etSalary.getText().toString()));
            person.setAge(Integer.parseInt(binding.etAge.getText().toString()));
            person.setEmploymentStatus(binding.etEmploymentStatus.getText().toString());

            personRepository = new PersonRepository(person, new DbHelper(AddEditPersonActivity.this));


            if (isEdit) {
                edit();
            } else {
                Person personExisted = personRepository.GetByUsername();
                if (personExisted != null) {
                    Toast.makeText(AddEditPersonActivity.this, "user existed", Toast.LENGTH_SHORT).show();
                    return;
                }

                create();
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditPersonActivity.this, PersonListActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void create() {
        boolean isSuccess = personRepository.Create();
        String message = isSuccess ? "Created successfully" : "Failed to create";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (isSuccess) {
            Intent intent = new Intent(AddEditPersonActivity.this, PersonListActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void edit() {
        boolean isSuccess = personRepository.Update();
        String message = isSuccess ? "Created successfully" : "Failed to create";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (isSuccess) {
            Intent intent = new Intent(AddEditPersonActivity.this, PersonListActivity.class);
            startActivity(intent);
            finish();
        }

    }

}