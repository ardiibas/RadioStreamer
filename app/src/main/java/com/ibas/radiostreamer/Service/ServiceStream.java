package com.ibas.radiostreamer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ibas.radiostreamer.R;

import java.io.IOException;

/**
 * Created by Ibas on 30/05/2017.
 */

public class ServiceStream extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created!", Toast.LENGTH_SHORT).show();
//        mediaPlayer.create(this, R.string.URL_BASE);
        Uri uri = Uri.parse("http://stream.radioreklama.bg:80/radio1rock128");
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e){
            e.printStackTrace();
        }

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                // INCOMING CALL
                if (state == TelephonyManager.CALL_STATE_RINGING){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                }
                // NO CALL
                if (state == TelephonyManager.CALL_STATE_IDLE){
                    if (mediaPlayer != null){
                        mediaPlayer.start();
                    }
                }
                // IN CALL IS DIALING
                if (state == TelephonyManager.CALL_STATE_OFFHOOK){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                }
                    super.onCallStateChanged(state, incomingNumber);
            }
        };

        TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        if (manager != null){
            manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_SHORT).show();
        mediaPlayer.release();
    }
}
