package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);
        EditText et_mail = (EditText) findViewById(R.id.et_mail);
        EditText et_phone = (EditText) findViewById(R.id.et_phone);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_signup = (Button) findViewById(R.id.btn_signup);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(et_username.getText().toString(), et_password.getText().toString(), et_mail.getText().toString(), et_phone.getText().toString());
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked();
            }
        });
    }

    private void loginClicked() {
        //TODO: RETURN TO PREVIOUS PAGE
        finish();
    }

    private void registerClicked(String username, String password, String mail, String phone) {
        int register = AppUsers.addNewUser(username,password,mail,phone);
        if (register > 0){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("userId",  register);
            intent.putExtra("type",  "Kayıt");
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.user_already_exists, Toast.LENGTH_SHORT).show();
        }
    }
}