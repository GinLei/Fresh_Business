package com.dingxin.fresh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.dingxin.fresh.J.JPushEntity;
import com.dingxin.fresh.R;
import com.dingxin.fresh.fragment.HomeFragment;
import com.dingxin.fresh.fragment.LiveFragment;
import com.dingxin.fresh.fragment.MOrderFragment;
import com.dingxin.fresh.fragment.MineFragment;
import com.dingxin.fresh.s.KeepManager;
//import com.dingxin.fresh.s.WebSocketClientService;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;


import java.lang.reflect.Field;
import java.util.Locale;

import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt, TextToSpeech.OnInitListener {
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.dingxin.fresh.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private MessageReceiver mMessageReceiver;
    private BottomNavigationView bottomNavigationView;
    //定义Fragment
    private MediaPlayer mediaPlayer;
    private HomeFragment homeFragment;
    private LiveFragment secondFragment;
    private MineFragment fourFragment;
    private MOrderFragment thirdFragment;
    //记录当前正在使用的fragment
    private Fragment isFragment;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment(savedInstanceState);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //doRegisterReceiver();
        //bindService();
        registerMessageReceiver();
        initTextToSpeech();
    }

    private void initTextToSpeech() {
        tts = new TextToSpeech(this, this);
    }

    public void initFragment(Bundle savedInstanceState) {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
            }
            isFragment = homeFragment;
            ft.replace(R.id.container, homeFragment).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_one:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    switchContent(isFragment, homeFragment);
                    return true;
                case R.id.navigation_two:
                    if (secondFragment == null) {
                        secondFragment = new LiveFragment();
                    }
                    switchContent(isFragment, secondFragment);
                    return true;
                case R.id.navigation_three:
                    if (thirdFragment == null) {
                        thirdFragment = new MOrderFragment();
                    }
                    switchContent(isFragment, thirdFragment);
                    return true;
                case R.id.navigation_four:
                    if (fourFragment == null) {
                        fourFragment = new MineFragment();
                    }
                    switchContent(isFragment, fourFragment);
                    return true;
            }
            return false;
        }

    };


    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {
                ft.hide(from).add(R.id.container, to).commit();
            } else {
                ft.hide(from).show(to).commit();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView mMenuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < mMenuView.getChildCount(); i++) {
            BottomNavigationItemView button = (BottomNavigationItemView) mMenuView.getChildAt(i);
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");
            float mSmallLabelSize = mSmallLabel.getTextSize();
            setField(button.getClass(), button, "shiftAmount", 0F);
            setField(button.getClass(), button, "scaleUpFactor", 1F);
            setField(button.getClass(), button, "scaleDownFactor", 1F);
            mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
        }
        mMenuView.updateMenuView();
    }


    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setField(Class targetClass, Object instance, String fieldName, Object value) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


//    private void bindService() {
//
////        Intent bindIntent = new Intent(MainActivity.this, WebSocketClientService.class);
////        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //android8.0以上通过startForegroundService启动service
//            startForegroundService(new Intent(MainActivity.this, WebSocketClientService.class));
//        } else {
//            startService(new Intent(MainActivity.this, WebSocketClientService.class));
//        }
//    }

    private ServiceActivationReceiver serviceActivationReceiver;
    //private WebSocketClient client;
//    private WebSocketClientService.WebSocketClientBinder binder;
//    private WebSocketClientService jWebSClientService;
//    private MessageReceiver messageReceiver;
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            //服务与活动成功绑定
//            binder = (WebSocketClientService.WebSocketClientBinder) iBinder;
//            jWebSClientService = binder.getService();
//            client = jWebSClientService.client;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            //服务与活动断开
//        }
//    };

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }

//    private class MessageReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            mVibrator.vibrate(1500);
//            String message = intent.getStringExtra("message");
//            MediaPlayer mediaPlayer = MediaPlayer.create(getApplication(), Uri.parse(message));
//            //mediaPlayer.setDataSource("https://7chezhibo.com/music/watch_greens.mp3");
//            mediaPlayer.start();
//        }
//    }

//    private void doRegisterReceiver() {
//        messageReceiver = new MessageReceiver();
//        serviceActivationReceiver = new ServiceActivationReceiver();
//        IntentFilter filter = new IntentFilter(getPackageName());
//        IntentFilter filter_s = new IntentFilter("com.dingxin.fresh.s.WebSocketClientService");
//        registerReceiver(messageReceiver, filter);
//        registerReceiver(serviceActivationReceiver, filter_s);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
//        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(serviceConnection);
        //stopService(new Intent(MainActivity.this, WebSocketClientService.class));
        //unregisterReceiver(messageReceiver);
        unregisterReceiver(serviceActivationReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        KeepManager.getInstance().unregisterKeep(this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //设置播放语言
            tts.setLanguage(Locale.CHINESE);
        }
    }

    private class ServiceActivationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            bindService();
        }
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


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        KeepManager.getInstance().registerKeep(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);

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


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String extra = intent.getStringExtra(KEY_EXTRAS);
                    JPushEntity jPushEntity = new Gson().fromJson(extra, JPushEntity.class);
                    int type = jPushEntity.getType();
                    if (type == 2) {
                        //playMusic(context, "android.resource://" + context.getPackageName() + "/" + R.raw.order_weight_vol9);

                        tts.speak(jPushEntity.getContent(), TextToSpeech.QUEUE_FLUSH, null);
                    } else if (type == 3) {
                        playMusic(context, "android.resource://" + context.getPackageName() + "/" + R.raw.watch_greens);
                    } else if (type == 4) {
//                        Log.v("mac", AppUtils.getMacAddress());
//                        Log.v("j_mac", jPushEntity.getContent());
//                        if (!AppUtils.getMacAddress().equals(jPushEntity.getContent())) {
//                            ToastUtils.showShort("此账号已在别处登录");
//                            AppManager.getAppManager().finishAllActivity();
//                            startActivity(new Intent().setClass(context, LoginActivity.class));
//                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playMusic(Context context, String url) {
        Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.vibrate(1000);
        mediaPlayer = MediaPlayer.create(getApplication(), Uri.parse(url));
        mediaPlayer.start();
    }

    public void stopTTS() {
        if (tts != null) {
            tts.shutdown();
            tts.stop();
            tts = null;
        }
    }
}