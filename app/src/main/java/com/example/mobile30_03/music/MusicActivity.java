package com.example.mobile30_03.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.Music;

import java.io.Serializable;
import java.net.URI;

public class MusicActivity extends AppCompatActivity {
    ImageButton btn_shuf;
    ImageButton btn_prev;
    ImageButton btn_play;
    ImageButton btn_next;
    ImageButton btn_loop;
    SeekBar sb_media;
    MediaPlayer player;
    TextView tv_duration_current;
    TextView tv_duration_total;
    TextView tv_artist_name;
    TextView tv_song_name;

    private Handler handler =new Handler();

    boolean isShuffle = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent incomingIntent = getIntent();
        String sfafas = incomingIntent.getStringExtra("uri");
        Log.i("aaa",sfafas);

        Music currentSong = new Music(
                Uri.parse(incomingIntent.getStringExtra("uri")),
                incomingIntent.getStringExtra("sng"),
                incomingIntent.getStringExtra("art"),
                incomingIntent.getStringExtra("dsp"),
//                incomingIntent.getStringExtra("alb"),
                incomingIntent.getIntExtra("dur",0)

        );

//        player = MediaPlayer.create(this, R.raw.sound);
        player = MediaPlayer.create(this, currentSong.getUri());
        btn_shuf = findViewById(R.id.btn_shuf);
        btn_prev = findViewById(R.id.btn_prev);
        btn_play = findViewById(R.id.btn_play);
        btn_next = findViewById(R.id.btn_next);
        btn_loop = findViewById(R.id.btn_loop);
        sb_media = findViewById(R.id.sb_media);
        tv_duration_current = findViewById(R.id.tv_duration_current);
        tv_duration_total = findViewById(R.id.tv_duration_total);
        tv_artist_name = findViewById(R.id.tv_artist_name);
        tv_song_name = findViewById(R.id.tv_song_name);

        tv_duration_total.setText(milliSecondsToTimer(player.getDuration()));
        tv_artist_name.setText(currentSong.getArtist_name());
        tv_song_name.setText(currentSong.getSong_name());

        play();

        sb_media.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekTo;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekTo = i;
                tv_duration_current.setText(milliSecondsToTimer(seekTo * player.getDuration() / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekTo * player.getDuration() / 100 );
                tv_duration_current.setText(milliSecondsToTimer(seekTo * player.getDuration() / 100));
            }
        });

        btn_play.setOnClickListener(view -> {
            play();
        });

        btn_loop.setOnClickListener(view -> {
            if (player.isLooping()) {
                player.setLooping(false);
                Log.i("Media Player", "not looping");
                System.out.println(player.isLooping());
                btn_loop.clearColorFilter();
            } else {
                player.setLooping(true);
                Log.i("Media Player", "now looping");
                System.out.println(player.isLooping());
                btn_loop.setColorFilter(R.color.blue);
            }
        });

        btn_shuf.setOnClickListener(view -> {
            if (isShuffle) {
                isShuffle = false;
                Log.i("Media Player", "not shuffling");
                System.out.println(isShuffle);
                btn_shuf.clearColorFilter();
            } else {
                isShuffle = true;
                Log.i("Media Player", "now shuffling");
                System.out.println(isShuffle);
                btn_shuf.setColorFilter(R.color.blue);
            }
        });

        btn_prev.setOnClickListener(view -> {
            if (player.getCurrentPosition() / 1000 > 10){
                Log.i("btn_prev",String.valueOf(player.getCurrentPosition()));
                player.seekTo(0);
            }else{
                //TODO PREVIOUS MUSIC
            }
        });
    }

    private void play(){
        if (player.isPlaying()){
            //Remove handler callbacks when music stops
            handler.removeCallbacks(myUpdater);
            player.pause();
            //player.prepareAsync();
            btn_play.setImageResource(R.drawable.ic_baseline_play_circle_filled);
        }else{
            player.start();
            btn_play.setImageResource(R.drawable.ic_baseline_pause_circle_filled);
            updateSeekBar();
        }
    }

    //SEEKBAR Updater with Runnable & Handler
    private void updateSeekBar(){
        if(player.isPlaying()){
            sb_media.setProgress((int)(((float) player.getCurrentPosition()/player.getDuration())*100));
            handler.postDelayed(myUpdater,1000);
        }
    }
    private class Updater implements Runnable{
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration=player.getCurrentPosition();
            tv_duration_current.setText(milliSecondsToTimer(currentDuration));
        }
    }
    private Runnable myUpdater = new Updater();

    private String milliSecondsToTimer(long milliSeconds){
        String timerString="";
        String secondString;
        int hour=(int) (milliSeconds/(1000*60*60));
        int minutes=(int)(milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int)((milliSeconds%(1000*60*60))%(1000*60)/1000);
        if(hour>0){
            timerString=hour+":";
        }
        if(seconds<10){
            secondString="0"+seconds;
        }
        else {
            secondString=""+seconds;
        }
        timerString=timerString+minutes+":"+secondString;
        return timerString;
    }
}

/*TODO 1:
    3 defa yanlis loginde disable -done
    ad soyad-bday-phone-mail      -?
    signup sonrasi mail gonder    -done
    seekbar

  TODO 2:
    music listesi
    liste ekrani
    cardview or gridview
    telefona dosylar yukle
    listenin duzenlenebilmesi

  TODO:
    SPOTIFY
    UI UX
    BASIT
*/