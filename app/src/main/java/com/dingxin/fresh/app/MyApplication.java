package com.dingxin.fresh.app;

import android.app.ActivityManager;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.R;
import com.dingxin.fresh.e.LoginBean;
import com.dingxin.fresh.utils.PollingUtil;
import com.example.jjhome.network.DeviceUtils;
import com.example.jjhome.network.TestEvent;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.fanjun.keeplive.config.KeepLiveService;
import com.jjhome.master.http.MasterRequest;
import com.tencent.rtmp.TXLiveBase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
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
    private PollingUtil pollingUtil;
    private Runnable runnable;
    public static MasterRequest masterRequest;

    //public static final String APP_ID = "a27xxxxxxxxxxxxxxxxxxxxxxxx22";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        KLog.init(false);
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
        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        TXLiveBase.getInstance().setLicence(this, "http://license.vod2.myqcloud.com/license/v1/701947fd803f8c5f878a2c6fd8086eca/TXLiveSDK.licence", "430ab98fe8f19f8b69ae47b039407d33");
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        rid = JPushInterface.getRegistrationID(getApplicationContext());
        ForegroundNotification foregroundNotification = new ForegroundNotification("鲜到家", "商户平台", R.mipmap.ic_launcher,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {

                    }
                });
        KeepLive.startWork(this, KeepLive.RunMode.ENERGY, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                        //TODO 长链接(备选方案)

                        pollingUtil = new PollingUtil(new Handler(getMainLooper()));
                        runnable = new Runnable() {
                            @Override
                            public void run() {

                            }
                        };
                        pollingUtil.startPolling(runnable, 5000, true);
                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        pollingUtil.endPolling(runnable);
                        pollingUtil = null;
                        runnable = null;
                    }
                }
        );
    }

    private void initBle() {
        BleManager.getInstance()
                .enableLog(true)
                .setConnectOverTime(30000)
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
