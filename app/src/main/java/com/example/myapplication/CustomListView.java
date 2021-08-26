package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<Integer> image;
    private ArrayList<String> song;

    public CustomListView(Activity context, ArrayList<Integer> image,ArrayList<String> song ) {
        super(context, R.layout.list_item, song);
        this.context = context;
        this.image = image;
        this.song = song;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       //Calling layout inflator only while creating a new row will optimize the overall performance of ListView

        View row = convertView;
        ViewHolder viewHolder = null;
        if(row == null)
        {
            //Layout Inflator converts XML file to corresponding java object with the method getLayoutInflator
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(R.layout.list_item,null, true);
            viewHolder = new ViewHolder(row);
            //setTag is a View class method which stores View i.e., findViewById
            row.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) row.getTag();
        }
        viewHolder.imageView.setImageResource(image.get(position));
        viewHolder.textView.setText(song.get(position));

        return row;
    }


    class ViewHolder{
        TextView textView;
        ImageView imageView;

        ViewHolder(View view)
        {
            textView =  (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.song_image);
        }
    }
}
