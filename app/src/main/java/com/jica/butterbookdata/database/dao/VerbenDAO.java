package com.jica.butterbookdata.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.jica.butterbookdata.database.entity.Verben;

import java.util.List;

@Dao
public interface VerbenDAO {
    //get by ID
    @Query("SELECT * FROM verben WHERE vid = :vid")
    Verben get(int vid);
    @Query("SELECT * FROM verben WHERE verb_wir = :verben")
    Verben get(String verben);

    //get All
    @Query("SELECT * FROM verben")
    List<Verben> getAll();

    //delete All
    @Query("DELETE FROM verben")
    void deleteall();
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Verben... data);

    //update
    @Update
    void update(Verben... data);

    //delete
    @Delete
    void delete(Verben... data);
}
