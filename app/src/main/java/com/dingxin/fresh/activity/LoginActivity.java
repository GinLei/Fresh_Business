package com.dingxin.fresh.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.J.JUtil;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.ActivityLoginBinding;
import com.dingxin.fresh.vm.LoginViewModel;
import com.yanzhenjie.sofia.Sofia;

import java.util.Locale;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements CustomAdapt {
    private CountDownTimer timer;
    private TextToSpeech tts;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        Sofia.with(this).statusBarBackgroundAlpha(0).invasionStatusBar();
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        JUtil.requestPermission(this);
        ignoreBatteryOptimization(this);
    }


    public void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
                /**
                 * 判断当前APP是否有加入电池优化的白名单，
                 * 如果没有，弹出加入电池优化的白名单的设置对话框
                 * */
                if (!hasIgnored) {
                    Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initViewObservable() {
        viewModel.yzEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.btYz.setClickable(false);
                timer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btYz.setEnabled(false);
                        binding.btYz.setText("已发送(" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        binding.btYz.setClickable(true);
                        binding.btYz.setEnabled(true);
                        binding.btYz.setText("请重新发送");
                    }
                }.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
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
}