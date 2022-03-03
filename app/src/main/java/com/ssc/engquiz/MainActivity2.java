package com.ssc.engquiz;

import androidx.appcompat.app.AppCompatActivity;
import hotchemi.android.rate.AppRate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ssc.engquiz.activities.jitu;

public class MainActivity2 extends AppCompatActivity {
    Button show, show2, getStarted, Continue;
    EditText edit_password, edit_name, edit_email, edit_password2;
    TextView toast, name_display, forget;
    private final String Default = "N/A";
    String[] Gender = {"Male", "Female"};
    String gender;
    Spinner spinner;
    MediaPlayer mediaPlayer;
    ImageView icon_user;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file

        String name_file = sharedPreferences.getString("name", Default);
        Integer flag = sharedPreferences.getInt("flag", 0);
        String email_file = sharedPreferences.getString("email", Default);
        String gender_file = sharedPreferences.getString("gender", Default);


        if (name_file.equals(Default)|| email_file.equals(Default) || gender_file.equals(Default))
        {

            setContentView(R.layout.activity_main2);

            edit_email = (EditText) findViewById(R.id.email);
            edit_name = (EditText) findViewById(R.id.name);

            //Spinner for choosing the gender
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, Gender);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new spinner());


            getStarted = (Button) findViewById(R.id.getStarted);
            getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String save_name = edit_name.getText().toString();
                    String save_email = edit_email.getText().toString();


                    if (save_name.equals("") || save_email.equals("")) {
                        try{
                            Toast.makeText(MainActivity2.this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {}
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", save_name);
                        editor.putInt("flag", 1);
                        editor.putString("email", save_email);
                        editor.putString("gender", gender);
                        editor.commit();


                        // for progress Dialog
                        progressBar = new ProgressDialog(v.getContext());
                        progressBar.setCancelable(false);
                        progressBar.setMessage("Please Wait...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();

                        //This handler will add a delay of 3 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                progressBar.cancel();
                                Intent intent = new Intent(MainActivity2.this, jitu.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3500);

                    }

                }
            });


        }




        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(5)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        // for clear dialog box
        //   AppRate.with(this).clearAgreeShowDialog();
        // for all time
       // AppRate.with(this).showRateDialog(this);


    }
    //Used to add some time so that user cannot directly press and exity out of the activity
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);

    }


    //Spinner class to select spinner and also add gender
    class spinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            gender = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //When nothing is selected
            Toast.makeText(getApplicationContext(), "Please Enter the gender", Toast.LENGTH_SHORT).show();
        }
    }

}