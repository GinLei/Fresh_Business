package com.dingxin.fresh.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnectThread extends Thread {

    private static final UUID BluetoothUUID = UUID.fromString("00001102-0000-1000-8000-00805f9b34fb");
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    private BluetoothConnectCallback connectCallback;

    public BluetoothConnectThread(BluetoothDevice device,
                                  BluetoothConnectCallback callback) {
        try {
            bluetoothDevice = device;
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(BluetoothUUID);
            connectCallback = callback;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BluetoothConnectThread(BluetoothDevice device, UUID Bluetoothuuid,
                                  BluetoothConnectCallback callback) {
        try {
            bluetoothDevice = device;
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(Bluetoothuuid);
            connectCallback = callback;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        connect();
    }

    public void connect() {
        try {
            bluetoothSocket.connect();
        } catch (Exception connectException) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectCommand(byte[] command) {
        try {
            outputStream = bluetoothSocket.getOutputStream();
            outputStream.write(command);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printText(String text) {
        try {
            outputStream = bluetoothSocket.getOutputStream();
            byte[] data = text.getBytes("gbk");
            outputStream.write(data, 0, data.length);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
