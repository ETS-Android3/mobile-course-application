package com.example.mobile30_03.music;

import static com.example.mobile30_03.utils.HelperFunctions.milliSecondsToTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.utils.MediaPlayerManager;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance();
    Music currentMusic;

    ImageButton btn_shuf;
    ImageButton btn_prev;
    ImageButton btn_play;
    ImageButton btn_next;
    ImageButton btn_loop;
    ImageView iv_album_art;
    SeekBar sb_media;
    TextView tv_duration_current;
    TextView tv_duration_total;
    TextView tv_artist_name;
    TextView tv_song_name;


    private Handler handler =new Handler();
    private class Updater implements Runnable{
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration=mediaPlayerManager.getMediaPlayer().getCurrentPosition();
            tv_duration_current.setText(milliSecondsToTimer(currentDuration));
        }
    }
    private Runnable myUpdater = new Updater();
    private void updateSeekBar(){
        if(mediaPlayerManager.getMediaPlayer().isPlaying()){
            sb_media.setProgress((int)(((float) mediaPlayerManager.getMediaPlayer().getCurrentPosition()/mediaPlayerManager.getMediaPlayer().getDuration())*100));
            handler.postDelayed(myUpdater,1000);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        findViewByIds();


        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        Log.d(TAG, "onCreate: position: " + position);

        mediaPlayerInit(position);

        btn_play.setOnClickListener(v -> {
            if (mediaPlayerManager.getMediaPlayer().isPlaying()){
                mediaPlayerManager.pause();
                //Remove handler callbacks when music stops
                handler.removeCallbacks(myUpdater);
                btn_play.setImageResource(R.drawable.ic_baseline_play_circle_filled);
            }else{
                mediaPlayerManager.play();
                btn_play.setImageResource(R.drawable.ic_baseline_pause_circle_filled);
                updateSeekBar();
            }
        });

        btn_prev.setOnClickListener(view -> {
            if (mediaPlayerManager.getMediaPlayer().getCurrentPosition() / 1000 > 10){
                mediaPlayerManager.getMediaPlayer().seekTo(0);
            }else {
                mediaPlayerInit(mediaPlayerManager.getCurrentSongIndex() - 1);
            }
        });

        btn_next.setOnClickListener(view -> {
            mediaPlayerInit(mediaPlayerManager.getCurrentSongIndex()+1);
        });

        btn_loop.setOnClickListener(view -> {
            if (mediaPlayerManager.getMediaPlayer().isLooping()){
                mediaPlayerManager.getMediaPlayer().setLooping(false);
                btn_loop.setColorFilter(R.color.blue);
                Log.i(TAG, "onCreate: btn_loop - false");
            }else{
                mediaPlayerManager.getMediaPlayer().setLooping(true);
                btn_loop.clearColorFilter();
                Log.i(TAG, "onCreate: btn_loop - true");
            }
        });


        btn_shuf.setOnClickListener(view -> {
            if (mediaPlayerManager.isShuffle()){
                mediaPlayerManager.setShuffle(false);
                btn_shuf.setColorFilter(R.color.blue);
                Log.i(TAG, "onCreate: btn_shuf - false");
            }else{
                mediaPlayerManager.setShuffle(true);
                btn_shuf.clearColorFilter();
                Log.i(TAG, "onCreate: btn_shuf - true");
            }
        });

    }

    private void findViewByIds() {
        tv_duration_current = findViewById(R.id.tv_duration_current);
        tv_duration_total = findViewById(R.id.tv_duration_total);
        tv_artist_name = findViewById(R.id.tv_artist_name);
        tv_song_name = findViewById(R.id.tv_song_name);
        iv_album_art = findViewById(R.id.iv_album_art);
        btn_shuf = findViewById(R.id.btn_shuf);
        btn_prev = findViewById(R.id.btn_prev);
        btn_play = findViewById(R.id.btn_play);
        btn_next = findViewById(R.id.btn_next);
        btn_loop = findViewById(R.id.btn_loop);
        sb_media = findViewById(R.id.sb_media);
    }

    private void mediaPlayerInit(int position) {
        //mediaPlayer init
        mediaPlayerManager.setMediaPlayer(this, position);
        mediaPlayerManager.play();
        currentMusic = mediaPlayerManager.getMusicList().get(position);

        mediaPlayerManager.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayerInit(mediaPlayerManager.getCurrentSongIndex()+1);
            }
        });
        mediaUIInit();
    }

    private void mediaUIInit() {
        btn_play.setImageResource(R.drawable.ic_baseline_pause_circle_filled);
        tv_artist_name.setText(currentMusic.getArtist_name());
        tv_duration_total.setText(milliSecondsToTimer(currentMusic.getDuration()));
        tv_song_name.setText(currentMusic.getSong_name());
        iv_album_art.setImageResource(currentMusic.getArt());
        sb_media.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekTo;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekTo = i;
                tv_duration_current.setText(milliSecondsToTimer(seekTo * mediaPlayerManager.getMediaPlayer().getDuration() / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(myUpdater);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayerManager.getMediaPlayer().seekTo(seekTo * mediaPlayerManager.getMediaPlayer().getDuration() / 100 );
//                tv_duration_current.setText(milliSecondsToTimer(seekTo * mediaPlayerManager.getMediaPlayer().getDuration() / 100));
                updateSeekBar();
            }
        });
        updateSeekBar();
    }
}

/*
TODO,S:
    Loop tuşu
    Shuffler tuşu
    Arkaplan falan
    Geri dönüp tekrar aynı şarkıya tıkladığında baştan başlatmama
    En sonda ve başta overflow*underflow checks
    Playlist
 */