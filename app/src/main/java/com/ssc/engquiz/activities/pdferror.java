package com.ssc.engquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ssc.engquiz.R;

import static com.ssc.engquiz.activities.Comerror.err;

public class pdferror extends AppCompatActivity {
    private View decodrView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdferror);
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
        int j=err;
        if(j==1)
        {
            webView.loadUrl("file:///android_asset/erro/verb.html");
        }
        else if (j==2)
        {
            webView.loadUrl("file:///android_asset/erro/tense.html");
        }
        else if (j==3)
        {
            webView.loadUrl("file:///android_asset/erro/voice.html");
        }

        else if (j==4)
        {
            webView.loadUrl("file:///android_asset/erro/narration.html");
        }
        else if (j==5)
        {
            webView.loadUrl("file:///android_asset/erro/adverb.html");
        }
        else if (j==6)
        {
            webView.loadUrl("file:///android_asset/erro/subverb.html");

        }
        else if (j==7)
        {
            webView.loadUrl("file:///android_asset/erro/cond.html");
        }
        else if (j==8)
        {
            webView.loadUrl("file:///android_asset/erro/noun.html");
        }
        else if (j==9)
        {
            webView.loadUrl("file:///android_asset/erro/pronoun.html");
        }
        else if (j==10)
        {
            webView.loadUrl("file:///android_asset/erro/adjective.html");
        }
        else if (j==11)
        {
            webView.loadUrl("file:///android_asset/erro/conjuction.html");
        }
        else if (j==12)
        {
            webView.loadUrl("file:///android_asset/erro/article.html");
        }
        else
        {
            webView.loadUrl("file:///android_asset/vocab/impword.html");
            // webView.loadUrl("file:///android_asset/pract/quiz.html");
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