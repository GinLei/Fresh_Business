package com.dingxin.fresh.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.J.JPushEntity;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.ActivityTabBarBinding;
import com.dingxin.fresh.fragment.HomeFragment;
import com.dingxin.fresh.fragment.LiveFragment;
import com.dingxin.fresh.fragment.MOrderFragment;
import com.dingxin.fresh.fragment.MineFragment;
import com.dingxin.fresh.s.NotificationService;
import com.google.gson.Gson;
import com.yanzhenjie.sofia.Sofia;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.jessyan.autosize.internal.CustomAdapt;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class TabBarActivity extends BaseActivity<ActivityTabBarBinding, BaseViewModel> implements CustomAdapt {
    private List<Fragment> mFragments;
    private MessageReceiver mMessageReceiver;
    private NavigationController navigationController;
    public static final String MESSAGE_RECEIVED_ACTION = "com.dingxin.fresh.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    private Handler alive = new Handler();
    @SuppressLint("InvalidWakeLockTag")
    private PowerManager.WakeLock mWakeLock;
    private Runnable alive_runnable = new Runnable() {
        @Override
        public void run() {
            mWakeLock.acquire(5 * 1000L /*1 minutes*/);
            mWakeLock.release();
        }
    };
    private NotificationService.MyBinder myBinder;
    private ScreenStatusReceiver mScreenStatusReceiver;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (NotificationService.MyBinder) service;
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        Sofia.with(this).statusBarBackgroundAlpha(0).invasionStatusBar();
        Intent bindIntent = new Intent(this, NotificationService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
        registerMessageReceiver();
        return R.layout.activity_tab_bar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void initData() {
        initFragment();
        initBottomTab();
        mWakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mScreenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStatusReceiver, screenStatusIF);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new LiveFragment());
        mFragments.add(new MOrderFragment());
        mFragments.add(new MineFragment());
        commitAllowingStateLoss(0);
    }

    private void initBottomTab() {

        navigationController = binding.tab.custom()
                .addItem(newItem(R.drawable.ic_home, R.drawable.ic_home_cl, "首页"))
                .addItem(newItem(R.drawable.ic_live, R.drawable.ic_live_cl, "直播"))
                .addItem(newItem(R.drawable.ic_order, R.drawable.ic_order_cl, "订单"))
                .addItem(newItem(R.drawable.ic_mine, R.drawable.ic_mine_cl, "我的"))
                .build();

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                commitAllowingStateLoss(index);
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    private void commitAllowingStateLoss(int position) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(position + "");
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = mFragments.get(position);
            transaction.add(R.id.frameLayout, currentFragment, position + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(Color.parseColor("#ff7845"));
        return normalItemView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        unbindService(connection);
        unregisterReceiver(mScreenStatusReceiver);
        alive = null;
        alive_runnable = null;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String extra = intent.getStringExtra(KEY_EXTRAS);
                    JPushEntity jPushEntity = new Gson().fromJson(extra, JPushEntity.class);
                    int type = jPushEntity.getType();
                    if (type == 2 || type == 3) {
                        if (myBinder != null) {
                            myBinder.play(jPushEntity.getContent());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot()) {
                moveTaskToBack(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class ScreenStatusReceiver extends BroadcastReceiver {
        String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
        String SCREEN_ON = "android.intent.action.SCREEN_ON";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SCREEN_OFF.equals(intent.getAction())) {
                alive.postDelayed(alive_runnable, 1000L * 60);
            } else if (SCREEN_ON.equals(intent.getAction())) {
                alive.removeCallbacks(alive_runnable);
            }
        }
    }
}