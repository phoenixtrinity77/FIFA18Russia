package com.trinity.phoenix.fifa_18russia;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;

public class SplashScreen extends AppCompatActivity {

    TextView longday;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        longday = (TextView) findViewById(R.id.longday);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        longday.setTypeface(typeface);
        button = (Button) findViewById(R.id.nextbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
        });

        MobileAds.initialize(this, "ca-app-pub-3530215583120862~8506625349");

        AdView adView1 = (AdView)findViewById(R.id.fifa1);
        AdView adView2 = (AdView)findViewById(R.id.fifa2);
        AdView adView3 = (AdView)findViewById(R.id.fifa3);
        AdView adView4 = (AdView)findViewById(R.id.fifa4);
        AdView adView5 = (AdView)findViewById(R.id.fifa5);
        AdView adView6 = (AdView)findViewById(R.id.fifa6);
        AdView adView7 = (AdView)findViewById(R.id.fifa7);
        AdView adView8 = (AdView)findViewById(R.id.fifa8);

        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adView2.loadAd(adRequest2);
        AdRequest adRequest3 = new AdRequest.Builder().build();
        adView3.loadAd(adRequest3);
        AdRequest adRequest4 = new AdRequest.Builder().build();
        adView4.loadAd(adRequest4);
        AdRequest adRequest5 = new AdRequest.Builder().build();
        adView5.loadAd(adRequest5);
        AdRequest adRequest6 = new AdRequest.Builder().build();
        adView6.loadAd(adRequest6);
        AdRequest adRequest7 = new AdRequest.Builder().build();
        adView7.loadAd(adRequest7);
        AdRequest adRequest8 = new AdRequest.Builder().build();
        adView8.loadAd(adRequest8);

        starTtimer();
    }


    private void starTtimer() {
        long differance = getRemainDays();
        new CountDownTimer(differance,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

                int days = (int) (millisUntilFinished/(1000*60*60*24));
                int hours = (int) ((millisUntilFinished/(1000*60*60))%24);
                int mins = (int) ((millisUntilFinished/(1000*60))%60);
                int sec = (int) (millisUntilFinished/(1000)%60);
                longday.setText(String.format("%d DAYS %02d:%02d:%02d",days,hours,mins,sec));
            }

            @Override
            public void onFinish() {
                longday.setVisibility(View.GONE);
            }
        }.start();
    }

    public long getRemainDays() {
        Date currentDate = new Date();
        Date endDate = null;
        if (currentDate.getMonth()<=5){
            endDate = new Date(currentDate.getYear(),5,14);
        }
        return endDate.getTime() - currentDate.getTime();
    }
}
