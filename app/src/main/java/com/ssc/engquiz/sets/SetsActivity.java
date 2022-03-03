package com.ssc.engquiz.sets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ssc.engquiz.AppUpdateChecker;
import com.ssc.engquiz.R;
import com.ssc.engquiz.Utils;
import com.ssc.engquiz.activities.Comerror;
import com.ssc.engquiz.activities.practices;
import com.ssc.engquiz.activities.vocabulary;

import java.util.ArrayList;
import java.util.List;

public class SetsActivity extends AppCompatActivity {
    private GridView sets_grid;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;
    private ProgressDialog progressBar;
    public static int category_id;
    Toolbar toolbar;
    public static List<String> setsIDs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removing status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sets);
         toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);


        String title =getIntent().getStringExtra("CATEGORY");
        category_id=getIntent().getIntExtra("CATEGORY_ID",1);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sets_grid = findViewById(R.id.sets_gridview);

        // for progress Dialog
        progressBar = new ProgressDialog(SetsActivity.this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();


        firestore=FirebaseFirestore.getInstance();
        loadSets();

    }

    private void loadSets() {
        firestore.collection("QUIZ").document("CAT"+String.valueOf(category_id))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists())
                    {
                        long sets = (long)doc.get("SETS");

                        SetsAdapter adapter=new SetsAdapter(Integer.valueOf((int)sets));
                        sets_grid.setAdapter(adapter);



                    }
                    else
                    {
                        Toast.makeText(SetsActivity.this,"No CAT Document Exists!",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                else
                {

                    Toast.makeText(SetsActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
                progressBar.cancel();
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

            if (Utils.isNetworkConnected(SetsActivity.this))
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
            SetsActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



}