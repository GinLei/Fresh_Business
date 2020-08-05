package com.dingxin.fresh.utils;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.dingxin.fresh.app.MyApplication;
import com.dingxin.fresh.e.LoginEntity;
import com.google.gson.Gson;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import me.goldze.mvvmhabit.utils.SPUtils;

public class AppUtils {

    public static boolean isBindScale() {
        return !TextUtils.isEmpty(new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getScale());
    }

    public static boolean isBindPrint() {
        return !TextUtils.isEmpty(new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getTicket());
    }


    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
