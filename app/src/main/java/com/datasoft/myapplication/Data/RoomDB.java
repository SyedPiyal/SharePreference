package com.datasoft.myapplication.Data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.datasoft.myapplication.Model.Notes;
import com.datasoft.myapplication.Model.Users;

@Database(entities = {Users.class, Notes.class}, version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB  userDatabase;
    private static String DATABASE_NAME = "Users";

    public synchronized static RoomDB getInstance(Context context){
        if (userDatabase==null){
            userDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  userDatabase;
    }
    public abstract DAO dao();

}
