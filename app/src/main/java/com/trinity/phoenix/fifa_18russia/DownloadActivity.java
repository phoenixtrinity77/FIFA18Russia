package com.trinity.phoenix.fifa_18russia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class DownloadActivity extends AppCompatActivity {

    private Bitmap bitmap;
    ImageView imageView;
    FabSpeedDial fabSpeedDial;
    boolean imagedownload = false;
    String url,countryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_download);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        countryname = intent.getStringExtra("countryname");

        AdView adView1 = (AdView)findViewById(R.id.avengers18);

        AdRequest adRequest8 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest8);

        imageView = (ImageView)findViewById(R.id.downloadimageview);
        fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabSpeedDial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.playerlist:
                        Intent intent = new Intent(DownloadActivity.this,PlayerlistActivity.class);
                        intent.putExtra("countryname",countryname);
                        intent.putExtra("url",url);
                        startActivity(intent);
                        break;
                    case R.id.fixturesdata:
                        Intent intent1 = new Intent(DownloadActivity.this,PlayerDownload.class);
                        intent1.putExtra("countryname","checkfixtures");
                        intent1.putExtra("url",url);
                        startActivity(intent1);
                        break;
                    case R.id.downloadpic:
                        if (imagedownload){
                            SaveImage(bitmap);
                        }else{
                            Toast.makeText(DownloadActivity.this, "Image is not Loaded please wait...", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return false;
            }
        });


        Glide.with(DownloadActivity.this).
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

    private Uri SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AVENGERS");
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
            Toast.makeText(DownloadActivity.this,"Downloaded",Toast.LENGTH_LONG).show();
            out.flush();
            out.close();
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
