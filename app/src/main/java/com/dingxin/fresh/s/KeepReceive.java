package com.dingxin.fresh.s;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class KeepReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (TextUtils.equals(action, intent.ACTION_SCREEN_OFF)) {
            KeepManager.getInstance().startKeep(context);
        } else if (TextUtils.equals(action, intent.ACTION_SCREEN_ON)) {
            KeepManager.getInstance().finishKeep();
        }
    }
}