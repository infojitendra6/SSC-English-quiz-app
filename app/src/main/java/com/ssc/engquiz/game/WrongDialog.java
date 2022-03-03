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

public class WrongDialog {
    private Context mContext;
    private Dialog wrongAnswerDialog;

    private QuizActivity mquizActivity;
    private QuestionActivity mquestionActivity;

    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    void WrongDialog(String correctAnswer,final int jk, QuizActivity quizActivity){

        mquizActivity = quizActivity;
        wrongAnswerDialog = new Dialog(mContext);
        wrongAnswerDialog.setContentView(R.layout.wrong_dialog);
        final Button btwrongAnswerDialog = (Button) wrongAnswerDialog.findViewById(R.id.bt_wrongDialog);
        TextView textView = (TextView) wrongAnswerDialog.findViewById(R.id.textView_Correct_Answer);

        // textView.setText("Correct Ans: " + correctAnswer);

        textView.setText("" + correctAnswer);
        btwrongAnswerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wrongAnswerDialog.dismiss();

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
                else {
                    mquizActivity.setQuestionView();
                }
               // mquizActivity.setQuestionView1();
            }
        });

        wrongAnswerDialog.show();
        wrongAnswerDialog.setCancelable(false);
        wrongAnswerDialog.setCanceledOnTouchOutside(false);

        wrongAnswerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void WrongDialog1(String correctAnswer, QuestionActivity questionActivity){

        mquestionActivity = questionActivity;
        wrongAnswerDialog = new Dialog(mContext);
        wrongAnswerDialog.setContentView(R.layout.wrong_dialog);
        final Button btwrongAnswerDialog = (Button) wrongAnswerDialog.findViewById(R.id.bt_wrongDialog);
        TextView textView = (TextView) wrongAnswerDialog.findViewById(R.id.textView_Correct_Answer);

        // textView.setText("Correct Ans: " + correctAnswer);

        textView.setText("" + correctAnswer);
        btwrongAnswerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wrongAnswerDialog.dismiss();

                 mquestionActivity.setQuestionView();
            }
        });

        wrongAnswerDialog.show();
        wrongAnswerDialog.setCancelable(false);
        wrongAnswerDialog.setCanceledOnTouchOutside(false);

        wrongAnswerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


}
