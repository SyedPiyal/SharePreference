package com.datasoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.datasoft.myapplication.Model.Notes;


import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {

    EditText editText_tittle,editText_notes;
    ImageView image_save;
    Notes notes;
    boolean is_old_note = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        image_save= findViewById(R.id.IV_save);
        editText_tittle=findViewById(R.id.ed_tittle);
        editText_notes=findViewById(R.id.ed_add_notes);
        is_old_note=true;

        /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
        String name = prefs.getString("name", "Blank Name"); //"Blank Name" the default value.
        String idName = prefs.getString("id", "Blank Id");*/

        // new

        YourPreference.init(getApplicationContext());
        String name = YourPreference.getData("name");

        //


        notes=new Notes();
        try {
            notes= (Notes) getIntent().getSerializableExtra("old_note");
            editText_tittle.setText(notes.getTittle());
            editText_notes.setText(notes.getNotes());
        }catch (Exception e){
            e.printStackTrace();
        }

        image_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle = editText_tittle.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(NotesActivity.this, "Please Add some notes !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format=new SimpleDateFormat("EEE, d MMM yyyy HH : mm a");
                Date date= new Date();

                if (is_old_note){
                    notes= new Notes();
                }


                Log.v("id",name);
                notes.setTittle(tittle);
                notes.setUserId(name);
                notes.setNotes(description);
                notes.setDate(format.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }



}