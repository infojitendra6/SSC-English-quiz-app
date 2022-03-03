package com.ssc.engquiz.game;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.ssc.engquiz.activities.jitu;
import com.ssc.engquiz.R;
import com.ssc.engquiz.activities.quiz;

public class TimerDialog {
    private Context mContext;
    private Dialog timerDialog;


    public TimerDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void timerDialog(){


        timerDialog = new Dialog(mContext);

        timerDialog.setContentView(R.layout.timer_dialog);
        final Button bttimerDialog = (Button) timerDialog.findViewById(R.id.bt_timerDialog);


        bttimerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timerDialog.dismiss();
                Intent intent = new Intent(mContext, quiz.class);
                mContext.startActivity(intent);


            }
        });

        timerDialog.show();
        timerDialog.setCancelable(false);
        timerDialog.setCanceledOnTouchOutside(false);
        timerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}
