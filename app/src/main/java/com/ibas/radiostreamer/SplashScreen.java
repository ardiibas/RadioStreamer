package com.ibas.radiostreamer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.ibas.radiostreamer.Main.MainWithService;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainWithService.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
