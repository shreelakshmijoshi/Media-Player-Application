package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {
//    int[] mp3 = new int[]{,
//            R.raw.counting_stars_by_onerepublic,
//            R.raw.fireflies_by_owlcity,
//            R.raw.happier_by_marshmello,
//            R.raw.silence_by_khalid_and_marshmello,
//            R.raw.hey_brother_by_avicii};

//    Map<String, Integer> songAndItsName = new HashMap<>();

    //    Map<String, Map<Integer,Integer>> songAndItsName = new HashMap<>();
    ScrollView scrollView;
    ImageView home_icon;
    ImageView play_button;
    String songKeyName;
    String nextSongKeyName;
    String previousSongKeyName;
    MediaPlayer mediaPlayer;
    boolean isMute = false;


    private static final Map<String, Song> songHashMap = new HashMap<>();

    static {
        songHashMap.put("Happy by Pharrell Williams", new Song("Happy by Pharrell Williams",
                R.raw.happy_by_pharrell_william, R.drawable.happy_image, R.string.happy_by_pharrell_williams_lyrics));
        songHashMap.put("Counting Stars by OneRepublic", new Song("Counting Stars by OneRepublic",
                R.raw.counting_stars_by_onerepublic, R.drawable.counting_stars_image, R.string.counting_stars_by_onerepublic_lyrics));
        songHashMap.put("Happier by Marshmello", new Song("Happier by Marshmello",
                R.raw.happier_by_marshmello, R.drawable.happier_by_marshmello_image, R.string.happier_by_marshmello_lyrics));
        songHashMap.put("Silence by Marshmello,Khalid", new Song("Silence by Marshmello,Khalid",
                R.raw.silence_by_khalid_and_marshmello, R.drawable.silence_by_khalid, R.string.silence_by_khalid_lyrics));
        songHashMap.put("Hey Brother ! by Avicii", new Song("Hey Brother ! by Avicii",
                R.raw.hey_brother_by_avicii, R.drawable.hey_brother_by_avicii, R.string.hey_brother_avicii_lyrics));
        songHashMap.put("Fireflies by Owl City", new Song("Fireflies by Owl City",
                R.raw.fireflies_by_owlcity, R.drawable.fireflies, R.string.fireflies_lyrics));


    }

    ;

//    ImageView songImage;
    TextView lyrics;
    ImageView skip_previous;
    ImageView skip_next;
    Intent intent;
    int position;
    TextView textView;
    ArrayList<String> songs = new ArrayList<>();
    SeekBar seekBarMusic;
    Thread updateSeekbar;
    TextView time_from_start;
    TextView time_from_stop;
    ImageView volume;
    AudioManager audioManager; //for volume
    int maxVolume;
    ObjectAnimator animation;
    int currentVolume;
    SeekBar volumeSeekBar;

    ImageView repeatIcon;
    boolean isRepeated = false;

//    ImageView lyricsIcon;
    String currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        home_icon = (ImageView) findViewById(R.id.home_icon);
        home_icon.setOnClickListener(this);

        releaseMediaPlayer();
        intent = getIntent();

        //getting song name frm the intent and setting that to the textView to make a banner

        songKeyName = intent.getStringExtra("songKeyName");
        position = intent.getIntExtra("keyPosition", 0);
        textView = (TextView) findViewById(R.id.textView);
        Log.v("MusicPlayer", "Song name : " + songKeyName);
        textView.setText(songKeyName);

//
//        here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

//        songs = intent.getStringArrayListExtra("songs");
setCurrentSong(songKeyName);
        songs = new MainActivity().setSongs();

        ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "translationX", 1000f);
        animation.setRepeatCount(50);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setDuration(9000);
        animation.start();


        scrollView = (ScrollView) findViewById(R.id.scrollView2);

        // get id of the image fr the song

//        songImage = (ImageView) findViewById(R.id.song_image);
//        songImage.setImageResource(songHashMap.get(songKeyName).getImage(songKeyName));

            lyrics = (TextView) findViewById(R.id.lyrics);
            lyrics.setText(songHashMap.get(songKeyName).getSongLyrics(songKeyName));

//        scrollView.setBackgroundResource(songHashMap.get(songKeyName).getImage(songKeyName));

        //lyrics animation
       lyricsAnimation();

        //add song name as key and song (mp3) ->int as its value  wrapped in Integer

