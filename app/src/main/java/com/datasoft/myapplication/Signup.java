package com.datasoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft.myapplication.Data.DAO;
import com.datasoft.myapplication.Data.RoomDB;
import com.datasoft.myapplication.Model.Users;

public class Signup extends AppCompatActivity {
    EditText userId, password,name;
    TextView logIn;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userId = findViewById(R.id.et_userid);
        password = findViewById(R.id.etSignupPassword);
        name = findViewById(R.id.et_userName);
        logIn = findViewById(R.id.tvGotoLogin);
        register = findViewById(R.id.btnReg);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users= new Users();
                users.setUserId(userId.getText().toString());
                users.setPassword(password.getText().toString());
                users.setName(name.getText().toString());

                if (validateInput(users)){

                    RoomDB userDatabase = RoomDB.getInstance(getApplicationContext());
                    DAO dao = userDatabase.dao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dao.registerUser(users);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    finish();
                                    startActivity(new Intent(Signup.this, Login.class));
                                    Toast.makeText(getApplicationContext(), "User Register", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();
                }
                else {
                    Toast.makeText(getApplicationContext(), "fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Signup.this, Login.class));
            }
        });
    }
    private Boolean validateInput(Users users){
        if (users.getName().isEmpty() ||
                users.getPassword().isEmpty() ||
                users.getName().isEmpty()) {
            return false;
        }
        return true;


    }
}