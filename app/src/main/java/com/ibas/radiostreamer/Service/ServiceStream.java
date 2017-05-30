package com.ibas.radiostreamer.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ibas.radiostreamer.Main.MainWithService;
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

        // Phone
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

        // Notification
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_kal_ph)
                .setContentTitle("Test")
                .setContentText("Isi test");

        Intent mainIntent = new Intent(this, MainWithService.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);

        taskStackBuilder.addParentStack(MainWithService.class);

        taskStackBuilder.addNextIntent(mainIntent);
        PendingIntent mainPendingIntent = taskStackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(mainPendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(1, builder.build());
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
