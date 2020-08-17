package com.dingxin.fresh.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.J.JPushEntity;
import com.dingxin.fresh.R;
import com.dingxin.fresh.activity.TabBarActivity;
import com.dingxin.fresh.e.LoginBean;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.utils.CrashProtectManager;
import com.example.jjhome.network.DeviceUtils;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.fanjun.keeplive.config.KeepLiveService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjhome.master.http.MasterRequest;
import com.tencent.rtmp.TXLiveBase;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends BaseApplication implements TextToSpeech.OnInitListener {
    public static String rid = "";
    public static MyApplication instance;
    public LoginBean loginBean;
    private static Context mContext;
    private Runnable runnable;
    public static MasterRequest masterRequest;
    private WebSocketClient client;
    private TextToSpeech tts;
    //public static final String APP_ID = "a27xxxxxxxxxxxxxxxxxxxxxxxx22";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashProtectManager.getInstance(this).init();
        tts = new TextToSpeech(getApplicationContext(), this);
        tts.setPitch(0.5f);
        tts.setSpeechRate(1.0f);
        initBle();
        initSocketClient();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        KLog.init(false);
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
//                .enabled(true) //是否启动全局异常捕获
//                .showErrorDetails(true) //是否显示错误详细信息
//                .showRestartButton(true) //是否显示重启按钮
//                .trackActivities(true) //是否跟踪Activity
//                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//                .errorDrawable(R.mipmap.ic_launcher) //错误图标
//                .restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
//                .apply();
        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        TXLiveBase.getInstance().setLicence(this, "http://license.vod2.myqcloud.com/license/v1/aa5c4727566cba884d271dad671bd3ad/TXLiveSDK.licence", "b3953da910780ed02ddc41a68ba29e80");
        //JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        //JPushInterface.init(this);// 初始化 JPush
        //rid = JPushInterface.getRegistrationID(getApplicationContext());
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
                        mHandler.post(heartBeatRunnable);
                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        if (client != null) {
                            if (client.isOpen()) {
                                try {
                                    client.closeBlocking();
                                    client = null;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (tts != null) {
                            tts.shutdown();
                            tts.stop();
                            tts = null;
                        }
                        mHandler.removeCallbacks(heartBeatRunnable);
                    }
                }
        );

    }

    private void initBle() {
        BleManager.getInstance()
                .enableLog(false)
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


    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测,考虑到网络切换的情况心跳
    private MyHandler mHandler = new MyHandler(this);
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                } else {
                    client.send("KeepAlive");
                }
            } else {
                //如果client已为空，重新初始化websocket
                initSocketClient();
            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private void initSocketClient() {
        try {
            if (client != null) {
                if (client.isOpen()) {
                    client.closeBlocking();
                    client = null;
                }
            }
            client = new WebSocketClient(URI.create("wss://7chezhibo.com/wss")) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                }

                @Override
                public void onMessage(String message) {
                    try {
                        String user_info = SPUtils.getInstance().getString("user_info");
                        if (!TextUtils.isEmpty(user_info)) {
                            LoginEntity entity = new Gson().fromJson(user_info, LoginEntity.class);
                            ArrayList<JPushEntity> list = new Gson().fromJson(message, new TypeToken<List<JPushEntity>>() {
                            }.getType());
                            for (JPushEntity pushEntity : list) {
                                if (TextUtils.equals(pushEntity.getUid(), String.valueOf(entity.getId()))) {
                                    tts.speak(pushEntity.getContent(), TextToSpeech.QUEUE_ADD, null, null);
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.CHINESE);
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MyApplication> myApplication;

        public MyHandler(MyApplication context) {
            myApplication = new WeakReference<MyApplication>(context);
        }
    }
}
