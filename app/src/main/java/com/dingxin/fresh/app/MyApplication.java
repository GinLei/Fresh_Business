package com.dingxin.fresh.app;

import android.app.ActivityManager;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.activity.MainActivity;
import com.dingxin.fresh.e.LoginBean;
import com.dingxin.fresh.s.KeepManager;
import com.example.jjhome.network.DeviceUtils;
import com.example.jjhome.network.TestEvent;
import com.jjhome.master.http.MasterRequest;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.rtmp.TXLiveBase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends BaseApplication {
    public static String rid = "";
    public static MyApplication instance;
    public LoginBean loginBean;
    private static Context mContext;
    private static TestEvent event;
    private static Bundle bundle;
    public static MasterRequest masterRequest;

    //public static final String APP_ID = "a27xxxxxxxxxxxxxxxxxxxxxxxx22";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        KLog.init(true);
//配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//                .errorDrawable(R.mipmap.ic_launcher) //错误图标
//                .restartActivity(LoginActivity.class) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
        initBle();
//        if (LeakCanary.isInAnalyzerProcess(this)) {//1
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        //initMI();
//        LeakCanary.install(this);
        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        TXLiveBase.getInstance().setLicence(this, "http://license.vod2.myqcloud.com/license/v1/701947fd803f8c5f878a2c6fd8086eca/TXLiveSDK.licence", "430ab98fe8f19f8b69ae47b039407d33");
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        rid = JPushInterface.getRegistrationID(getApplicationContext());
    }

    private void initBle() {
        BleManager.getInstance()
                .enableLog(true)
                .setConnectOverTime(50000)
                .setOperateTimeout(5000).init(this);

    }

    private void initMI() {
        instance = this;
        if (shouldInit()) {
            DeviceUtils.init(this);
            masterRequest = new MasterRequest(this);
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
            String mainProcessName = getPackageName();
            int myPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static MasterRequest getMasterRequest() {
        return masterRequest;
    }

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }


    public static Context getContext() {
        return mContext;
    }

}
