package com.jica.butterbookdata.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jica.butterbookdata.database.entity.Word;

import java.util.List;

@Dao
public interface WordDAO {
    //get by ID
    @Query("SELECT * FROM word WHERE wid = :wid")
    Word get(int wid);

    @Query("SELECT * FROM word WHERE word = :word")
    Word get(String word);

    @Query(("SELECT * FROM word WHERE category = :category AND category_id = :category_id"))
    Word get(int category, int category_id);

    //get All
    @Query("SELECT * FROM word")
    List<Word> getAll();

    //get by quizfinish state
    @Query("SELECT * FROM word WHERE quizfinish = :state")
    List<Word> getByQuizState(int state);

    //get by category
    @Query("SELECT * FROM word WHERE category = :category AND bookmark = :bookmark")
    List<Word> getAllbyCategory_Bookmark(int category,int bookmark);
    @Query("SELECT * FROM word WHERE category = :category ORDER BY random() LIMIT :count")
    List<Word> getAllbyCategory(int category, int count);
    @Query("SELECT * FROM word WHERE category = :category AND quizfinish = :finish ORDER BY random() LIMIT :count")
    List<Word> getAllbyCategory(int category, int finish, int count);
    @Query("SELECT * FROM word WHERE category = :category AND quizfinish = :finish")
    List<Word> getAllbyCategory_Finish(int category, int finish);

    //get by date
    @Query("SELECT * FROM word WHERE date = :date")
    List<Word> getbyDate(String date);
    @Query("SELECT * FROM word WHERE date = :date AND quizfinish = :finish")
    List<Word> getbyDate(String date,int finish);

    //get by bookmark
    @Query("SELECT * FROM word WHERE bookmark = :bookmark")
    List<Word> getbyBookmark(int bookmark);

    //get by study
    @Query("SELECT * FROM word WHERE study = :study")
    List<Word> getbyStudy(int study);
    @Query("SELECT * FROM word WHERE study = :study AND date = :date")
    List<Word> getbyStudy(int study,String date);


    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Word> value);

    //update
    @Update
    void update(Word... data);

    //delete
    @Delete
    void delete(Word... data);

    //delete All
    @Query("DELETE FROM word")
    void deleteall();
}
