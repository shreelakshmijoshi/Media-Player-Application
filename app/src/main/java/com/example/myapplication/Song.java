package com.example.myapplication;


/**
 *  Song object contains information related to a single song.
 */
public class Song {
    private int mp3;
    private String songName;
    private int image;
    private int songLyrics;

    /**
     * Constructs a new Song object.
     *
     * @param songName is the name of the song
     * @param mp3 is the song audio which is stored as integer
     * @param image is the image associated with the song
     */
    Song(String songName,int  mp3,int image, int songLyrics)
    {
        this.image = image;
        this.mp3 = mp3;
        this.songName = songName;
        this.songLyrics = songLyrics;
    }

    public int getImage(String songName) {
        return image;
    }

    public int getMp3(String songName) {
        return mp3;
    }

    public int getSongLyrics(String songName)
    {
        return songLyrics;
    }

}
