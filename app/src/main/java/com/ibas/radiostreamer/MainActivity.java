package com.ibas.radiostreamer;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ibas.radiostreamer.Service.StreamService;
import com.ibas.radiostreamer.Utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent serviceIntent;
    private Button button_play;
    private static boolean isStreaming = false;
    private ProgressDialog pdBuff = null;
    private boolean mBufferBroadcastIsRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play = (Button)findViewById(R.id.button_play);
        button_play.setOnClickListener(this);

        serviceIntent = new Intent(this, StreamService.class);
        isStreaming = Utils.getDataBooleanFromSP(this, Utils.IS_STREAM);
        if (isStreaming)
            button_play.setText("Stop");
    }

    @Override
    public void onClick(View v) {
        if (v == button_play){
            Log.d("playStatus", "" + isStreaming);

            if (!isStreaming) {
                button_play.setText("Stop");
                startStreaming();
                Utils.setDataBooleanToSP(this, Utils.IS_STREAM, true);
            } else {
                if (isStreaming) {
                    button_play.setText("Start");
                    Toast.makeText(this, "Stop Streaming..", Toast.LENGTH_SHORT).show();
                    stopStreaming();
                    isStreaming = false;
                    Utils.setDataBooleanToSP(this, Utils.IS_STREAM, false);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mBufferBroadcastIsRegistered) {
            registerReceiver(broadcastBufferReceiver, new IntentFilter(
                    StreamService.BROADCAST_BUFFER));
            mBufferBroadcastIsRegistered = true;
        }
    }

    private void startStreaming() {
        Toast.makeText(this, "Start Streaming..", Toast.LENGTH_SHORT).show();
        stopStreaming();
        try {
            startService(serviceIntent);
        } catch (Exception e) {
        }
    }

    private void stopStreaming() {
        try {
            stopService(serviceIntent);
        } catch (Exception e) {

        }
    }

    private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            
        }
    }
}
