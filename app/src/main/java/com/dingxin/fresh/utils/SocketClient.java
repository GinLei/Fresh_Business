//package com.dingxin.fresh.utils;
//
//import android.content.Context;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.util.Log;
//
//import com.dingxin.fresh.e.ReceiveEntity;
//import com.dingxin.fresh.e.SendEntity;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft_6455;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//
//import me.goldze.mvvmhabit.utils.StringUtils;
//
//public class SocketClient extends WebSocketClient {
//    private MediaPlayer mediaPlayer=null;
//    private static final String TAG = "SocketClient";
//    private Context context;
//    private static volatile SocketClient client = null;
//
//    private SocketClient(URI uri,Context context) {
//        super(uri,new Draft_6455());
//        this.context=context;
//    }
//
//    public static SocketClient getInstance(Context context,URI uri) {
//        if (client == null) {
//            synchronized (SocketClient.class) {
//                if (client == null) {
//                    client = new SocketClient(uri,context);
//                }
//            }
//        }
//        return client;
//    }
//
//
//    @Override
//    public void onOpen(ServerHandshake serverHandshake) {
//        send(new Gson().toJson(new SendEntity(3)));
//    }
//
//    @Override
//    public void onMessage(String message) {
//        send(new Gson().toJson(new SendEntity(3)));
//        if(message!="text"){
//            ArrayList<ReceiveEntity> list = new Gson().fromJson(message, new TypeToken<ArrayList<ReceiveEntity>>() {
//            }.getType());
//            ReceiveEntity entity = list.get(0);
//            Log.v("TAG",entity.getContent());
//
//            if(!StringUtils.equals(entity.getContent(),"apk_text")){
////                Log.v("TAG2", String.valueOf(entity.getContent()!="apk_text"?true:false));
//                try {
//                    if(mediaPlayer==null){
//                        mediaPlayer = new MediaPlayer();
//                    }
//                    if(!mediaPlayer.isPlaying()){
//                    Uri uri = Uri.parse(entity.getContent());
//                    Log.v("mp3",uri.toString());
//                    mediaPlayer.setDataSource(context, uri);
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.start(); }
//                } catch (IOException e) {
//                    Log.v("进来了",e.getMessage());
//                }
//            }}
//      }
//
//    @Override
//    public void onClose(int code, String reason, boolean remote) {
//
//    }
//
//    @Override
//    public void onError(Exception ex) {
//        Log.v("错误",ex.getMessage());
//    }
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
//}
