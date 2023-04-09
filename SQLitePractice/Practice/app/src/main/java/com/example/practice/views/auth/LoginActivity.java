package com.example.practice.views.auth;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.database.DbHelper;
import com.example.practice.models.Person;
import com.example.practice.repository.PersonRepository;
import com.example.practice.views.person.PersonListActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            Person person = new Person();
            person.setPassword(etPassword.getText().toString());
            person.setUsername(etUsername.getText().toString());

            PersonRepository personRepository = new PersonRepository(person, new DbHelper(LoginActivity.this));

            Person personLog = personRepository.Login();

            boolean isSuccess = personLog != null;
            String message = isSuccess ? "Signed In" : "Wrong username or password";

            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(LoginActivity.this, PersonListActivity.class);
                startActivity(intent);
                finish();
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}