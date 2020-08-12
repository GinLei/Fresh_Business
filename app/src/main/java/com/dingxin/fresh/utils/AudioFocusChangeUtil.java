package com.dingxin.fresh.utils;

import android.media.AudioManager;

import com.dingxin.fresh.app.MyApplication;

import static android.content.Context.AUDIO_SERVICE;

public class AudioFocusChangeUtil {

    private static AudioFocusChangeUtil instance;

    private AudioManager audioManager;

    public static AudioFocusChangeUtil getInstance() {
        if (instance == null) {
            instance = new AudioFocusChangeUtil();
        }
        return instance;
    }

    private AudioFocusChangeUtil() {
        audioManager = (AudioManager) MyApplication.getInstance().getSystemService(AUDIO_SERVICE);
    }

    public boolean requestFocus() {
        // Request audio focus for playback
        int result = audioManager.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public boolean abandonAudioFocus() {
        int result = audioManager.abandonAudioFocus(afChangeListener);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // mAm.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                audioManager.abandonAudioFocus(afChangeListener);
                // Stop playback
            }
        }
    };
}