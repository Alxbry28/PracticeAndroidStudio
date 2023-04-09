package com.example.practice.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.database.DbHelper;
import com.example.practice.dialogs.ChoicesDialog;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.person.AddEditPersonActivity;
import com.example.practice.views.person.PersonListActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister, btnBack;
    private EditText etFirstname, etMiddlename, etLastname;
    private EditText etAge, etSalary, etEmploymentStatus, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstname = findViewById(R.id.etFirstname);
        etMiddlename = findViewById(R.id.etMiddlename);
        etLastname = findViewById(R.id.etLastname);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmploymentStatus = findViewById(R.id.etEmploymentStatus);

        etEmploymentStatus.setFocusable(false);
        etEmploymentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoicesDialog choicesDialog = new ChoicesDialog();
                choicesDialog.setContext(RegisterActivity.this);
                choicesDialog.setChosen(etEmploymentStatus.getText().toString());

                choicesDialog.show(getSupportFragmentManager(),"SHOW_CHOICES");
                choicesDialog.getChoice(new ChoicesDialog.ChoiceDialogListener() {
                    @Override
                    public void setChoice(String choice) {
                       etEmploymentStatus.setText(choice);
                    }
                });
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Person person = new Person();
                person.setFirstname(etFirstname.getText().toString());
                person.setMiddlename(etMiddlename.getText().toString());
                person.setLastname(etLastname.getText().toString());
                person.setPassword(etPassword.getText().toString());
                person.setUsername(etUsername.getText().toString());
                person.setSalary(Double.parseDouble(etSalary.getText().toString()));
                person.setAge(Integer.parseInt(etAge.getText().toString()));
                person.setEmploymentStatus(etEmploymentStatus.getText().toString());

                PersonRepository personRepository = new PersonRepository(person, new DbHelper(RegisterActivity.this));

                Person personExisted = personRepository.GetByUsername();
                if (personExisted != null) {
                    Toast.makeText(RegisterActivity.this, "user existed", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isSuccess = personRepository.Create();
                String message = isSuccess ? "Registered successfully" : "Failed to Register";

                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                if (isSuccess) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }

}