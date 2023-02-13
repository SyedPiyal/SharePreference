package com.datasoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft.myapplication.Data.DAO;
import com.datasoft.myapplication.Data.RoomDB;
import com.datasoft.myapplication.Model.Users;

public class Login extends AppCompatActivity {

    EditText LoguserId, Logpassword;
    TextView Logsignup,Forget_pass;
    Button Loglogin;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoguserId = findViewById(R.id.etLogId);
        Logpassword = findViewById(R.id.etLoginPassword);
        Loglogin = findViewById(R.id.btnLogin);
        Logsignup = findViewById(R.id.tvRegister);
        Forget_pass = findViewById(R.id.tvForgotpass);




        Loglogin.setOnClickListener(new View.OnClickListener() {
            @Override //Log
            public void onClick(View v) {

                String userIdText = LoguserId.getText().toString();
                String passwordText = Logpassword.getText().toString();
                if (userIdText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    //perform query
                    RoomDB userDatabse = RoomDB.getInstance(getApplicationContext());
                    DAO dao = userDatabse.dao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Users users = dao.login(userIdText,passwordText);
                            // Log.d("user",users.getUserId());
                            if (users == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                /*SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userId",users.getUserId());
                                editor.putBoolean("userPass",true);
                                editor.commit();

                                SharedPreferences.Editor editor = getSharedPreferences("MyPreference", MODE_PRIVATE).edit();
                                editor.putString("name", users.getUserId());
                                editor.putString("id", users.getPassword());
                                editor.apply();*/
                                //

                                YourPreference.init(getApplicationContext());

                                YourPreference.saveData("name",userIdText);
                                YourPreference.saveData("id",passwordText);


                                //

                                Intent intent =  new Intent(Login.this,Dashboard.class);
                                startActivity(intent);
                                startActivity(new Intent(Login.this, Dashboard.class));
                            }
                        }
                    }).start();
                }
            }
        });

        Logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });


    }
}