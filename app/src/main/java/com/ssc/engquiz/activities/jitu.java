package com.ssc.engquiz.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ssc.engquiz.AppUpdateChecker;
import com.ssc.engquiz.R;
import com.ssc.engquiz.Utils;
import com.ssc.engquiz.game.ResultAcitvity;

public class jitu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nav_header_nam, nav_header_emal, nav_header_coin;
    ImageView nav_header_imag;
    private InterstitialAd interstitialAd, interstitialAd2, interstitialAd3, interstitialAd4, interstitialAd5, interstitialAd6;

    private ProgressDialog progressBar;
    Integer coins1;
    AdView adView;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_jitu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
// for adds
        //banner ads
        MobileAds.initialize(this, "ca-app-pub-8822045494726292/8034340487");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        //  start interstitial ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8822045494726292/1843143714");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, grammar.class));
                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

        interstitialAd2 = new InterstitialAd(this);
        interstitialAd2.setAdUnitId("ca-app-pub-8822045494726292/6850754352");
        interstitialAd2.loadAd(new AdRequest.Builder().build());
        interstitialAd2.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, vocabulary.class));
                interstitialAd2.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd3 = new InterstitialAd(this);
        interstitialAd3.setAdUnitId("ca-app-pub-8822045494726292/5601124589");
        interstitialAd3.loadAd(new AdRequest.Builder().build());
        interstitialAd3.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, Comerror.class));
                interstitialAd3.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd4 = new InterstitialAd(this);
        interstitialAd4.setAdUnitId("ca-app-pub-8822045494726292/6697519147");
        interstitialAd4.loadAd(new AdRequest.Builder().build());
        interstitialAd4.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, practices.class));
                interstitialAd4.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd5 = new InterstitialAd(this);
        interstitialAd5.setAdUnitId("ca-app-pub-8822045494726292/3348149698");
        interstitialAd5.loadAd(new AdRequest.Builder().build());
        interstitialAd5.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, quiz.class));
                interstitialAd5.loadAd(new AdRequest.Builder().build());

            }
        });
        interstitialAd6 = new InterstitialAd(this);
        interstitialAd6.setAdUnitId("ca-app-pub-8822045494726292/2777304058");
        interstitialAd6.loadAd(new AdRequest.Builder().build());
        interstitialAd6.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()
            {
                startActivity(new Intent(jitu.this, engsets.class));
                interstitialAd6.loadAd(new AdRequest.Builder().build());

            }
        });

        //Set name,email,image in  the navigation side drawer to those we enter in the login page
        String nav_header_name = sharedPreferences.getString("name", "Friend");
        String nav_header_email = sharedPreferences.getString("email", "abc@gmail.com");
        String nav_header_gender = sharedPreferences.getString("gender", "Male");
        View header = navigationView.getHeaderView(0);//Used to get a reference to navigation header
        nav_header_nam = (TextView) header.findViewById(R.id.nav_header_name);
        nav_header_emal = (TextView) header.findViewById(R.id.nav_header_email);
         nav_header_emal = (TextView) header.findViewById(R.id.nav_header_email);
        nav_header_imag = (ImageView) header.findViewById(R.id.nav_header_image);
        nav_header_nam.setText(nav_header_name);
         nav_header_emal.setText(nav_header_email);

        if (nav_header_gender.equals("Male")) {
            nav_header_imag.setImageResource(R.drawable.man);
        } else {
            nav_header_imag.setImageResource(R.drawable.female);
        }

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
                    Intent intent = new Intent(jitu.this,grammar.class);

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
                    Intent intent = new Intent(jitu.this,vocabulary.class);

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
                    Intent intent = new Intent(jitu.this,Comerror.class);

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
                    Intent intent = new Intent(jitu.this,practices.class);
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
                    Intent intent = new Intent(jitu.this,quiz.class);
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
                    Intent intent = new Intent(jitu.this,engsets.class);
                    startActivity(intent);
                }
            }
        });



        // for update check
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);
        appUpdateChecker.checkForUpdate(false);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

            if (Utils.isNetworkConnected(jitu.this))
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


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.about_us) {
            Intent intent3=new Intent(jitu.this,practreader.class);
            startActivity(intent3);


        } else if (id == R.id.rating_us) {


            if (Utils.isNetworkConnected(jitu.this))
            {
                try{
                    Uri uri1=Uri.parse("https://play.google.com/store/apps/details?id=com.ssc.engquiz");
                    Intent goToMarkek1=new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarkek1);
                }
                catch (ActivityNotFoundException e)
                {
                    Uri uri1=Uri.parse("https://play.google.com/store/apps/details?id=com.ssc.engquiz"+getPackageName());
                    Intent goToMarkek1=new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarkek1);
                }
            }
            else {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                Utils.networkNotAvailableAlertBox(this);
            }

        } else if (id == R.id.other_app) {
            if (Utils.isNetworkConnected(jitu.this))
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
        } else if (id == R.id.nav_share) {

            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody="https://play.google.com/store/apps/details?id=com.ssc.engquiz";
            String shareSub="English Quiz App";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(myIntent,"Share using"));


        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to={"infojitendra6@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback about english quiz app");
            intent.putExtra(Intent.EXTRA_TEXT,"");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose your Gmail :"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}