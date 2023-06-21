package be.antwaan.moresncb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import be.antwaan.moresncb.logica.Memory;

public class SplashActivity extends AppCompatActivity {
    private Memory memory;
    private static final long SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        memory = new Memory(this);
        resetMemory();
        // Use a Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, SPLASH_DELAY);
    }

    private void resetMemory(){
        memory.writeToMemory("Departure", null);
        memory.writeToMemory("Destination", null);
        memory.writeToMemory("DateTime", null);
        memory.writeToMemory("DepArr", null);
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: finish the splash screen activity
    }
}