package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText username = (EditText) findViewById(R.id.editTextUserName);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        Button buttonLogin = (Button) findViewById(R.id.loginButton);
        Button buttonSignup = (Button) findViewById(R.id.signupButton);

        users = User.getUserList();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(username.getText().toString(), password.getText().toString());
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void registerClicked(String username, String password) {

    }

    private void loginClicked(String username, String password) {


        System.out.println(User.tryToLogin(username,password));

    }
}

