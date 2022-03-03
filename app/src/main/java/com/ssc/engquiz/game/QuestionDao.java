package com.ssc.engquiz.game;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface QuestionDao {
    @Query("SELECT * from questions_table ORDER BY RANDOM() limit 16")
    LiveData<List<Questions>> getAllQuestions();

    @Query("SELECT * from ANTONYMS_TABLE ORDER BY RANDOM() limit 16")
    LiveData<List<Antonyms>> getAllAntonyms();

    @Query("SELECT * from oneword_table ORDER BY RANDOM() limit 16")
    LiveData<List<Oneword>> getAllOneword();

    @Query("SELECT * from phrases_table ORDER BY RANDOM() limit 16")
    LiveData<List<Phrases>> getAllPhrases();

    @Query("SELECT * from synonyms_table ORDER BY RANDOM() limit 16")
    LiveData<List<Synonyms>> getAllSynonyms();

    @Query("SELECT * from preposition_table ORDER BY RANDOM() limit 16")
    LiveData<List<Preposition>> getAllPreposition();

    @Insert
    void insert(Antonyms questions);
    @Insert
    void insert(Synonyms questions);

    @Insert
    void insert(Oneword questions);

    @Insert
    void insert(Phrases questions);

    @Insert
    void insert(Preposition questions);

    @Insert
    void insert(Questions questions);
}
