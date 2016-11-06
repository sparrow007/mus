package com.example.ankit.music_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private int TIMER=0;
    private Button b1;
    private boolean check=false;
    MediaPlayer mediaPlayer;
    int currentpos;
    SeekBar seekBar;
    int hour ;
    int min;
    int sec;
    int totalTime;
    TextView time1,time2;
    String setText;
    ListView listView;
    ArrayList<SongData> son;
    SongData curr;
    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String calu="";
            String second = "";
            totalTime = mediaPlayer.getCurrentPosition();
            hour =  (totalTime / (1000 * 60 * 60));
            min = (totalTime % (1000 * 60 * 60)) / (1000 * 60);
            sec = ((totalTime % (1000 * 60 * 60)) % (1000 * 60) / 1000);
            if(hour > 0) {
                calu = hour +":";
            }
            if(sec<10) {
                second = "0"+sec;
            }
            else {
                second += sec;
            }
            calu = calu + min + ":" + second;
            time1.setText(calu);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initliazation();
     Bundle b = getIntent().getExtras();
        son = (ArrayList<SongData>) b.getSerializable("key");
        int pos = b.getInt("pos",0);
         curr = son.get(pos);
       // ArrayList<SongData> songDatas = (ArrayList<SongData>) getIntent().getSerializableExtra("SongList");

Log.d("My Tag", ""+curr.getTitle());
        if(!mediaPlayer.isPlaying()) {
            Uri uri = Uri.fromFile(new File(curr.getPath()));
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            setText = cal(mediaPlayer.getDuration());
            time2.setText(setText);
            seek();
            b1.setBackgroundResource(R.drawable.ic_media_pause);
            check = true;
        }
        /*String arr[] = {"Airtel_song", "Jabra_fan","Har Kisi ko nahi milta","mein Agar samnie","Ankhon ke paane",
                "Aaj raat ka scene","Dhik chika","Lolipop lagu lu","wo jaha rha",
        "bhiar", "loliii","lakjfklajlkfjakl","afikrlfkajfkafaijasjfi;oas"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.text_layout,son);*/
        SongDataAdapter songDataAdapter = new SongDataAdapter(this, son);
        listView.setAdapter(songDataAdapter);
        listView.setOnItemClickListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!check) {
                   if(mediaPlayer.isPlaying()) {
                       mediaPlayer.seekTo(currentpos);
                   }

                   mediaPlayer.start();


                   check=true;
                   b1.setBackgroundResource(R.drawable.ic_media_pause);
                   mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                       public void onCompletion(MediaPlayer mp){
                           b1.setBackgroundResource(R.drawable.ic_media_play);
                           check=false;
                       }
                   });
               }
                else {
                   if(mediaPlayer.isPlaying()) {
                       currentpos = mediaPlayer.getCurrentPosition();
                       mediaPlayer.pause();

                   }
                   else {
                       mediaPlayer.stop();
                   }
                   check = false;
                       b1.setBackgroundResource(R.drawable.ic_media_play);
               }
            }
        });

    }

    private void initliazation() {
        b1 = (Button) findViewById(R.id.play_button);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.airtel_song);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        listView = (ListView) findViewById(R.id.listview);
    }

    public void seek() {
        Thread thread;
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        thread = new Thread() {
          @Override
            public void run() {

              while (mediaPlayer.isPlaying()) {
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  seekBar.setProgress(mediaPlayer.getCurrentPosition());
                  handler.sendEmptyMessage(TIMER);
              }
          }
        };
        thread.start();
    }
   String cal(int i) {
      String calu="";
       String second = "";
       totalTime = i;
        hour =  (totalTime / (1000 * 60 * 60));
        min = (totalTime % (1000 * 60 * 60)) / (1000 * 60);
        sec =  ((totalTime % (1000 * 60 * 60)) % (1000 * 60) / 1000);
       if(hour > 0) {
         calu = hour +":";
       }
       if(sec<10) {
           second = "0"+sec;
       }
       else {
           second += sec;
       }
       calu = calu + min + ":" + second;
       return calu;

   }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


              if(mediaPlayer.isPlaying()) {
                  mediaPlayer.stop();
                  b1.setBackgroundResource(R.drawable.ic_media_play);
              }
                     curr = son.get(position);
                    Uri uri = Uri.fromFile(new File(curr.getPath()));
                    mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
                    mediaPlayer.start();
                    seek();
                    b1.setBackgroundResource(R.drawable.ic_media_pause);
                    setText = cal(mediaPlayer.getDuration());
                    time2.setText(setText);
                    check = true;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp){
                b1.setBackgroundResource(R.drawable.ic_media_play);
                check=false;

            }
        });

    }
}
