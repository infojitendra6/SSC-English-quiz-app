package com.ssc.engquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.ssc.engquiz.AppUpdateChecker;
import com.ssc.engquiz.R;
import com.ssc.engquiz.Utils;
import com.ssc.engquiz.game.QuizActivity;

public class quiz extends AppCompatActivity {
Toolbar toolbar;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    AdView adView;
    private InterstitialAd interstitialAd, interstitialAd2, interstitialAd3, interstitialAd4, interstitialAd5, interstitialAd6;

    public static int jitu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);

        toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PLAY QUIZ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //banner ads
        MobileAds.initialize(this, "ca-app-pub-8822045494726292/4161488164");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        //  start interstitial ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",1);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

        interstitialAd2 = new InterstitialAd(this);
        interstitialAd2.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd2.loadAd(new AdRequest.Builder().build());
        interstitialAd2.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",2);
                startActivity(intent);

            }
        });
        interstitialAd3 = new InterstitialAd(this);
        interstitialAd3.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd3.loadAd(new AdRequest.Builder().build());
        interstitialAd3.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",3);
                startActivity(intent);
                interstitialAd3.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd4 = new InterstitialAd(this);
        interstitialAd4.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd4.loadAd(new AdRequest.Builder().build());
        interstitialAd4.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",4);
                startActivity(intent);
                interstitialAd4.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd5 = new InterstitialAd(this);
        interstitialAd5.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd5.loadAd(new AdRequest.Builder().build());
        interstitialAd5.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",5);
                startActivity(intent);
                interstitialAd5.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd6 = new InterstitialAd(this);
        interstitialAd6.setAdUnitId("ca-app-pub-8822045494726292/3983069354");
        interstitialAd6.loadAd(new AdRequest.Builder().build());
        interstitialAd6.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                Intent intent = new Intent(quiz.this,QuizActivity.class);
                intent.putExtra("jitu",6);
                startActivity(intent);
                interstitialAd6.loadAd(new AdRequest.Builder().build());

            }
        });

        cardView1=(CardView)findViewById(R.id.card1);
        cardView2=(CardView)findViewById(R.id.card2);
        cardView3=(CardView)findViewById(R.id.card3);
        cardView4=(CardView)findViewById(R.id.card4);
        cardView5=(CardView)findViewById(R.id.card5);
        cardView6=(CardView)findViewById(R.id.card6);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",1);
                    startActivity(intent);
                }



            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(interstitialAd2.isLoaded())
                {
                    interstitialAd2.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",2);
                    startActivity(intent);
                }


            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd3.isLoaded())
                {
                    interstitialAd3.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",3);
                    startActivity(intent);
                }


            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd4.isLoaded())
                {
                    interstitialAd4.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",4);
                    startActivity(intent);
                }


            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd5.isLoaded())
                {
                    interstitialAd5.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",5);
                    startActivity(intent);
                }


            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd6.isLoaded())
                {
                    interstitialAd6.show();
                }
                else
                {
                    Intent intent = new Intent(quiz.this,QuizActivity.class);
                    intent.putExtra("jitu",6);
                    startActivity(intent);
                }


            }
        });


    }
    // menu icon

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if (id == R.id.log_out) {
            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody="https://play.google.com/store/apps/details?id=com.ssc.engquiz";
            String shareSub="English Quiz App";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(myIntent,"Share using"));

        }
        else if (id == R.id.check_update) {
            AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);
            appUpdateChecker.checkForUpdate(true);
        }
        else if (id == R.id.other_app) {

            if (Utils.isNetworkConnected(quiz.this))
            {
                try{
                    Uri uri1=Uri.parse("https://play.google.com/store/apps/developer?id=The+Power+of+Science");
                    Intent goToMarkek1=new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarkek1);
                }
                catch (ActivityNotFoundException e)
                {
                    Uri uri1=Uri.parse("https://play.google.com/store/apps/developer?id=The+Power+of+Science");
                    Intent goToMarkek1=new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarkek1);
                }
            }
            else {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                Utils.networkNotAvailableAlertBox(this);
            }

        }



        if(item.getItemId() == android.R.id.home)
        {
            quiz.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}