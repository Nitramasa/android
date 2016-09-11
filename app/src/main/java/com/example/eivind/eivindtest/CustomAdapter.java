package com.example.eivind.eivindtest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eivind on 27.08.2016.
 */
public class CustomAdapter extends BaseAdapter {
    String[] result;
    Context context;
    private ArrayList list, list2;

    int [] imageId;
    private static LayoutInflater inflater = null;
    public CustomAdapter(Activity a, ArrayList d, ArrayList d2){
        list = d;
        list2 = d2;
        context = a;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        if(list.size()<=0)
            return 1;
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }
    public class Holder {
        TextView tv;
        TextView tvInfo;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.label);
        holder.tvInfo = (TextView) rowView.findViewById(R.id.info);
        holder.tv.setText(list2.get(position).toString());
        holder.tvInfo.setText(list.get(position).toString());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+list.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }
}
