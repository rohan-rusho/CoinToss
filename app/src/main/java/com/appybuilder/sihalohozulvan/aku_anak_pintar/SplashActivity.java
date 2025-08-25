package com.appybuilder.sihalohozulvan.aku_anak_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 2.5 seconds
    private TextView appNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appNameText = findViewById(R.id.app_name);

        // Typing animation for app name
        String appName = "Coin Toss";
        typeWriterEffect(appName, 0);

        // Move to MainActivity after SPLASH_DURATION
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);
    }

    private void typeWriterEffect(final String text, final int index) {
        if (index < text.length()) {
            appNameText.setText(appNameText.getText() + String.valueOf(text.charAt(index)));
            appNameText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    typeWriterEffect(text, index + 1);
                }
            }, 150); // typing speed in ms
        }
    }
}
