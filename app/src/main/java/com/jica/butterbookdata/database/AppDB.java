package com.jica.butterbookdata.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Adjektiv;
import com.jica.butterbookdata.database.entity.Nomen;
import com.jica.butterbookdata.database.entity.Verben;
import com.jica.butterbookdata.database.entity.VerbenMitPraposition;
import com.jica.butterbookdata.database.entity.Word;

//entity 및 version 입력
@Database(entities = {Adjektiv.class, Nomen.class, Verben.class, VerbenMitPraposition.class, Word.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB INSTANCE;
    private static final String DB_NAME = "AppDB.db";

    public abstract AdjektivDAO adjektivDAO();
    public abstract NomenDAO nomenDAO();
    public abstract VerbenDAO verbenDAO();
    public abstract WordDAO wordDAO();

    public static AppDB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }
    private static AppDB buildDatabase(final Context context) {
        return Room.databaseBuilder(context,AppDB.class,DB_NAME).allowMainThreadQueries().build();
    }
}