//        songAndItsName.put("Happy by Pharrell Williams",R.raw.happy_by_pharrell_william);
//        songAndItsName.put("Counting Stars by OneRepublic", R.raw.counting_stars_by_onerepublic);
//        songAndItsName.put("Happier by Marshmello", R.raw.happier_by_marshmello);
//        songAndItsName.put("Silence by Marshmello,Khalid",R.raw.silence_by_khalid_and_marshmello);
//        songAndItsName.put("Hey Brother ! by Avicii",R.raw.hey_brother_by_avicii);
//        songAndItsName.put("Fireflies by Owl City",  R.raw.fireflies_by_owlcity);

        // returns null value so , stopped using map of map
//
//        songAndItsName.put("Happy by Pharrell Williams",
//                new HashMap<>(R.raw.happy_by_pharrell_william,R.drawable.happy_image));
//        songAndItsName.put("Counting Stars by OneRepublic",
//                new HashMap<>(R.raw.counting_stars_by_onerepublic,R.drawable.counting_stars_image));
//        songAndItsName.put("Happier by Marshmello",
//                new HashMap<>(R.raw.happier_by_marshmello,R.drawable.happier_by_marshmello_image));
//        songAndItsName.put("Silence by Marshmello,Khalid",
//                new HashMap<>(R.raw.silence_by_khalid_and_marshmello,R.drawable.silence_by_khalid));
//        songAndItsName.put("Hey Brother ! by Avicii",
//                new HashMap<>(R.raw.hey_brother_by_avicii,R.drawable.hey_brother_by_avicii));
//        songAndItsName.put("Fireflies by Owl City",
//                new HashMap<>(R.raw.fireflies_by_owlcity,R.drawable.fireflies));


//        Log.v("MusicPlayer","song details : " + song1.getMp3("Silence by Marshmello,Khalid"));
        Log.v("MusicPlayer", "song details : " + songHashMap.get("Happier by Marshmello").getMp3("Happier by Marshmello"));
        mediaPlayer = MediaPlayer.create(MusicPlayer.this, songHashMap.get(songKeyName).getMp3(songKeyName));

        //get the id of play button
        play_button = (ImageView) findViewById(R.id.play_button);
        play_button.setOnClickListener(this);


        // Previous and next button
        skip_previous = (ImageView) findViewById(R.id.skip_previous);
        skip_next = (ImageView) findViewById(R.id.skip_next);

        skip_previous.setOnClickListener(this);
        skip_next.setOnClickListener(this);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                skip_next.performClick();
            }
        });

        //resource id for music seek bar
        seekBarMusic = (SeekBar) findViewById(R.id.music_seek_bar);

        //get resource id for time --> the 2 textView below the seekbar
        time_from_start = (TextView) findViewById(R.id.time_from_start);
        time_from_stop = (TextView) findViewById(R.id.time_from_stop);
//        time_from_stop.setText(convertToMinutesAndSeconds ((mediaPlayer.getDuration())));
        //time we get from mediaPlayer is in milli seconds, I have to convert
        // the milli seconds
        String endTime = convertToMinutesAndSeconds(mediaPlayer.getDuration());
        time_from_stop.setText(endTime);
        Log.v("MusicPlayer", "time_from_stop " + time_from_stop + " || endTime " + endTime);
        //Thread for seekbar
        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                //for every 500 seconds , update the seekbar
                while (currentPosition < totalDuration) {
                    try {
                        //for every 500 milli second, update the current position of the seekbar
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBarMusic.setProgress(currentPosition);

                    } catch (Exception e) {
                        Log.e("MusicPlayer", "Error in seekbar " + e);
                    }
                }
            }
        };
        // semi colon after this :)
        seekBarMusic.setMax(mediaPlayer.getDuration());
        //start the thread of update seek bar
        updateSeekbar.start();
        //change the seekbar position when the user changes it
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarMusic.getProgress());
            }
        });
        //time we get from mediaPlayer is in milli seconds, I have to convert
        // the milli seconds
//    String endTime = convertToMinutesAndSeconds(mediaPlayer.getDuration());
//    time_from_stop.setText(endTime);

// current time is updated every second unlike the endTime for a song
        // we need to change current time frequently...
        // so creating a handler for that
        //one of the complex method that I had to implement is this one !
        final Handler handler = new Handler();
        final int delay = 1000;
        //i.e., update the time every second (1000 milli second)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = convertToMinutesAndSeconds(mediaPlayer.getCurrentPosition());
                time_from_start.setText(currentTime);
                handler.postDelayed(this, delay);

            }
        }, delay);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSeekBar = (SeekBar) findViewById(R.id.volume_seek_bar);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // if I click the volume button it should turn off or on
        // when on resume to the recent value of the volume
        volume = (ImageView) findViewById(R.id.volume);
        volume.setOnClickListener(this);

