package com.datasoft.myapplication.Data;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.datasoft.myapplication.Model.Notes;
import com.datasoft.myapplication.Model.Users;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void registerUser(Users users);

    @Query("SELECT * FROM users WHERE userId=(:userId) and password =(:password)")
    Users login(String userId,String password);


    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query("SELECT * FROM notes WHERE UserId=(:UserId)")
    List<Notes> getAllNotes(String UserId);

    @Query("UPDATE notes SET tittle =:tittle, notes=:notes WHERE ID= :id")
    void  update(int id,String tittle,String notes);

    @Delete
    void  delete(Notes notes);


    @Query("UPDATE notes SET pinned=:pin WHERE ID= :id")
    void pin(int id,boolean pin);

}
