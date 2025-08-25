package com.appybuilder.sihalohozulvan.aku_anak_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView coinText, bigResultText;
    private Button flipButton, shareButton;
    private Random random;
    private String lastResult = "";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinText = findViewById(R.id.coinText);
        bigResultText = findViewById(R.id.bigResultText);
        flipButton = findViewById(R.id.flipButton);
        shareButton = findViewById(R.id.shareButton);
        random = new Random();

        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCoinFlip();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });
    }

    private void startCoinFlip() {
        flipButton.setEnabled(false);
        bigResultText.setText("");
        shareButton.setVisibility(View.GONE);

        // Rotate animation
        RotateAnimation rotate = new RotateAnimation(
                0, 1440,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(3000);
        rotate.setFillAfter(true);
        coinText.startAnimation(rotate);

        // Flickering effect
        final int duration = 3000; // 3 seconds
        final int interval = 100;  // update 10 times per second
        final long startTime = System.currentTimeMillis();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed < duration) {
                    // Randomly show H or T
                    coinText.setText(random.nextBoolean() ? "H" : "T");
                    handler.postDelayed(this, interval);
                } else {
                    // Show final result
                    boolean isHeads = random.nextBoolean();
                    if (isHeads) {
                        coinText.setText("H");
                        lastResult = "Heads";
                    } else {
                        coinText.setText("T");
                        lastResult = "Tails";
                    }
                    bigResultText.setText("You got " + lastResult + "!");
                    shareButton.setVisibility(View.VISIBLE);
                    flipButton.setEnabled(true);
                }
            }
        });
    }

    private void shareResult() {
        if (!lastResult.isEmpty()) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I just got " + lastResult + " in Coin Toss!");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
}