//here


        //get res ID of the repeat icon to repeat the song
        repeatIcon = (ImageView) findViewById(R.id.repeat_icon);
        repeatIcon.setOnClickListener(this);

        //Lyrics
//        lyricsIcon = (ImageView) findViewById(R.id.lyrics_icon);
//        lyricsIcon.setOnClickListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
//            releaseMediaPlayer();  ---> maybe you should uncomment it later after null value exception is solved
        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (v.equals(home_icon)) {
            intent = new Intent(MusicPlayer.this, MainActivity.class);
            startActivity(intent);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

//            releaseMediaPlayer();  ---> maybe I should uncomment it later after null value exception is solved
        }
        if (v.equals(play_button)) {

            rotate(play_button);

            //           -->>>>>
//            mediaPlayer = MediaPlayer.create(MusicPlayer.this, songAndItsName.get(songKeyName).get(0));
//            mediaPlayer.start(); // no need to call prepare(); create() does that for you
//            play_button.setImageResource(R.drawable.ic_baseline_pause_24);
//            paused = true;
            if (mediaPlayer.isPlaying()) {
//                play_button.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                play_button.setImageResource(R.drawable.ic_baseline_pause_24);
                mediaPlayer.pause();
                play_button.setImageResource(R.drawable.ic_baseline_play_arrow_24);

                //pause the lyrics
//                animation.end();

            } else {

                mediaPlayer.start(); // no need to call prepare(); create() does that for you
                play_button.setImageResource(R.drawable.ic_baseline_pause_24);

                //start the lyrics
//                animation.resume();
            }


        }
//        if(v.equals(play_button) && paused==true)
//        {
//            mediaPlayer.pause();
//            play_button.setImageResource(R.drawable.ic_baseline_play_arrow_24);
//            paused = false;
//        }

        if (v.equals(skip_next)) {

//            if(isRepeated)
//            {
//                Log.v("MediaPlayer","repetition" + "Iam here");
//                mediaPlayer.setLooping(false);
//                isRepeated = false;
//                repeatIcon.setColorFilter(Color.GRAY);
//            }

            //if i repeat click on repeat a song and play the next song,
            //the next song which I play should not run repeatedly..
            //the user should press the repeat the button again
            //for that I'll call the method which makes as if I press the
            // repeat button (image really)
            //same happens, when I try playing previous song
            if(isRepeated)
            {
                repeatIcon.performClick();
            }

            play_button.setImageResource(R.drawable.ic_baseline_play_arrow_24);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }


//
//            intent = getIntent();
//           //getting song name frm the intent and setting that to the textView to make a banner
//            nextSongKeyName = intent.getStringExtra("nextSongKeyName");
//            Intent newIntent = new Intent(MusicPlayer.this, MusicPlayer.class);
//           newIntent.putExtra("songKeyName" , nextSongKeyName);
//            startActivity(newIntent);
//            intent = new Intent(MusicPlayer.this, MusicPlayer.class);
//            intent.putExtra("songKeyName" , nextSongKeyName);
//            startActivity(intent);
//            intent = getIntent();
            position = ((position + 1) % (songs.size() - 1));
            nextSongKeyName = songs.get(position);
            //set the banner name i.e., name of the song to next song
            textView.setText(nextSongKeyName);
            //set the background image for the song
            //REMOVEEE THE SONG IMAGE : 2
//            songImage.setImageResource(songHashMap.get(nextSongKeyName).getImage(nextSongKeyName));

            setCurrentSong(nextSongKeyName);
            lyrics.setText(songHashMap.get(nextSongKeyName).getSongLyrics(nextSongKeyName));

//            scrollView.setBackgroundResource(songHashMap.get(nextSongKeyName).getImage(nextSongKeyName));

            //lyrics animation
            lyricsAnimation();


            //create a media player with the next song
            mediaPlayer = MediaPlayer.create(MusicPlayer.this, songHashMap.get(nextSongKeyName).getMp3(nextSongKeyName));

//            startActivity(intent);

        }

        if (v.equals(skip_previous)) {
//repeatIcon clicking without actually clicking it
            if(isRepeated)
            {
                Log.v("MediaPlayer","repetition" + "Iam here");
                repeatIcon.performClick();
            }
            play_button.setImageResource(R.drawable.ic_baseline_play_arrow_24);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }


