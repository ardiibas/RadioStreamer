package com.ibas.radiostreamer.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ibas.radiostreamer.R;
import com.ibas.radiostreamer.Service.ServiceStream;

/**
 * Created by Ibas on 30/05/2017.
 */

public class MainWithService extends AppCompatActivity implements View.OnClickListener {
    Button button_play, button_stop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play = (Button)findViewById(R.id.button_play);
        button_stop = (Button)findViewById(R.id.button_stop);

        button_play.setOnClickListener(this);
        button_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button_play){
            startService(new Intent(MainWithService.this, ServiceStream.class));
        }
        if (v == button_stop){
            stopService(new Intent(MainWithService.this, ServiceStream.class));
        }
    }
}
