package com.example.clickergame.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.clickergame.R;
import com.example.clickergame.util.SharedResources;
import com.example.clickergame.util.UpgradeAdapter;
import com.example.clickergame.entity.Player;
import com.example.clickergame.entity.Upgrade;
import com.example.clickergame.util.Utils;
import java.util.List;

public class StoreActivity extends AppCompatActivity implements InitiableActivity {
    private ListView LIST_VIEW;
    private TextView CURRENT_MONEY;
    private Player player;
    private List<Upgrade> mockedUpgrades = SharedResources.upgrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store);
        initActivity();
    }

    @Override
    public void onBackPressed() {
        Utils.startActivityOf(StoreActivity.this, GameActivity.class, null);
    }

    @Override
    public void initActivity() {
        player = SharedResources.player;

        CURRENT_MONEY = findViewById(R.id.currentMoney);
        CURRENT_MONEY.setText(getString(R.string.current_money) + " " + player.getMoney());

        LIST_VIEW = findViewById(R.id.listView);
        LIST_VIEW.setAdapter(new UpgradeAdapter(this, mockedUpgrades));
        LIST_VIEW.setOnItemClickListener((parent, view, position, id) -> {
            Upgrade upgrade = (Upgrade)LIST_VIEW.getAdapter().getItem(position);
            if(upgrade.getCost() > player.getMoney()) {
                LIST_VIEW.getChildAt(position).setEnabled(false);
            } else {
                player.buyUpgrade(upgrade);
                upgrade.update();
                ((BaseAdapter) LIST_VIEW.getAdapter()).notifyDataSetChanged();
            }
        });
        keepRefreshingCurrentMoneyTextView().run();
    }

    private Runnable keepRefreshingCurrentMoneyTextView() {
        return new Runnable() {
            @Override
            public void run() {
                CURRENT_MONEY.setText(getString(R.string.current_money) + " " + player.getMoney());
                new Handler().post(this);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}