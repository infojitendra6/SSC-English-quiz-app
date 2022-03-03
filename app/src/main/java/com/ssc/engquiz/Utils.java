package com.ssc.engquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

public class Utils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
    public static AlertDialog createAlertBox(Context context) {
        ContextThemeWrapper ctw = new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog);
        AlertDialog.Builder alertBox = new AlertDialog.Builder(ctw);
        alertBox.setInverseBackgroundForced(true);
        AlertDialog alertDialog = alertBox.create();
        alertDialog.setView(LayoutInflater.from(context).inflate(R.layout.dialog_box, null), 0, 0, 0, 0);

        return alertDialog;
    }
    public static void networkNotAvailableAlertBox(final Context context) {
        final AlertDialog alertBox = createAlertBox(context);
        alertBox.show();
        ((ImageView) alertBox.findViewById(R.id.dialog_icon)).setImageResource(R.drawable.error_icon);
        ((TextView) alertBox.findViewById(R.id.dialog_title)).setText("No Internet Connection");
        ((TextView) alertBox.findViewById(R.id.dialog_message)).setText("Please check your Wi-Fi or mobile network connection and try again.");
        ((LinearLayout) alertBox.findViewById(R.id.dialog_two_button)).setVisibility(View.VISIBLE);
        Button alertBoxButton1 = (Button) alertBox.findViewById(R.id.dialog_two_button_button1);
        alertBoxButton1.setText("Settings");
        alertBoxButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View paramView) {
                // TODO Auto-generated method stub
                Activity activity = (Activity) context;
                activity.startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                alertBox.dismiss();
            }
        });

        Button alertBoxButton2 = (Button) alertBox.findViewById(R.id.dialog_two_button_button2);
        alertBoxButton2.setText("Cancel");
        alertBoxButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View paramView) {
                // TODO Auto-generated method stub
                alertBox.dismiss();
            }
        });
    }

}
