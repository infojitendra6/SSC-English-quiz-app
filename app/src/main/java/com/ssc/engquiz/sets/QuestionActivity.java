package com.ssc.engquiz.sets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ssc.engquiz.MainActivity;
import com.ssc.engquiz.R;
import com.ssc.engquiz.activities.quiz;
import com.ssc.engquiz.game.CorrectDialog;
import com.ssc.engquiz.game.PlayAudioForAnswers;
import com.ssc.engquiz.game.ResultAcitvity;
import com.ssc.engquiz.game.TimerDialog;
import com.ssc.engquiz.game.WrongDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.ssc.engquiz.sets.SetsActivity.category_id;

public class QuestionActivity extends AppCompatActivity {

    TextView txtQuestion;
    TextView textViewScore,textViewQuestionCount,textViewCountDownTimer;


    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rbGroup;
    Button buttonNext;

    boolean answerd = false;

    private FirebaseFirestore firestore;
    private List<QuestionSet> questionList;
    private int setNo;
    private int quesNum, quesNum2;

    private int  questionTotalCount;


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

        setContentView(R.layout.activity_question);

        setupUI();

        textColorofButtons = rb1.getTextColors();  // this is used to change the text colors of the buttons



        timerDialog =  new TimerDialog(this);
        wrongDialog =  new WrongDialog(this);
        correctDialog = new CorrectDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);

        questionList = new ArrayList<>();

        setNo = getIntent().getIntExtra("SETNO",1);
        firestore = FirebaseFirestore.getInstance();

        getQuestionsList();
        quesNum=-1;

        score = 0;

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


    private void getQuestionsList()
    {
        firestore.collection("QUIZ").document("CAT"+String.valueOf(category_id))
                .collection("SET"+String.valueOf(setNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot questions = task.getResult();

                    for (QueryDocumentSnapshot doc:questions) {
                        questionList.add(new QuestionSet(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                        ));
                    }
                    //setQuestion();
                    startQuiz();

                } else {

                    Toast.makeText(QuestionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
                //   loadingDialog.cancel();

            }
        });

    }




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

        questionTotalCount =questionList.size();

        if (quesNum < questionTotalCount -1){
            quesNum++;


            txtQuestion.setText(questionList.get(quesNum).getQuestion());
            rb1.setText(questionList.get(quesNum).getOptionA());
            rb2.setText(questionList.get(quesNum).getOptionB());
            rb3.setText(questionList.get(quesNum).getOptionC());
            rb4.setText(questionList.get(quesNum).getOptionD());


            answerd = false;

            quesNum2=quesNum+1;
            buttonNext.setText("Confirm");

            textViewQuestionCount.setText("Questions: " + quesNum2 +"/" +(questionTotalCount));

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

                        Toast.makeText(QuestionActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();

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
        int jk1=2;

        switch (questionList.get(quesNum).getCorrectAns()) {

            case 1:

                if (questionList.get(quesNum).getCorrectAns() == answerNr) {


                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.WrongDialog1(correctAnswer,this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 2:

                if (questionList.get(quesNum).getCorrectAns() == answerNr) {


                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.WrongDialog1(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);


                }
                break;


            case 3:

                if (questionList.get(quesNum).getCorrectAns() == answerNr) {


                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score,  this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.WrongDialog1(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;


            case 4:

                if (questionList.get(quesNum).getCorrectAns() == answerNr) {


                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);


                    correctAns++;


                    score +=10;  // score = score + 10
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;


                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.WrongDialog1(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswers(FLAG);



                }
                break;
        }

        if (quesNum == questionTotalCount){

            buttonNext.setText("Confirm and Finish");
        }

    }





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

        Intent resultofQuiz = new Intent(QuestionActivity.this, ResultAcitvity2.class);
        resultofQuiz.putExtra("UserScore", score);
        resultofQuiz.putExtra("TotalQuizQuestions",questionTotalCount);
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
//            Intent intent = new Intent(QuestionActivity.this, SetsActivity.class);
//            startActivity(intent);
            QuestionActivity.this.finish();


        }else {

            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }

        backPressedTime = System.currentTimeMillis();

    }



    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        Log.i("DATATA","onStop() in QuestionActivity");
        finish();


    }
}