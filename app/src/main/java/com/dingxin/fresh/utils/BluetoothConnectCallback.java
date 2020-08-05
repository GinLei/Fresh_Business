package com.dingxin.fresh.utils;

import android.bluetooth.BluetoothSocket;

public interface BluetoothConnectCallback {

    void connectSuccess(BluetoothSocket socket);

    void connectFailed(String errorMsg);

    void connectCancel();
}
