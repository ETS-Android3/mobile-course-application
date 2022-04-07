package com.example.mobile30_03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUsers.populateUsers();
        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_signup = (Button) findViewById(R.id.btn_signup);

/*
    savedInstanceState icin deger alma
 */
//        if (savedInstanceState != null){
//            btn_login.setText(savedInstanceState.getString("message"));
//        }


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked(et_username.getText().toString(), et_password.getText().toString());
            }
        });
    }
/*
    savedInstanceState icin deger yazma
 */
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("message", "this is a saved message");
//    }

    private void registerClicked() {
        Log.d(LOG_TAG, "Register clicked!");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginClicked(String username, String password) {
        Log.d(LOG_TAG, "Login clicked!");
        int login = AppUsers.tryToLogin(username,password);
        System.out.println(login);
        if (login > 0){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("userId",  login);
            intent.putExtra("type",  "Giriş");
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
        }

    }
}

