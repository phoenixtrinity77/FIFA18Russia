package com.trinity.phoenix.fifa_18russia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerlistActivity extends AppCompatActivity {

    GridView listView_playerlist;
    List<PlayerList> playerListList = new ArrayList<PlayerList>();
    PlayerListAdapter adapter;
    String url,countryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerlist);

        listView_playerlist = (GridView) findViewById(R.id.listView_Playerlist);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        countryname = intent.getStringExtra("countryname");

        AdView adView1 = (AdView)findViewById(R.id.fifa17);

        AdRequest adRequest8 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest8);

        FirebaseDatabase fbCountryData = FirebaseDatabase.getInstance();
        DatabaseReference drCountryData = fbCountryData.getReference().child(countryname).child("playerList");

        listView_playerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayerlistActivity.this,PlayerDownload.class);
                intent.putExtra("countryname",countryname);
                intent.putExtra("url",playerListList.get(position).getUrl());
                startActivity(intent);
            }
        });

        drCountryData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchPlayerlist(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchPlayerlist(DataSnapshot dataSnapshot) {
        playerListList.clear();
        for (DataSnapshot ds1 : dataSnapshot.getChildren()){
            PlayerList cd = ds1.getValue(PlayerList.class);
            playerListList.add(cd);
        }
        adapter = new PlayerListAdapter(PlayerlistActivity.this,playerListList);
        listView_playerlist.setAdapter(adapter);
    }
}
