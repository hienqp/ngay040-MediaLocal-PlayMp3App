package com.hienqp.playmp3app;

public class Song {
    private String songName;
    private int songId;

    public Song(String songName, int songId) {
        this.songName = songName;
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
