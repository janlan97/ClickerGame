package com.example.clickergame.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

public class Utils {

    public static void startActivityOfAndFinish(AppCompatActivity context, Class<?> cls, Bundle bundle) {
        startActivityOf(context,cls,bundle);
        context.finish();
    }

    public static void startActivityOf(AppCompatActivity context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if(bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void triggerRebirth(Context context, Class myClass) {
        Intent intent = new Intent(context, myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Runtime.getRuntime().exit(0);
    }
}