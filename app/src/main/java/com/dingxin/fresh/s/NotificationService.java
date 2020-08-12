package com.dingxin.fresh.s;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import com.dingxin.fresh.utils.AudioFocusChangeUtil;

import java.util.Locale;

public class NotificationService extends Service implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(getApplicationContext(), this);
        tts.setPitch(0.5f);
        tts.setSpeechRate(1.0f);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.CHINESE);
        }
    }

    public class MyBinder extends Binder {
        public void play(String content) {
            tts.speak(content, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.shutdown();
            tts.stop();
            tts = null;
        }
    }
}