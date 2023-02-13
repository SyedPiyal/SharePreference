package com.datasoft.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.datasoft.myapplication.Adapters.NotesListAdapter;
import com.datasoft.myapplication.Data.RoomDB;
import com.datasoft.myapplication.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;



public class Dashboard extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_btn;
    SearchView searchView;
    Notes selected_notes;
    Button LogOutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
        String name = prefs.getString("name", "Blank Name"); //"Blank Name" the default value.
        String idName = prefs.getString("id", "Blank Id");*/

        YourPreference.init(getApplicationContext());
        String name = YourPreference.getData("name");

        recyclerView = findViewById(R.id.RV_recycle);
        fab_btn = findViewById(R.id.FB_floting);
        searchView = findViewById(R.id.sv_search);
        LogOutButton = findViewById(R.id.btnLogOut);
        database = RoomDB.getInstance(this);
        notes = database.dao().getAllNotes(name);
        updateRecycler(notes);



        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, NotesActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();*/

                YourPreference.init(getApplicationContext());
                YourPreference.clearData();

                Intent intent =  new Intent(Dashboard.this,Login.class);
                startActivity(intent);

            }
        });


    }

    private void filter(String newText){
        List<Notes> filter_list= new ArrayList<>();
        for (Notes single_notes : notes){
            if (single_notes.getTittle().toLowerCase().contains(newText.toLowerCase())
                    || single_notes.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filter_list.add(single_notes);
            }
        }
        notesListAdapter.filtered_list(filter_list);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode==101){
            if (resultCode== Activity.RESULT_OK){
                /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
                String name = prefs.getString("name", "Blank Name"); //"Blank Name" the default value.
                String idName = prefs.getString("id", "Blank Id");*/
                //
                YourPreference.init(getApplicationContext());
                String name = YourPreference.getData("name");
                //

                Notes new_notes= (Notes)  data.getSerializableExtra("note");
                database.dao().insert(new_notes);
                notes.clear();
                notes.addAll(database.dao().getAllNotes(name));
                notesListAdapter.notifyDataSetChanged();
            }
        }
        else if (requestCode==102){
            if (resultCode==Activity.RESULT_OK){
                /*SharedPreferences prefs = getSharedPreferences("MyPreference", MODE_PRIVATE);
                String name = prefs.getString("name", "Blank Name"); //"Blank Name" the default value.
                String idName = prefs.getString("id", "Blank Id");*/
                //
                YourPreference.init(getApplicationContext());
                String name = YourPreference.getData("name");
                //
                Notes new_notes= (Notes) data.getSerializableExtra("note");
                database.dao().update(new_notes.getID(),new_notes.getTittle(),new_notes.getNotes());
                notes.clear();
                notes.addAll(database.dao().getAllNotes(name));
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter=new NotesListAdapter(Dashboard.this,notes,notesClick);
        recyclerView.setAdapter(notesListAdapter);
    }


    private  final  NotesClick notesClick=new NotesClick() {
        @Override
        public void onClick(Notes notes) {

            Intent intent=new Intent(Dashboard.this,NotesActivity.class);
            intent.putExtra("old_note",notes);
            startActivityForResult(intent,102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selected_notes=new Notes();
            selected_notes=notes;
            showPopup(cardView);

        }
    };

    private void showPopup(CardView cardView) {

        PopupMenu popupMenu=new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.show();



    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if (selected_notes.isPinned()){
                    database.dao().pin(selected_notes.getID(),false);
                    Toast.makeText(this, "Unpinned", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.dao().pin(selected_notes.getID(),true);
                    Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                }

                notes.clear();
                notes.addAll(database.dao().getAll());
                notesListAdapter.notifyDataSetChanged();
                return  true;

            case R.id.del:
                database.dao().delete(selected_notes);
                notes.remove(selected_notes);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Notes Deleted", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                return false;

        }


    }
}