package com.example.clickergame.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import com.example.clickergame.util.SharedResources;
import com.example.clickergame.entity.Player;
import com.example.clickergame.R;
import com.example.clickergame.entity.Upgrade;
import com.example.clickergame.entity.UpgradeType;
import com.example.clickergame.util.Utils;
import java.util.function.Consumer;

public class GameActivity extends AppCompatActivity
        implements InitiableActivity, SensorEventListener {

    private Chronometer TIMER;
    private Button CLICK_BTN;
    private Button STORE_BTN;
    private TextView TIMES_CLICKED;
    private TextView CURRENT_MONEY;
    private Player player;
    private Integer allMoneyCollected;
    private final Handler HANDLER = new Handler();
    private Runnable currentBonus;
    private SensorManager sensorManager;
    private boolean isStopHandler;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            performShake(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        initActivity();
    }

    @Override
    public void onBackPressed() {
        createExitAlert().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleTriggerPassiveBonus();
        Upgrade.triggerUpgrade(UpgradeType.SHAKE, upgrade ->
                sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL));
        CURRENT_MONEY.setText(getString(R.string.current_money)+ " " + player.getMoney().toString());
    }

    @Override
    public void initActivity() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        allMoneyCollected = 0;
        isStopHandler = false;

        player = SharedResources.player;
        TIMER = findViewById(R.id.timer);
        TIMER.start();
        TIMES_CLICKED = findViewById(R.id.timesClicked);

        CURRENT_MONEY = findViewById(R.id.currentMoney);
        CURRENT_MONEY.setText(getString(R.string.current_money)+ " " + player.getMoney().toString());

        STORE_BTN = findViewById(R.id.storeBtn);
        STORE_BTN.setOnClickListener(l -> Utils.startActivityOf(GameActivity.this,
                StoreActivity.class, null));

        CLICK_BTN = findViewById(R.id.clickBtn);
        CLICK_BTN.setOnClickListener((l) -> {
            player.click(UpgradeType.CLICK);
            allMoneyCollected += player.getMoneyOnSingleClick();
            updateGameViews();
        });
    }

    private void performShake(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if (accelerationSquareRoot > 48 - player.getShakeModifier()) {
            player.click(UpgradeType.SHAKE);
            allMoneyCollected += player.getMoneyOnShake();
            updateGameViews();
        }
    }

    private void handleTriggerPassiveBonus() {
        Consumer<Upgrade> consumer = upgrade -> {
            currentBonus = createPassiveBonusRunnable();
            currentBonus.run();
        };
        if(currentBonus == null && SharedResources.justBought) {
            SharedResources.justBought = false;
            Upgrade.triggerUpgrade(UpgradeType.PASSIVE, consumer);
        } else if(currentBonus != null && SharedResources.justBought) {
            isStopHandler = true;
            Upgrade.triggerUpgrade(UpgradeType.PASSIVE, consumer);
            isStopHandler=false;
        }
    }

    private Runnable createPassiveBonusRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                if(isStopHandler) {
                    HANDLER.removeCallbacks(this);
                    return;
                }
                player.click(UpgradeType.PASSIVE);
                allMoneyCollected += player.getMoneyOnTime();
                updateGameViews();
                HANDLER.postDelayed(this, (5000 / player.getClicksOnTime()));
            }
        };
    }

    private void updateGameViews() {
        TIMES_CLICKED.setText(player.getTimesClicked() + "");
        CURRENT_MONEY.setText(getString(R.string.current_money)+ " " + player.getMoney().toString());
    }

    private AlertDialog createExitAlert() {
        return new AlertDialog.Builder(this)
                .setTitle("Exit game?")
                .setMessage("Do you really want to exit game?")
                .setPositiveButton("Exit", ((dialog, which) -> Utils.startActivityOfAndFinish(
                        GameActivity.this,
                        SummaryActivity.class,
                        createGameInfoBundleForSummaryActivity())))
                .setNegativeButton("Stay", ((dialog, which) -> dialog.cancel()))
                .create();
    }

    private Bundle createGameInfoBundleForSummaryActivity() {
        Bundle bundle = new Bundle();
        bundle.putLong("time",(SystemClock.elapsedRealtime() - TIMER.getBase()));
        bundle.putInt("money", allMoneyCollected);
        return bundle;
    }
}