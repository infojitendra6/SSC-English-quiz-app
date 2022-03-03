package com.ssc.engquiz.game;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionsRepository mRepository;

    private LiveData<List<Questions>> mAllQuestions;
    private LiveData<List<Antonyms>> mAllAntonyms;
    private LiveData<List<Synonyms>> mAllSynonyms;
    private LiveData<List<Oneword>> mAllOneword;
    private LiveData<List<Phrases>> mAllPhrases;
    private LiveData<List<Preposition>> mAllPreposition;

    public QuestionViewModel(Application application){
        super(application);
        mRepository = new QuestionsRepository(application);
        mAllQuestions = mRepository.getmAllQuestions();
        mAllAntonyms = mRepository.getAllmAntonyms();
        mAllSynonyms = mRepository.getAllmSynonyms();
        mAllOneword = mRepository.getAllmOneword();
        mAllPhrases = mRepository.getAllmPhrases();
        mAllPreposition = mRepository.getAllmPreposition();

    }

    LiveData<List<Questions>> getmAllQuestions()
    {
        return mAllQuestions;
    }
    LiveData<List<Antonyms>> getmAllAntonyms()
    {
        return mAllAntonyms;
    }
    LiveData<List<Synonyms>> getmAllSynonyms()
    {
        return mAllSynonyms;
    }
    LiveData<List<Oneword>> getmAllOneword()
    {
        return mAllOneword;
    }
    LiveData<List<Phrases>> getmAllPhrases()
    {
        return mAllPhrases;
    }
    LiveData<List<Preposition>> getmAllPreposition()
    {
        return mAllPreposition;
    }
}
