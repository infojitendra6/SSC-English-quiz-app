package com.ssc.engquiz.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.ssc.engquiz.R;


import com.ssc.engquiz.activities.jitu;
import com.ssc.engquiz.activities.quiz;
import com.ssc.engquiz.activities.vocabulary;

public class ResultAcitvity extends AppCompatActivity {
    TextView txtHighScore;
    TextView txtTotalQuizQuestion,txtCorrectQuestion,txtWrongQuestion, currentScoreText;
    Button btStartQuizAgain,btMainMenu;
    AdView adView;
    int highScore =0;
    int currentScore=0;
    Toolbar toolbar;
    private static final String SHRED_PREFERENCE = "shared_preference";
    private static final String SHRED_PREFERENCE_HIGH_SCORE = "shared_preference_high_score";

    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_result_acitvity);

        toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Result of QUIZ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtHighScore = findViewById(R.id.result_tv_HighScore);
        currentScoreText = findViewById(R.id.result_tv_currentScore);
        txtTotalQuizQuestion = findViewById(R.id.result_tv_Num_of_Ques);
        txtCorrectQuestion = findViewById(R.id.result_tv_correct_Ques);
        txtWrongQuestion = findViewById(R.id.result_tv_wrong_Ques);


        btStartQuizAgain = findViewById(R.id.bt_result_play_again);
        //banner ads
        MobileAds.initialize(this, "ca-app-pub-8822045494726292/2721463731");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        btStartQuizAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               finish();

            }
        });


        loadHighScore();


        Intent intent = getIntent();
        int score = intent.getIntExtra("UserScore",0);
        int totalQuestion = intent.getIntExtra("TotalQuizQuestions",0);
        int correctQuestions = intent.getIntExtra("CorrectQuestions",0);
        int wrongQuestion = intent.getIntExtra("WrongQuestions",0);

        currentScore=score;
        currentScoreText.setText("Current Score: "+currentScore);
        txtTotalQuizQuestion.setText("Total Questions: " + String.valueOf(totalQuestion));
        txtCorrectQuestion.setText("Correct Questions: " + String.valueOf(correctQuestions));
        txtWrongQuestion.setText("Wrong Questions: " + String.valueOf(wrongQuestion));


        if (score > highScore){

            updateScore(score);
        }



    }

    private void updateScore(int score) {

        highScore = score;

        txtHighScore.setText("High Score: " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHRED_PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHRED_PREFERENCE_HIGH_SCORE,highScore);
        editor.apply();


    }

    private void loadHighScore() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHRED_PREFERENCE,MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHRED_PREFERENCE_HIGH_SCORE,0);
        txtHighScore.setText("High Score: " + String.valueOf(highScore));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            ResultAcitvity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}