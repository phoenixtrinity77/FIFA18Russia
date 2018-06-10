package com.trinity.phoenix.fifa_18russia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class PlayerDownload extends AppCompatActivity {

    private Bitmap bitmap;
    ImageView imageView;
    boolean imagedownload = false;
    String url,countryname;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_download);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        countryname = intent.getStringExtra("countryname");

        imageView = (ImageView)findViewById(R.id.pdownloadimageview);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.faba);

        AdView adView1 = (AdView)findViewById(R.id.fifa18);

        AdRequest adRequest8 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest8);


        if (countryname.equals("checkfixtures")){
            FirebaseDatabase fbCountryDatac = FirebaseDatabase.getInstance();
            DatabaseReference drCountryDatac = fbCountryDatac.getReference().child("fixtures").child("data");
            drCountryDatac.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String val = (String) dataSnapshot.getValue();
                    Glide.with(PlayerDownload.this).
                            load(val)
                            .asBitmap()
                            .centerCrop()
                            .listener(new RequestListener<String, Bitmap>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    bitmap = resource;
                                    imagedownload = true;
                                    return false;
                                }
                            })
                            .into(imageView);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Glide.with(PlayerDownload.this).
                    load(url)
                    .asBitmap()
                    .centerCrop()
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            bitmap = resource;
                            imagedownload = true;
                            return false;
                        }
                    })
                    .into(imageView);
        }



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagedownload){
                    SaveImage(bitmap);
                }else{
                    Toast.makeText(PlayerDownload.this, "Image is not Loaded please wait...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private Uri SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/"+countryname);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(PlayerDownload.this,"Downloaded",Toast.LENGTH_LONG).show();
            out.flush();
            out.close();
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
