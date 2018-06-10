package com.trinity.phoenix.fifa_18russia;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class CountryDataAdapter extends ArrayAdapter<CountryData> {
    private Activity context;
    private List<CountryData> countryDataList;

    public CountryDataAdapter(Activity context,List<CountryData> countryDataList){
        super(context,R.layout.dataview,countryDataList);
        this.context = context;
        this.countryDataList = countryDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.dataview,null,true);

        ImageView image = (ImageView) listViewItem.findViewById(R.id.dataImageView);
        TextView name = (TextView) listViewItem.findViewById(R.id.dataTextView);

        CountryData dataInfo = countryDataList.get(position);

        Glide.with(context).load(dataInfo.getUrl()).placeholder(R.mipmap.ic_launcher).thumbnail(0.1f).centerCrop().into(image);
        name.setText(dataInfo.getCountryname());

        return listViewItem;
    }
}
