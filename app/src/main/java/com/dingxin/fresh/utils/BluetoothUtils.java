package com.dingxin.fresh.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BluetoothUtils {
    private BluetoothAdapter mBluetoothAdapter;
    private Activity activity;
    private BluetoothCallback bluetoothCallback;
    private static BluetoothUtils utils = null;

    public static BluetoothUtils getInstance() {
        if (utils == null) {
            synchronized (BluetoothUtils.class) {
                if (utils == null) {
                    utils = new BluetoothUtils();
                }
            }
        }
        return utils;
    }

    public Boolean isBluetoothEnable() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter.isEnabled();
    }

    public void init(Activity activity, BluetoothCallback callback) {
        this.activity = activity;
        this.bluetoothCallback = callback;
    }

    public void startDiscovery() {
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);
        intent.addAction(BluetoothDevice.ACTION_UUID);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(receiver, intent);
        startScanBluetooth();
    }

    public void cancelDiscovery() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private void startScanBluetooth() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    /**
     * 蓝牙广播接收
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (bluetoothCallback != null) {
                    bluetoothCallback.found(intent);
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                if (bluetoothCallback != null) {
                    bluetoothCallback.started("正在扫描");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (bluetoothCallback != null) {
                    bluetoothCallback.finished("蓝牙设备搜索完成");
                }
            }
        }
    };

    //获取已配对设备信息
    public List<BluetoothDevice> getPairedBluetoothDevices() {
        List deviceList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    public void openBluetooth() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.enable();
        }
    }

    //得到配对的设备列表，清除已配对的设备
    public void removePairDevice() {
        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : bondedDevices) {
                unpairDevice(device);
            }
        }

    }

    //反射来调用BluetoothDevice.removeBond取消设备的配对
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BluetoothCallback {
        void found(Intent intent);

        void started(String Msg);

        void finished(String Msg);
    }

    public void unRegisterReceiver() {
        if (receiver != null) {
            activity.unregisterReceiver(receiver);
        }
        bluetoothCallback = null;
    }
}
