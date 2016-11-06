package com.example.ankit.music_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class MusicList extends Activity implements AdapterView.OnItemClickListener{
private  final static int READ_EXTERNAL_STORAGE_RESULT=0;
    private ListView songList;
    ArrayList<SongData> songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        checkRuntimePermission();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            songList = (ListView) findViewById(R.id.list_item);
            songs = new ArrayList<SongData>();
            getAllSongs();
          SongDataAdapter adapter = new SongDataAdapter(this, songs);
            songList.setAdapter(adapter);
            songList.setOnItemClickListener(this);
        }
    }

    private void getAllSongs() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                do {
                   Long id = cursor.getLong(idColumn);
                    String title = cursor.getString(titleColumn);
                    String album = cursor.getString(albumColumn);
                    String artist = cursor.getString(artistColumn);
                    String path = cursor.getString(dataColumn);
                    songs.add(new SongData(id, title, album, artist, path));

                } while (cursor.moveToNext());
            }
        }
    }

    public void checkRuntimePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_RESULT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_RESULT:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else
                checkRuntimePermission();
            default:
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MusicList.this, MainActivity.class);
        Bundle bundle = new Bundle();
       bundle.putSerializable("key",songs);
        intent.putExtras(bundle);
        intent.putExtra("pos",position);
       startActivity(intent);
    }
}
