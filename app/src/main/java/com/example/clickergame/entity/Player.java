package com.example.clickergame.entity;

import com.example.clickergame.util.SharedResources;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private Integer timesClicked;
    private Integer money;
    private List<Upgrade> boughtUpgrades;
    private static Player instance = null;
    private Integer clicksOnSingleClick;
    private Integer moneyOnSingleClick;
    private Integer clicksOnTime;
    private Integer moneyOnTime;
    private Integer shakeModifier;
    private Integer moneyOnShake;

    public static Player getInstance() {
        return instance == null ? new Player() : instance;
    }

    private Player() {
        timesClicked = 0;
        money = 0;
        boughtUpgrades = new ArrayList<>();
        clicksOnSingleClick = 1;
        moneyOnSingleClick = 1;
        clicksOnTime = 0;
        moneyOnTime = 0;
        shakeModifier = 0;
        moneyOnShake = 1;
    }

    public void click(UpgradeType source) {
        if(source == UpgradeType.CLICK) {
            timesClicked += clicksOnSingleClick;
            money += moneyOnSingleClick;
        } else if(source == UpgradeType.PASSIVE) {
            timesClicked += clicksOnTime;
            money += moneyOnTime;
            System.out.println(moneyOnTime);
        } else if(source == UpgradeType.SHAKE) {
            timesClicked += shakeModifier;
            money += moneyOnShake;
        }
    }

    public void buyUpgrade(Upgrade upgrade) {
        if(money >= upgrade.getCost()) {
            boughtUpgrades.add(upgrade);
            money -= upgrade.getCost();
        }
        includeUpgrade(upgrade);
    }

    private void includeUpgrade(Upgrade upgrade) {
        if(upgrade.getType() == UpgradeType.CLICK && upgrade.getOfModifier() == UpgradeOf.CLICK) {
            clicksOnSingleClick = upgrade.getModifier();
            moneyOnSingleClick += clicksOnSingleClick * moneyOnSingleClick;
        } else if(upgrade.getType() == UpgradeType.CLICK && upgrade.getOfModifier() == UpgradeOf.MONEY) {
            moneyOnSingleClick += upgrade.getModifier();
        } else if(upgrade.getType() == UpgradeType.PASSIVE && upgrade.getOfModifier() == UpgradeOf.CLICK) {
            clicksOnTime = upgrade.getModifier();
            moneyOnTime += clicksOnTime ;
            SharedResources.justBought = true;
        } else if(upgrade.getType() == UpgradeType.PASSIVE && upgrade.getOfModifier() == UpgradeOf.MONEY) {
            moneyOnTime += upgrade.getModifier();
        } else if(upgrade.getType() == UpgradeType.SHAKE && upgrade.getOfModifier() == UpgradeOf.CLICK) {
            shakeModifier += (upgrade.getModifier() * 10);
            moneyOnShake = upgrade.getModifier();
        } else if(upgrade.getType() == UpgradeType.SHAKE && upgrade.getOfModifier() == UpgradeOf.MONEY) {
            moneyOnShake += upgrade.getModifier();
        }
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getTimesClicked() {
        return timesClicked;
    }

    public void setTimesClicked(Integer timesClicked) {
        this.timesClicked = timesClicked;
    }

    public List<Upgrade> getBoughtUpgrades() {
        return boughtUpgrades;
    }

    public void setBoughtUpgrades(List<Upgrade> boughtUpgrades) {
        this.boughtUpgrades = boughtUpgrades;
    }

    public Integer getClicksOnSingleClick() {
        return clicksOnSingleClick;
    }

    public void setClicksOnSingleClick(Integer clicksOnSingleClick) {
        this.clicksOnSingleClick = clicksOnSingleClick;
    }

    public Integer getMoneyOnSingleClick() {
        return moneyOnSingleClick;
    }

    public void setMoneyOnSingleClick(Integer moneyOnSingleClick) {
        this.moneyOnSingleClick = moneyOnSingleClick;
    }

    public Integer getClicksOnTime() {
        return clicksOnTime;
    }

    public void setClicksOnTime(Integer clicksOnTime) {
        this.clicksOnTime = clicksOnTime;
    }

    public Integer getMoneyOnTime() {
        return moneyOnTime;
    }

    public void setMoneyOnTime(Integer moneyOnTime) {
        this.moneyOnTime = moneyOnTime;
    }

    public Integer getShakeModifier() {
        return shakeModifier;
    }

    public void setShakeModifier(Integer shakeModifier) {
        this.shakeModifier = shakeModifier;
    }

    public Integer getMoneyOnShake() {
        return moneyOnShake;
    }

    public void setMoneyOnShake(Integer moneyOnShake) {
        this.moneyOnShake = moneyOnShake;
    }
}