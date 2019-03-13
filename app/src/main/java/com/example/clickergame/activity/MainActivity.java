package com.example.clickergame.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import com.example.clickergame.R;
import com.example.clickergame.util.Utils;

public class MainActivity extends AppCompatActivity implements InitiableActivity {
    private Button START_BTN;
    private Button EXIT_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initActivity();
    }

    @Override
    public void initActivity() {
        START_BTN = findViewById(R.id.startBtn);
        START_BTN.setOnClickListener((l) -> Utils.startActivityOfAndFinish(MainActivity.this,
                GameActivity.class,null));
        EXIT_BTN = findViewById(R.id.exitBtn);
        EXIT_BTN.setOnClickListener((l) -> {
            finishAndRemoveTask();
            System.exit(0);
        });
    }
}