//             intent = getIntent();
//            //getting song name frm the intent and setting that to the textView to make a banner
//            previousSongKeyName = intent.getStringExtra("previousSongKeyName");
////            Intent newIntent = new Intent(MusicPlayer.this, MusicPlayer.class);
////            newIntent.putExtra("songKeyName" , previousSongKeyName);
////            startActivity(newIntent);
//             intent = new Intent(MusicPlayer.this, MusicPlayer.class);
//            intent.putExtra("songKeyName" , previousSongKeyName);
//            startActivity(intent);

//            intent = getIntent();

            if ((position - 1) % (songs.size() - 1) < 0) {
                position = songs.size() - 1;
            }
            position = ((position - 1) % (songs.size() - 1));
            previousSongKeyName = songs.get(position);
            //set the banner name i.e., name of the song to next song
            textView.setText(previousSongKeyName);
            //set the background image of the previous song
            //REMOVEEE THE SONG IMAGE : PART 3
//            songImage.setImageResource(songHashMap.get(previousSongKeyName).getImage(previousSongKeyName));
//
//scrollView.setBackgroundResource(songHashMap.get(previousSongKeyName).getImage(previousSongKeyName));


            setCurrentSong(previousSongKeyName);
            lyrics.setText(songHashMap.get(previousSongKeyName).getSongLyrics(previousSongKeyName));




            //lyrics animation
            lyricsAnimation();


            //create a media player with the next song
            mediaPlayer = MediaPlayer.create(MusicPlayer.this, songHashMap.get(previousSongKeyName).getMp3(previousSongKeyName));



//            startActivity(intent);

        }
        if (v.equals(volume)) {
            if (isMute == false) {
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                Log.v("MusicPlayer", "volume button :( " + "it should be mute");
                volumeSeekBar.setProgress(0);
                isMute = true;
            } else {
                volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                volumeSeekBar.setProgress(currentVolume);
                Log.v("MusicPlayer", "volume button :( " + "NOT mute");
                isMute = false;
            }
        }


        if (v.equals(repeatIcon)) {
            rotate(repeatIcon);
            if (isRepeated == false) {
                //repeat the same song even if it is finished
                //set isRepeated = true
                mediaPlayer.setLooping(true);
                isRepeated = true;
                repeatIcon.setColorFilter(Color.parseColor("#1A5D93"));


            } else {
                //stop the song repatation and resume the normal execution
                isRepeated = false;
                mediaPlayer.setLooping(false);
                repeatIcon.setColorFilter(Color.GRAY);
            }
        }


//        if(v.equals(lyricsIcon))
//        {
//            Log.v("MusicPlayer","lyrics_icon" + "I did not click this");
//            intent = new Intent(MusicPlayer.this, LyricsActivity.class);
//            intent.putExtra("KeyForLyricsActivity",getCurrentSong());
//            startActivity(intent);
//
//
//        }


    }

    /**
     * get and set the songs. Called when I press the play_next , play previous button
     */
    public String getCurrentSong()
    {
        return currentSong;
    }

    public void setCurrentSong(String happy)
    {
        currentSong = happy;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }

    /**
     * Converts time in milli seconds to minutes and seconds
     */
    public String convertToMinutesAndSeconds(int duration) {
        String time = "";
        int minute = duration / 1000 / 60;
        int seconds = duration / 1000 % 60;
        time = minute + ":" + time;
        if (seconds < 10) {
            time = time + "0";
        }
        time = time + seconds;
        Log.v("MusicPlayer", "time -> " + time);
        return time;

    }

    /**
     * rotate play and pause button 360 degree to itself when it is pressed
     * @param imageView which might be play , pause , repeat one button
     */

    /**
     * I HAVE TO KNOW MORE ABOUT THISSSS
     *
     */
    public void rotate(ImageView imageView)
    {
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());

//        ImageView image= (ImageView) findViewById(R.id.imageView);
//   image.startAnimation(rotate);
        imageView.startAnimation(rotate);

    }


    /**
     * Lyrics Animation ! not in sync with the song though, it just goes slowly
     */
    private void lyricsAnimation() {

        animation = ObjectAnimator.ofFloat(lyrics, "translationY", -5000f);
        animation.setRepeatCount(50);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setDuration(50000);
        animation.start();


    }

}