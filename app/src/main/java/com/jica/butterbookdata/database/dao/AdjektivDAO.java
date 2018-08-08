package com.jica.butterbookdata.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.jica.butterbookdata.database.entity.Adjektiv;

import java.util.List;

@Dao
public interface AdjektivDAO {
    //get by ID
    @Query("SELECT * FROM adjektiv WHERE aid = :aid")
    Adjektiv get(int aid);
    @Query("SELECT * FROM adjektiv WHERE word_adjektiv = :adjektiv")
    Adjektiv get(String adjektiv);

    //get All
    @Query("SELECT * FROM adjektiv")
    List<Adjektiv> getAll();

    //delete All
    @Query("DELETE FROM adjektiv")
    void deleteall();

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Adjektiv... data);

    //update
    @Update
    void update(Adjektiv... data);

    //delete
    @Delete
    void delete(Adjektiv... data);
}
