package com.example.clickergame.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.example.clickergame.R;
import com.example.clickergame.util.SharedResources;
import com.example.clickergame.util.Utils;

public class SummaryActivity extends AppCompatActivity implements InitiableActivity {
    private TextView TIMES_CLICKED;
    private TextView MONEY_COLLECTED;
    private TextView TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_summary);
        initActivity();
    }

    @Override
    public void onBackPressed() {
        Utils.triggerRebirth(this, MainActivity.class);
    }

    @Override
    public void initActivity() {
        Bundle bundle = getIntent().getExtras();

        TIMES_CLICKED = findViewById(R.id.timesClicked);
        TIMES_CLICKED.setText(TIMES_CLICKED.getText() + " " +
                SharedResources.player.getTimesClicked().toString());

        MONEY_COLLECTED = findViewById(R.id.moneyCollected);
        MONEY_COLLECTED.setText(MONEY_COLLECTED.getText() + " " + String.valueOf(bundle.get("money")));

        TOTAL_TIME = findViewById(R.id.totalTime);
        TOTAL_TIME.setText(TOTAL_TIME.getText() + " " +
                String.valueOf(((Long)bundle.get("time") / 1000)) + " seconds");
    }
}