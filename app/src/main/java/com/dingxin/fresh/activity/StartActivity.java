package com.dingxin.fresh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.dingxin.fresh.R;
import com.yanzhenjie.sofia.Sofia;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.jessyan.autosize.internal.CustomAdapt;

public class StartActivity extends AppCompatActivity implements CustomAdapt {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(SPUtils.getInstance().getString("token"))) {
                    intent.setClass(StartActivity.this, LoginActivity.class);
                } else {
                    intent.setClass(StartActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
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
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}
