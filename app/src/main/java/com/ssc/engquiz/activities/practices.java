package com.ssc.engquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.ssc.engquiz.AppUpdateChecker;
import com.ssc.engquiz.R;
import com.ssc.engquiz.Utils;

public class practices extends AppCompatActivity {
Toolbar toolbar;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    AdView adView;
    private InterstitialAd interstitialAd;
    public static int prac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_practices);

        toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PRACTICES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //banner ads
        MobileAds.initialize(this, "ca-app-pub-8822045494726292/4161488164");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //  start interstitial ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8822045494726292/8010600816");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {  Intent intent = new Intent(practices.this,practreader.class);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().build());

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

                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/synonymsquiz.html");
                }
                else {
                    prac=1;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
                }



            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/antonymsquiz.html");
                }
                else {
                    prac=2;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
                }

            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/oneword.html");
                }
                else {
                    prac=3;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
                }
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/phrasequiz.html");
                }
                else {
                    prac=4;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
                }

            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/commonerrorquiz.html");
                }
                else {
                    prac=5;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
                }
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 21) {
                    btn1("http://infojitu.heliohost.org/fillinthblankquiz.html");
                }
                else {
                    prac=6;

                    if(interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }
                    else
                    {
                        Intent intent = new Intent(practices.this,pdferror.class);
                        startActivity(intent);
                    }
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

            if (Utils.isNetworkConnected(practices.this))
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
            practices.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public  void btn1(String url)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}