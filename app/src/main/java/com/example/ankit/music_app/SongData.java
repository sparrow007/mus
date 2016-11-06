package com.example.ankit.music_app;

import java.io.Serializable;

/**
 * Created by Ankit on 05-11-2016.
 */

public class SongData implements Serializable{
    long ID;
    String title;
    String album,artist;
    String path;
    public SongData(long ID, String title, String album, String artist, String path) {
        this.album = album;
        this.title = title;
        this.artist = artist;
        this.ID = ID;
        this.path = path;
    }
   public String getTitle() {return  title;}
    public String getAlbum() {return  album;}
    public String getArtist() {
        return artist;
    }
    public String getPath(){return path;}
    public long getID(){return ID;}
}
