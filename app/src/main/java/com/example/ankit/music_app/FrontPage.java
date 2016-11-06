package com.example.ankit.music_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        Thread t = new Thread() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(FrontPage.this,MusicList.class);
                startActivity(intent);
            }
        };
        t.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
