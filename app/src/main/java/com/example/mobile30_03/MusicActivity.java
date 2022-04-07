package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicActivity extends AppCompatActivity {

    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        player = MediaPlayer.create(this, R.raw.sound);

        Button btn_play = findViewById(R.id.btn_play);
        Button btn_pause = findViewById(R.id.btn_pause);

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.start();
            }
        });
    }
}

/*TODO 1:
    3 defa yanlis loginde disable
    ad soyad-bday-phone-mail
    signup sonrasi mail gonder
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