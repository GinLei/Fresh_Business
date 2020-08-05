package com.dingxin.fresh.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.clj.fastble.data.BleDevice;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import me.goldze.mvvmhabit.utils.ToastUtils;

public class ConnectThread extends Thread {
    private BluetoothSocket mmSocket;
    private static final UUID BluetoothUUID = UUID.fromString("00001102-0000-1000-8000-00805f9b34fb");
    private BluetoothConnectCallback callback;
    private OutputStream outputStream;
    private BluetoothDevice device;

    public ConnectThread(BluetoothDevice m_device, BluetoothConnectCallback callback) {
        this.callback = callback;
        device = m_device;
        BluetoothSocket tmp = null;
        try {
            Method m = device.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
            tmp = (BluetoothSocket) m.invoke(device, Integer.valueOf(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mmSocket.connect();
                    callback.connectSuccess(mmSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        mmSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectCommand(byte[] command) {
        try {
            outputStream = mmSocket.getOutputStream();
            outputStream.write(command);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printText(String text) {
        try {
            outputStream = mmSocket.getOutputStream();
            byte[] data = text.getBytes("gbk");
            outputStream.write(data, 0, data.length);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}