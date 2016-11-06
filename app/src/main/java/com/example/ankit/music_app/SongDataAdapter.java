package com.example.ankit.music_app;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ankit on 06-11-2016.
 */

public class SongDataAdapter extends BaseAdapter {
   private ArrayList<SongData> song;
    private LayoutInflater inflater;
    Context context;
    private MediaPlayer mediaPlayer=null;
    public SongDataAdapter(Context c , ArrayList<SongData> s) {
        context = c;
        song = s;
        inflater = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return song.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.layout,null);
        TextView textView = (TextView) v.findViewById(R.id.textview);
        SongData currsong = song.get(position);
        textView.setText(currsong.getTitle());
        return v;
    }
}
