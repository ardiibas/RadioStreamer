package com.ibas.radiostreamer.Main;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ibas.radiostreamer.R;

import java.io.IOException;

import static android.support.v7.appcompat.R.styleable.View;

/**
 * Created by Ibas on 29/05/2017.
 */

public class Main extends AppCompatActivity implements View.OnClickListener {

    Button button_play;
    boolean prepare = false;

    boolean start = false;
    MediaPlayer mediaPlayer;
    String stream = "http://streaming.unisifm.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play = (Button)findViewById(R.id.button_play);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PlayerTask().execute(stream);

        button_play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private class PlayerTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {

            try {
                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return prepare;
        }
    }
}
