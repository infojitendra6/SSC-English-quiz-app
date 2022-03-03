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

public class grammar extends AppCompatActivity {
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardView11, cardView12, cardView13, cardView14;
    Toolbar toolbar;
    private InterstitialAd interstitialAd;
    AdView adView;
    public static int pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_grammar);

        toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GRAMMAR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //  start interstitial ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8822045494726292/4416162703");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(grammar.this, pdfreader.class));
                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

        cardView1=(CardView)findViewById(R.id.card1);
        cardView2=(CardView)findViewById(R.id.card2);
        cardView3=(CardView)findViewById(R.id.card3);
        cardView4=(CardView)findViewById(R.id.card4);
        cardView5=(CardView)findViewById(R.id.card5);
        cardView6=(CardView)findViewById(R.id.card6);
        cardView7=(CardView)findViewById(R.id.card7);
        cardView8=(CardView)findViewById(R.id.card8);
        cardView9=(CardView)findViewById(R.id.card9);
        cardView10=(CardView)findViewById(R.id.card10);
        cardView11=(CardView)findViewById(R.id.card11);
        cardView12=(CardView)findViewById(R.id.card12);
        cardView13=(CardView)findViewById(R.id.card13);
        cardView14=(CardView)findViewById(R.id.card14);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              pdf=1;

                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=2;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=3;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=4;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=5;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=6;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=7;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=8;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=9;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=10;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=11;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=12;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=13;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
                    startActivity(intent);
                }

            }
        });
        cardView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf=14;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Intent intent = new Intent(grammar.this,pdfreader.class);
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

            if (Utils.isNetworkConnected(grammar.this))
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
            grammar.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}