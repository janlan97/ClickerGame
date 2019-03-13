package com.example.clickergame.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.clickergame.R;
import com.example.clickergame.entity.Upgrade;
import java.util.List;

public class UpgradeAdapter extends ArrayAdapter<Upgrade> {

    public UpgradeAdapter(Context context, List<Upgrade> upgrades) {
        super(context,0, upgrades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Upgrade upgrade = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.product_list_layout, parent, false);
        }
        TextView name = convertView.findViewById(R.id.nameTextField);
        TextView lvl = convertView.findViewById(R.id.lvlTextField);
        TextView modifier = convertView.findViewById(R.id.modifierTextField);
        TextView type = convertView.findViewById(R.id.typeTextField);
        TextView cost = convertView.findViewById(R.id.costTextField);

        name.setText(upgrade.getName());
        lvl.setText(upgrade.getLvl() + "");
        modifier.setText(upgrade.getModifier() + "");
        type.setText(upgrade.getType().toString());
        cost.setText(upgrade.getCost() + "");

        return convertView;
    }
}