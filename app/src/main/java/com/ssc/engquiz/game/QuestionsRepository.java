package com.ssc.engquiz.game;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class QuestionsRepository {
    private QuestionDao mQuestionDao;
    private LiveData<List<Questions>> mAllQuestions;
    private LiveData<List<Antonyms>> mAllAntonyms;
    private LiveData<List<Synonyms>> mAllSynonyms;
    private LiveData<List<Oneword>> mAllAnOneword;
    private LiveData<List<Phrases>> mAllPhrases;
    private LiveData<List<Preposition>> mAllPreposition;



    public QuestionsRepository(Application application){
        QuestionRoomDatabase db = QuestionRoomDatabase.getInstance(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAllQuestions();
        mAllAntonyms = mQuestionDao.getAllAntonyms();
        mAllSynonyms = mQuestionDao.getAllSynonyms();
        mAllAnOneword = mQuestionDao.getAllOneword();
        mAllPhrases = mQuestionDao.getAllPhrases();
        mAllPreposition = mQuestionDao.getAllPreposition();
    }


    public LiveData<List<Questions>> getmAllQuestions(){
        return mAllQuestions;
    }
    public LiveData<List<Antonyms>> getAllmAntonyms(){
        return mAllAntonyms;
    }
    public LiveData<List<Synonyms>> getAllmSynonyms(){
        return mAllSynonyms;
    }
    public LiveData<List<Oneword>> getAllmOneword(){
        return mAllAnOneword;
    }
    public LiveData<List<Phrases>> getAllmPhrases(){
        return mAllPhrases;
    }
    public LiveData<List<Preposition>> getAllmPreposition(){
        return mAllPreposition;
    }

}
