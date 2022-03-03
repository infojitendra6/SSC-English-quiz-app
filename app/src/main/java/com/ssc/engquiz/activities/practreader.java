package com.ssc.engquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ssc.engquiz.R;

import static com.ssc.engquiz.activities.practices.prac;

public class practreader extends AppCompatActivity {
    private View decodrView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practreader);
        // for full screen
        decodrView=getWindow().getDecorView();
        decodrView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0)
                {
                    decodrView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });

        WebView webView=(WebView)findViewById(R.id.webView);
        int j=prac;
        if(j==1)
        {
            webView.loadUrl("file:///android_asset/pract/synonymsquiz.html");
        }
        else if (j==2)
        {
            webView.loadUrl("file:///android_asset/pract/antonymsquiz.html");
        }
        else if (j==3)
        {
            webView.loadUrl("file:///android_asset/pract/oneword.html");
        }

        else if (j==4)
        {
            webView.loadUrl("file:///android_asset/pract/phrasequiz.html");
        }
        else if (j==5)
        {
            webView.loadUrl("file:///android_asset/pract/commonerrorquiz.html");
        }
        else if (j==6)
        {
            webView.loadUrl("file:///android_asset/pract/synonymsquiz.html");

        }
        else
        {
            webView.loadUrl("file:///android_asset/about.html");

        }



        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
        {
            decodrView.setSystemUiVisibility(hideSystemBar());
        }
    }
    private int hideSystemBar()
    {
        return View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}