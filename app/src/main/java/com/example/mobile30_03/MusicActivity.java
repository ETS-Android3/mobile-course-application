package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.AudioRouting;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

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

    private Handler handler =new Handler();

    boolean isShuffle = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        player = MediaPlayer.create(this, R.raw.sound);
        btn_shuf = findViewById(R.id.btn_shuf);
        btn_prev = findViewById(R.id.btn_prev);
        btn_play = findViewById(R.id.btn_play);
        btn_next = findViewById(R.id.btn_next);
        btn_loop = findViewById(R.id.btn_loop);
        sb_media = findViewById(R.id.sb_media);
        tv_duration_current = findViewById(R.id.tv_duration_current);
        tv_duration_total = findViewById(R.id.tv_duration_total);

        tv_duration_total.setText(milliSecondsToTimer(player.getDuration()));

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
            if (player.isPlaying()){
                handler.removeCallbacks(updater);
                player.pause();
                //player.prepareAsync();
                btn_play.setImageResource(R.drawable.ic_baseline_play_circle_filled);
            }else{
                player.start();
                btn_play.setImageResource(R.drawable.ic_baseline_pause_circle_filled);
                updateSeekBar();
            }
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

    private Runnable updater=new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration=player.getCurrentPosition();
            tv_duration_current.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if(player.isPlaying()){
            sb_media.setProgress((int)(((float) player.getCurrentPosition()/player.getDuration())*100));
            handler.postDelayed(updater,1000);
        }
    }



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

//    class SeekBarHandler extends AsyncTask<>{
//    }
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