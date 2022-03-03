package com.ssc.engquiz.game;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ssc.engquiz.R;
import com.ssc.engquiz.sets.QuestionActivity;

public class CorrectDialog {
    private Context mContext;
    private Dialog correctDialog;

    private QuizActivity mquizActivity;
    private QuestionActivity mquestionActivity;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void correctDialog(int score, final int jk, QuizActivity quizActivity){

        mquizActivity = quizActivity;


        correctDialog = new Dialog(mContext);

        correctDialog.setContentView(R.layout.correct_dialog);
        final Button btcorrectDialog = (Button) correctDialog.findViewById(R.id.bt_Score_Dialog);

        Score(score);  //  calling method

        btcorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correctDialog.dismiss();
                if (jk==1)
                {
                    mquizActivity.setQuestionView1();
                }
                else if (jk==2)
                {
                    mquizActivity.setQuestionView2();

                }
                else if (jk==3)
                {
                    mquizActivity.setQuestionView3();

                }
                else if (jk==4)
                {
                    mquizActivity.setQuestionView4();

                }
                else if (jk==5)
                {
                    mquizActivity.setQuestionView5();

                }
                else
                {
                    mquizActivity.setQuestionView();
                }

            }
        });

        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);
        correctDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void correctDialog(int score, QuestionActivity questionActivity){

        mquestionActivity = questionActivity;


        correctDialog = new Dialog(mContext);

        correctDialog.setContentView(R.layout.correct_dialog);
        final Button btcorrectDialog = (Button) correctDialog.findViewById(R.id.bt_Score_Dialog);

        Score(score);  //  calling method

        btcorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correctDialog.dismiss();

                    mquestionActivity.setQuestionView();


            }
        });

        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);
        correctDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void Score(int score) {

        TextView textScore = (TextView) correctDialog.findViewById(R.id.textView_Score);
        textScore.setText("Score: " + String.valueOf(score));
    }

}
