package com.dingxin.fresh.s;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.dingxin.fresh.activity.KeepActivity;

import java.lang.ref.WeakReference;

public class KeepManager {

    private static KeepManager instance = new KeepManager();

    public static KeepManager getInstance() {
        return instance;
    }


    private KeepManager() {

    }

    private KeepReceive keepReceive;

    private WeakReference<Activity> mActivity;

    /**
     * 注册关屏   开屏
     */

    public void registerKeep(Context context) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        keepReceive = new KeepReceive();
        context.registerReceiver(keepReceive, intentFilter);
    }


    /**
     * 注销广播
     */

    public void unregisterKeep(Context context) {
        if (null != keepReceive) {
            context.unregisterReceiver(keepReceive);
        }
    }


    /**
     * 开启1像素的activity
     */

    public void startKeep(Context context) {
        Intent intent = new Intent(context, KeepActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 关闭activity
     */
    public void finishKeep() {
        if (null != mActivity) {
            Activity activity = mActivity.get();
            if (null != activity) {
                activity.finish();
            }
            mActivity = null;
        }
    }

    //绑定弱引用
    public void setKeep(KeepActivity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }
}