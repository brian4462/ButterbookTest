package com.jica.butterbookdata.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.jica.butterbookdata.database.entity.VerbenMitPraposition;

import java.util.List;

@Dao
public interface ParpositionDAO {
    //get by ID
    @Query("SELECT * FROM verben WHERE vid = :vid")
    VerbenMitPraposition get(int vid);

    //get All
    @Query("SELECT * FROM verben")
    List<VerbenMitPraposition> getAll();

    //insert
    @Insert
    void insert(VerbenMitPraposition... data);

    //update
    @Update
    void update(VerbenMitPraposition... data);

    //delete
    @Delete
    void delete(VerbenMitPraposition... data);
}
