package com.ssc.engquiz.game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssc.engquiz.R;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.ssc.engquiz.activities.jitu;
import com.ssc.engquiz.activities.quiz;

public class QuizActivity extends AppCompatActivity {
    TextView txtQuestion;
    TextView textViewScore,textViewQuestionCount,textViewCountDownTimer;


    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rbGroup;
    Button buttonNext;

    boolean answerd = false;


    List<Questions> quesList;

    List<Antonyms> antList;
    List<Synonyms> synList;
    List<Oneword> onewordList;
    List<Phrases> phrasesList;
    List<Preposition> prepositionsList;

    Questions currentQ;
    Antonyms currentQ1;
    Synonyms currentQ2;
    Oneword currentQ3;
    Phrases currentQ4;
    Preposition currentQ5;

    private int questionCounter=0, questionCounter1=0, questionTotalCount, questionTotalCount1;

    private QuestionViewModel questionViewModel;

    private ColorStateList textColorofButtons;

    private Handler handler = new Handler();

    private int correctAns = 0,wrongAns =0;

    private int score=0;

    private TimerDialog timerDialog;
    private WrongDialog wrongDialog;
    private CorrectDialog correctDialog;

    private int totalSizeofQuiz;

    private int FLAG = 0;
    private PlayAudioForAnswers playAudioForAnswers;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz2);

        setupUI();

        textColorofButtons = rb1.getTextColors();  // this is used to change the text colors of the buttons



        timerDialog =  new TimerDialog(this);
        wrongDialog =  new WrongDialog(this);
        correctDialog = new CorrectDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);


        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
       int  j=getIntent().getIntExtra("jitu", 0);
        if(j==1)
        {
            questionViewModel.getmAllAntonyms().observe(this, new Observer<List<Antonyms>>() {
                @Override
                public void onChanged(@Nullable List<Antonyms> questions1) {
                   // Toast.makeText(QuizActivity.this, "Antonyms :)", Toast.LENGTH_SHORT).show();

                    fetchContent1(questions1);

                }
            });
        }
        else if(j==2) {
            questionViewModel.getmAllSynonyms().observe(this, new Observer<List<Synonyms>>() {
                @Override
                public void onChanged(@Nullable List<Synonyms> questions) {
                  //  Toast.makeText(QuizActivity.this, "Synonyms :)", Toast.LENGTH_SHORT).show();

                    fetchContent2(questions);

                }
            });
        }
        else if(j==3) {
            questionViewModel.getmAllOneword().observe(this, new Observer<List<Oneword>>() {
                @Override
                public void onChanged(@Nullable List<Oneword> questions) {
                  //  Toast.makeText(QuizActivity.this, "Oneword :)", Toast.LENGTH_SHORT).show();

                    fetchContent3(questions);

                }
            });
        }
        else if(j==4) {
            questionViewModel.getmAllPhrases().observe(this, new Observer<List<Phrases>>() {
                @Override
                public void onChanged(@Nullable List<Phrases> questions) {
                 //   Toast.makeText(QuizActivity.this, "phrases :)", Toast.LENGTH_SHORT).show();

                    fetchContent4(questions);

                }
            });
        }
        else if(j==5) {
            questionViewModel.getmAllPreposition().observe(this, new Observer<List<Preposition>>() {
                @Override
                public void onChanged(@Nullable List<Preposition> questions) {
                 //   Toast.makeText(QuizActivity.this, "preposition :)", Toast.LENGTH_SHORT).show();

                    fetchContent5(questions);

                }
            });
        }
        else {
            questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
                @Override
                public void onChanged(@Nullable List<Questions> questions) {
                  //  Toast.makeText(QuizActivity.this, "Mixed :)", Toast.LENGTH_SHORT).show();

                    fetchContent(questions);

                }
            });
        }

        Log.i("DATATA","onCreate() in QuizActivity");
    }


    void setupUI(){


        textViewCountDownTimer = findViewById(R.id.txtTimer);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        txtQuestion = findViewById(R.id.txtQuetsionContainer);


        rbGroup = findViewById(R.id.raido_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonNext = findViewById(R.id.button_Next);

    }

    private void fetchContent1(List<Antonyms> questions) {

        antList = questions;

        startQuiz1();

    }

    private void fetchContent2(List<Synonyms> questions) {

        synList = questions;

        startQuiz2();

    }
    private void fetchContent3(List<Oneword> questions) {

        onewordList = questions;

        startQuiz3();

    }
    private void fetchContent4(List<Phrases> questions) {

        phrasesList = questions;

        startQuiz4();

    }
    private void fetchContent5(List<Preposition> questions) {

        prepositionsList = questions;

        startQuiz5();

    }


    private void fetchContent(List<Questions> questions) {

        quesList = questions;

        startQuiz();

    }


    /*
     *
     *                      SetQuestionsView() Method
     *
     * */






    public void setQuestionView(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = quesList.size();
        Collections.shuffle(quesList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ = quesList.get(questionCounter1);
            //currentQ = antList.get(questionCounter);

            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOptA());
            rb2.setText(currentQ.getOptB());
            rb3.setText(currentQ.getOptC());
            rb4.setText(currentQ.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz() {


        setQuestionView();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution(answerNr,rbselected);



    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        int jk1=0;

        switch (currentQ.getAnswer()) {

            case 1:

                if (currentQ.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk1,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk1,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk1,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk1,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk1, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk1,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk1,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk1,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }




    //antonyms start---------------------------------------------------------------------------------
//antonyms start---------------------------------------------------------------------------------
//antonyms start---------------------------------------------------------------------------------


    public void setQuestionView1(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = antList.size();
        Collections.shuffle(antList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ1 = antList.get(questionCounter1);
            //currentQ = antList.get(questionCounter);

            txtQuestion.setText(currentQ1.getQuestion());
            rb1.setText(currentQ1.getOptA());
            rb2.setText(currentQ1.getOptB());
            rb3.setText(currentQ1.getOptC());
            rb4.setText(currentQ1.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz1() {


        setQuestionView1();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion1();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion1() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution1(answerNr,rbselected);



    }

    private void checkSolution1(int answerNr, RadioButton rbselected) {

        int jk=1;
        switch (currentQ1.getAnswer()) {

            case 1:

                if (currentQ1.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ1.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ1.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ1.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





    //antonyms end---------------------------------------------------------------------------------
//antonyms end---------------------------------------------------------------------------------
//antonyms end---------------------------------------------------------------------------------


//synonyms start---------------------------------------------------------------------------------
//synonyms start---------------------------------------------------------------------------------
//synonyms start---------------------------------------------------------------------------------


    public void setQuestionView2(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = synList.size();
        Collections.shuffle(synList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ2 = synList.get(questionCounter1);


            txtQuestion.setText(currentQ2.getQuestion());
            rb1.setText(currentQ2.getOptA());
            rb2.setText(currentQ2.getOptB());
            rb3.setText(currentQ2.getOptC());
            rb4.setText(currentQ2.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz2() {


        setQuestionView2();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion2();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion2() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution2(answerNr,rbselected);



    }

    private void checkSolution2(int answerNr, RadioButton rbselected) {

        int jk=2;
        switch (currentQ2.getAnswer()) {

            case 1:

                if (currentQ2.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ2.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ2.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ2.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





    //synonyms end---------------------------------------------------------------------------------
//synonyms end---------------------------------------------------------------------------------
//synonyms end---------------------------------------------------------------------------------


//oneword start---------------------------------------------------------------------------------
//oneword start---------------------------------------------------------------------------------
//oneword start---------------------------------------------------------------------------------


    public void setQuestionView3(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = onewordList.size();
        Collections.shuffle(onewordList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ3 = onewordList.get(questionCounter1);


            txtQuestion.setText(currentQ3.getQuestion());
            rb1.setText(currentQ3.getOptA());
            rb2.setText(currentQ3.getOptB());
            rb3.setText(currentQ3.getOptC());
            rb4.setText(currentQ3.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz3() {


        setQuestionView3();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion3();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion3() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution3(answerNr,rbselected);



    }

    private void checkSolution3(int answerNr, RadioButton rbselected) {

        int jk=3;
        switch (currentQ3.getAnswer()) {

            case 1:

                if (currentQ3.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ3.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ3.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ3.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





    //oneword end---------------------------------------------------------------------------------
//oneword end---------------------------------------------------------------------------------
//oneword end---------------------------------------------------------------------------------

//phrase start---------------------------------------------------------------------------------
//phrase start---------------------------------------------------------------------------------
//phrase start---------------------------------------------------------------------------------


    public void setQuestionView4(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = phrasesList.size();
        Collections.shuffle(phrasesList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ4 = phrasesList.get(questionCounter1);


            txtQuestion.setText(currentQ4.getQuestion());
            rb1.setText(currentQ4.getOptA());
            rb2.setText(currentQ4.getOptB());
            rb3.setText(currentQ4.getOptC());
            rb4.setText(currentQ4.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz4() {


        setQuestionView4();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion4();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion4() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution4(answerNr,rbselected);



    }

    private void checkSolution4(int answerNr, RadioButton rbselected) {

        int jk=4;
        switch (currentQ4.getAnswer()) {

            case 1:

                if (currentQ4.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ4.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ4.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ4.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





    //phrase end---------------------------------------------------------------------------------
//phrase end---------------------------------------------------------------------------------
//phrase end---------------------------------------------------------------------------------

//preposition start---------------------------------------------------------------------------------
//preposition start---------------------------------------------------------------------------------
//preposition start---------------------------------------------------------------------------------


    public void setQuestionView5(){

        rbGroup.clearCheck();


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        questionTotalCount1 = prepositionsList.size();
        Collections.shuffle(prepositionsList);
        if (questionCounter1 < questionTotalCount1 -1){

            currentQ5 = prepositionsList.get(questionCounter1);


            txtQuestion.setText(currentQ5.getQuestion());
            rb1.setText(currentQ5.getOptA());
            rb2.setText(currentQ5.getOptB());
            rb3.setText(currentQ5.getOptC());
            rb4.setText(currentQ5.getOptD());
            questionCounter1++;

            answerd = false;


            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + questionCounter1 +"/" +(questionTotalCount1-1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();



        }else {


            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    resultData();

                }
            },2000);
        }
    }



    private void startQuiz5() {


        setQuestionView5();



        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_a));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_c));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_d));

                        break;

                    case R.id.radio_button4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_option_d));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_b));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_c));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_option_a));

                        break;


                }


            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd){

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOpeartion5();

                    }else {

                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

    }

    private void quizOpeartion5() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected =  findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) +1;


        checkSolution5(answerNr,rbselected);



    }

    private void checkSolution5(int answerNr, RadioButton rbselected) {

        int jk=5;
        switch (currentQ5.getAnswer()) {

            case 1:

                if (currentQ5.getAnswer() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (currentQ5.getAnswer() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (currentQ5.getAnswer() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (currentQ5.getAnswer() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,jk,this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer,jk, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (questionCounter == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





    //preposition end---------------------------------------------------------------------------------
//preposition end---------------------------------------------------------------------------------
//preposition end---------------------------------------------------------------------------------




    private void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_wrong));
        rbselected.setTextColor(Color.WHITE);
    }




    // The timer code


    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeLeftinMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeLeftinMillis = 0;
                updateCountDownText();

            }
        }.start();

    }

    private void updateCountDownText() {

        int minutes = (int) (timeLeftinMillis/1000) /60;
        int seconds = (int) (timeLeftinMillis/1000) %60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
        textViewCountDownTimer.setText(timeFormatted);


        if (timeLeftinMillis <10000){

            textViewCountDownTimer.setTextColor(Color.RED);

            FLAG = 3;

            playAudioForAnswers.setAudioforAnswers(FLAG);

        }else {

            textViewCountDownTimer.setTextColor(ContextCompat.getColor(this,R.color.timerFontColor));

        }


        if (timeLeftinMillis == 0){

            Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    timerDialog.timerDialog();

                }
            },2000);

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        Log.i("DATATA","onDestroy in QuizActivity");

    }


    private void resultData(){

        Intent resultofQuiz = new Intent(QuizActivity.this,ResultAcitvity.class);
        resultofQuiz.putExtra("UserScore", score);

        resultofQuiz.putExtra("TotalQuizQuestions",(questionTotalCount1 -1));
        resultofQuiz.putExtra("CorrectQuestions",correctAns);
        resultofQuiz.putExtra("WrongQuestions",wrongAns);
        startActivity(resultofQuiz);

    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){


            if (countDownTimer != null){
                countDownTimer.cancel();
            }

            QuizActivity.this.finish();

        }else {

            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }

        backPressedTime = System.currentTimeMillis();



    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.i("DATATA","onStop() in QuizActivity");
        finish();
    }
}