package com.dingxin.fresh.r;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dingxin.fresh.R;
import com.dingxin.fresh.p.PrinterFormat;
import com.dingxin.fresh.p.PrinterService;
import com.dingxin.fresh.utils.BluetoothConnectCallback;
import com.dingxin.fresh.utils.ConnectThread;
import com.dingxin.fresh.utils.PrintUtil;

import me.goldze.mvvmhabit.utils.ToastUtils;

public class TestActivity extends AppCompatActivity {
    private Button button;
    private BluetoothAdapter adapter;
    private ConnectThread thread;
    private Object object = new Object();
    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        registerReceiver(mPrinterReceiver, new IntentFilter(PrinterService.FILTER_ACTION));
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        filter.addAction(BluetoothDevice.ACTION_FOUND);
//        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
//        registerReceiver(mReceiver, filter);
//        adapter = BluetoothAdapter.getDefaultAdapter();
//        button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.startDiscovery();
//            }
//        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("开始打印");
                print();
            }
        });
    }

    public void print() {
        // 构建 Intent 数据
        Intent intent = new Intent(TestActivity.this, PrinterService.class);
        // 打印模式 PrinterService.MODE.NORMAL 正常打印模式（默认） PrinterService.MODE.TEST 测试打印机
        intent.putExtra(PrinterService.PRINT_MODEL, PrinterService.MODE.NORMAL);
        // 蓝牙地址必须传(设置打印机的时候 存储到本地，如果没有 提示去设置打印机)
        intent.putExtra(PrinterService.BLUETOOTH_ADDRESS, "04:7F:0E:05:48:B9");
        // Test 模式可以不传要打印的数据 * 正常模式必传。 （打印数据格式为 byte[]） 数据格式参考 PrinterFormat
        intent.putExtra(PrinterService.PRINT_DATA, PrinterFormat.getPrintData(null));
        // 启动服务 自动打印。
        startService(intent);
    }

    //    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {//开始扫描蓝牙时候的广播
//
//            }
//            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {   //这个广播是扫描结束，蓝牙发送的广播
//
//            }
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                //当扫描到蓝牙设备时，会发送这个广播
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Log.v("设备地址", device.getAddress());
//                if (TextUtils.equals(device.getAddress(), "04:7F:0E:05:48:B9")) {
//                    adapter.cancelDiscovery();
//                    thread = new ConnectThread(device, connectCallback);
//                    thread.start();
//                }
//            }
//            if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {  //蓝牙扫描状态(SCAN_MODE)发生改变
//
//            }
//            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {  //绑定时候的广播
//
//            }
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(mReceiver);
//    }
//
//    public BluetoothConnectCallback connectCallback = new BluetoothConnectCallback() {
//        @Override
//        public void connectSuccess(BluetoothSocket bluetoothSocket) {
//            synchronized (object) {
//                thread.selectCommand(PrintUtil.ALIGN_CENTER);
//                thread.selectCommand(PrintUtil.DOUBLE_HEIGHT_WIDTH);
//                thread.selectCommand(PrintUtil.BOLD);
//                thread.printText("鲜到家平台订单");
//                thread.printText("\n");
//                thread.printText("在线支付(用户联)");
//                thread.printText("\n");
//                thread.cancel();
//            }
//        }
//
//        @Override
//        public void connectFailed(String errorMsg) {
//            ToastUtils.showShort(errorMsg);
//        }
//
//        @Override
//        public void connectCancel() {
//
//        }
//    };
    private BroadcastReceiver mPrinterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PrinterService.STATUS status =
                    (PrinterService.STATUS) intent.getSerializableExtra(PrinterService.FILTER_ACTION);
            String errorMsg = "";
            if (intent.hasExtra(PrinterService.ERROR_MESSAGE)) {
                errorMsg = intent.getStringExtra(PrinterService.ERROR_MESSAGE);
            }
            Log.i(TAG, "mPrinterReceiver printer status: " + status);
            switch (status) {
                case OPEN:// 打印机机盖开启。
                    Toast.makeText(context, "打印机机盖开启", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:// 打印机错误。
                    Toast.makeText(context, "打印机错误: " + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
                case NO_PAPER:// 打印机缺纸。
                    Toast.makeText(context, "打印机缺纸", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECTED:// 打印机连接成功。
                    Toast.makeText(context, "打印机连接成功", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECTING:// 正在连接打印机。
                    Toast.makeText(context, "正在连接打印机", Toast.LENGTH_SHORT).show();
                    break;
                case DISCONNECTED:// 打印机断开连接。
                    Toast.makeText(context, "打印机断开连接", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECT_FAIL:// 打印机连接失败。
                    Toast.makeText(context, "打印机连接失败：" + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
                case READY:// 打印机准备就绪。
                    Toast.makeText(context, "打印机准备就绪：" + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPrinterReceiver);
    }
}