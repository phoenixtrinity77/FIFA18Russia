package com.trinity.phoenix.fifa_18russia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView listView_Country;
    List<CountryData> countryDataList = new ArrayList<CountryData>();
    CountryDataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView_Country = (GridView) findViewById(R.id.listView_CountryData);

        FirebaseDatabase fbCountryData = FirebaseDatabase.getInstance();
        DatabaseReference drCountryData = fbCountryData.getReference();

        listView_Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DownloadActivity.class);
                intent.putExtra("countryname",countryDataList.get(position).getCountryname());
                intent.putExtra("url",countryDataList.get(position).getUrl());
                startActivity(intent);
            }
        });

        AdView adView1 = (AdView)findViewById(R.id.fifa9);
        AdView adView2 = (AdView)findViewById(R.id.fifa10);
        AdView adView3 = (AdView)findViewById(R.id.fifa11);
        AdView adView4 = (AdView)findViewById(R.id.fifa12);
        AdView adView5 = (AdView)findViewById(R.id.fifa13);
        AdView adView6 = (AdView)findViewById(R.id.fifa14);
        AdView adView7 = (AdView)findViewById(R.id.fifa15);
        AdView adView8 = (AdView)findViewById(R.id.fifa16);

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

        drCountryData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchCountryData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        permission();

    }

    private void permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    permission();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void fetchCountryData(DataSnapshot dataSnapshot) {
        countryDataList.clear();
        for (DataSnapshot ds1 : dataSnapshot.getChildren()){
            for (DataSnapshot ds2 : ds1.getChildren()){
                String check = null;
                check = ds2.getKey();
                if (check.equals("metaData")){
                    CountryData cd = ds2.getValue(CountryData.class);
                    countryDataList.add(cd);
                }

            }
        }
        adapter = new CountryDataAdapter(MainActivity.this,countryDataList);
        listView_Country.setAdapter(adapter);
    }
}
