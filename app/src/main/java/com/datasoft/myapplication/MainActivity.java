package com.datasoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
                String name = prefs.getString("name", "Blank Name"); //"Blank Name" the default value.
                String idName = prefs.getString("id", "Blank Id");*/

                //


                YourPreference.init(getApplicationContext());
                String name = YourPreference.getData("name");

                /*SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                String check = sharedPreferences.getString("userId","");
                Boolean check2 = sharedPreferences.getBoolean("userPass",false);*/

                Intent intent;
                if(Objects.equals(name, "blank")){
                    intent = new Intent(MainActivity.this,Login.class);

                }else{
                    intent = new Intent(MainActivity.this,Dashboard.class);
                }
                /*if (check2){
                    intent = new Intent(SplashScreen.this,Dashboard.class);
                }else{
                    intent = new Intent(SplashScreen.this,Login.class);

                }*/
                startActivity(intent);
            }
        },2000);


    }
}