//package com.dingxin.fresh.s;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Binder;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.text.TextUtils;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import com.dingxin.fresh.R;
//import com.dingxin.fresh.e.ReceiveEntity;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.net.URI;
//import java.util.List;
//
//import me.goldze.mvvmhabit.utils.ToastUtils;
//
//public class WebSocketClientService extends Service {
//    private static final String uri = "wss://7chezhibo.com/wss";
//    public WebSocketClient client = null;
//    private WebSocketClientBinder mBinder = new WebSocketClientBinder();
//    public static final int NOTIFICATION_ID = 0x11;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        //return mBinder;
//        return null;
//    }
//
//    public class WebSocketClientBinder extends Binder {
//        public WebSocketClientService getService() {
//            return WebSocketClientService.this;
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        return START_STICKY;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            startForeground(NOTIFICATION_ID, new Notification());
//        } else {
//            //18以上
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.mipmap.comingsoon);
//            startForeground(NOTIFICATION_ID, builder.build());
//            startService(new Intent(this, InnerService.class));
//        }
//        mHandler.post(heartBeatRunnable);
//    }
//
//    private static final long HEART_BEAT_RATE = 1 * 1000;//每隔1秒进行一次对长连接的心跳检测
//    private Handler mHandler = new Handler();
//    private Runnable heartBeatRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (client != null) {
//                if (client.isClosed()) {
//                    reconnectWs();
//                }
//            } else {
//                //如果client已为空，重新初始化websocket
//                initSocketClient();
//            }
//            //定时对长连接进行心跳检测
//            mHandler.postDelayed(this, HEART_BEAT_RATE);
//        }
//    };
//
//    /**
//     * 开启重连
//     */
//    private void reconnectWs() {
//        mHandler.removeCallbacks(heartBeatRunnable);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    //重连
//                    client.reconnectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private void initSocketClient() {
//        client = new WebSocketClient(URI.create(uri)) {
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                Log.v("TAG", "建立连接");
//            }
//
//            @Override
//            public void onMessage(String message) {
//                if (!TextUtils.equals("text", message)) {
//                    List<ReceiveEntity> entityList = new Gson().fromJson(message, new TypeToken<List<ReceiveEntity>>() {
//                    }.getType());
//                    Log.v("接受到的数据", new Gson().toJson(entityList));
//                    ReceiveEntity entity = entityList.get(0);
//                    String content = entity.getContent();
//                    if (!TextUtils.equals(content, "apk_text")) {
//                        Intent intent = new Intent();
//                        intent.setAction(getPackageName());
//                        intent.putExtra("message", content);
//                        sendBroadcast(intent);
//                    }
//                }
//            }
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {
//                Log.v("TAG", "断开连接");
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                Log.v("TAG", "发生异常" + ex.toString());
//            }
//        };
//        new Thread() {
//            public void run() {
//                try {
//                    client.connectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private void closeConnect() {
//        try {
//            if (null != client) {
//                client.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            client = null;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        closeConnect();
//        client = null;
//        Intent intent = new Intent();
//        intent.setAction("com.dingxin.fresh.s.WebSocketClientService");
//        sendBroadcast(intent);
//    }
//
//    public class InnerService extends Service {
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            return null;
//        }
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.mipmap.ic_launcher);
//            startForeground(NOTIFICATION_ID, builder.build());
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    stopForeground(true);
//                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    manager.cancel(NOTIFICATION_ID);
//                    stopSelf();
//                }
//            }, 100);
//        }
//    }
//}
