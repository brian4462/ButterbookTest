package com.jica.butterbookdata.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.jica.butterbookdata.database.entity.Nomen;
import java.util.List;

@Dao
public interface NomenDAO {
    //get Nomen by ID
    @Query("SELECT * FROM nomen WHERE nid = :nid")
    Nomen get(int nid);

    //get Nomen All
    @Query("SELECT * FROM nomen")
    List<Nomen> getAll();

    //insert
    @Insert
    void insert(Nomen... data);

    //update
    @Update
    void update(Nomen... data);

    //delete
    @Delete
    void delete(Nomen... data);
}
