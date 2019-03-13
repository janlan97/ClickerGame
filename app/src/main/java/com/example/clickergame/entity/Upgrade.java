package com.example.clickergame.entity;

import com.example.clickergame.util.SharedResources;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Upgrade implements Serializable {
    private String name;
    private UpgradeType type;
    private UpgradeOf ofModifier;
    private int modifier;
    private int lvl;
    private int cost;

    public static void triggerUpgrade(UpgradeType upgradeType, Consumer<Upgrade> consumer) {
        SharedResources.player.getBoughtUpgrades()
                .stream()
                .filter(upgrade -> upgrade.getType() == upgradeType)
                .forEach(upgrade -> consumer.accept(upgrade));
    }

    public static List<Upgrade> mockUpgrades() {
        List<Upgrade> upgrades = new ArrayList<>();
        Upgrade u1 = new Upgrade();
        u1.setName("multiclick");
        u1.setType(UpgradeType.CLICK);
        u1.setCost(100);
        u1.setLvl(1);
        u1.setModifier(1);
        u1.setOfModifier(UpgradeOf.CLICK);

        Upgrade u4 = new Upgrade();
        u4.setName("money on click");
        u4.setType(UpgradeType.CLICK);
        u4.setCost(150);
        u4.setLvl(1);
        u4.setModifier(1);
        u4.setOfModifier(UpgradeOf.MONEY);

        Upgrade u2 = new Upgrade();
        u2.setName("passive click");
        u2.setType(UpgradeType.PASSIVE);
        u2.setCost(1);
        u2.setLvl(1);
        u2.setModifier(1);
        u2.setOfModifier(UpgradeOf.CLICK);

        Upgrade u3 = new Upgrade();
        u3.setName("passive money");
        u3.setType(UpgradeType.PASSIVE);
        u3.setCost(500);
        u3.setLvl(1);
        u3.setModifier(1);
        u3.setOfModifier(UpgradeOf.MONEY);

        Upgrade u5 = new Upgrade();
        u5.setName("shake");
        u5.setType(UpgradeType.SHAKE);
        u5.setCost(100);
        u5.setLvl(1);
        u5.setModifier(1);
        u5.setOfModifier(UpgradeOf.CLICK);

        Upgrade u6 = new Upgrade();
        u6.setName("money per shake");
        u6.setType(UpgradeType.SHAKE);
        u6.setCost(150);
        u6.setLvl(1);
        u6.setModifier(1);
        u6.setOfModifier(UpgradeOf.MONEY);

        upgrades.add(u1);
        upgrades.add(u4);
        upgrades.add(u2);
        upgrades.add(u3);
        upgrades.add(u5);
        upgrades.add(u6);

        return upgrades;
    }

    public void update() {
        cost *= 10;
        lvl += 1;
        modifier += 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpgradeType getType() {
        return type;
    }

    public void setType(UpgradeType type) {
        this.type = type;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public UpgradeOf getOfModifier() {
        return ofModifier;
    }

    public void setOfModifier(UpgradeOf ofModifier) {
        this.ofModifier = ofModifier;
    }
}