package com.trinity.phoenix.fifa_18russia;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlayerListAdapter extends ArrayAdapter<PlayerList> {
    private Activity context;
    private List<PlayerList> playerListList;

    public PlayerListAdapter(Activity context, List<PlayerList> playerListList){
        super(context,R.layout.dataviewp,playerListList);
        this.context = context;
        this.playerListList = playerListList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.dataviewp,null,true);

        ImageView image = (ImageView) listViewItem.findViewById(R.id.dataImageView);

        PlayerList dataInfo = playerListList.get(position);

        Glide.with(context).load(dataInfo.getUrl()).placeholder(R.mipmap.ic_launcher).thumbnail(0.1f).centerCrop().into(image);

        return listViewItem;
    }
}
