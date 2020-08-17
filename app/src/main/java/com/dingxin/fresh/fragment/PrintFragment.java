package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.PrintOrderAdapter;
import com.dingxin.fresh.databinding.FragmentPrintBinding;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.p.PrinterFormat;
import com.dingxin.fresh.p.PrinterService;
import com.dingxin.fresh.vm.PrintViewModel;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.jessyan.autosize.internal.CustomAdapt;


public class PrintFragment extends BaseFragment<FragmentPrintBinding, PrintViewModel> {
    private PrintOrderAdapter adapter;
    private static final String TAG = PrintFragment.class.getSimpleName();


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().invasionNavigationBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_print;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        getActivity().registerReceiver(mPrinterReceiver, new IntentFilter(PrinterService.FILTER_ACTION));
        viewModel.RefreshPrint();
        binding.SmartRefreshLayout.setEnableLoadMore(false);
        binding.SmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.LoadMorePrint();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                adapter = null;
                viewModel.RefreshPrint();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.dataEvent.observe(this, new Observer<List<PrintEntity>>() {
            @Override
            public void onChanged(List<PrintEntity> printEntities) {
                if (printEntities != null) {
                    if (printEntities.size() == viewModel.pageSize) {
                        binding.SmartRefreshLayout.setEnableLoadMore(true);
                    } else {
                        binding.SmartRefreshLayout.setEnableLoadMore(false);
                    }
                    if (adapter == null) {
                        adapter = new PrintOrderAdapter(getActivity(), printEntities, viewModel);
                        binding.rv.setAdapter(adapter);
                        binding.SmartRefreshLayout.finishRefresh();
                    } else {
                        binding.SmartRefreshLayout.finishLoadMore();
                        adapter.getList().addAll(printEntities);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        viewModel.phoneEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    new RxPermissions(getActivity()).request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Uri data = Uri.parse("tel:" + s);
                                intent.setData(data);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
        viewModel.refreshEvent.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<PrintEntity> list = adapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    PrintEntity entity = list.get(i);
                    if (TextUtils.equals(String.valueOf(entity.getOrder_id()), String.valueOf(viewModel.entity.get().getOrder_id()))) {
                        entity.setPrint_status(1);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        });
        viewModel.print_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (!BleManager.getInstance().isBlueEnable()) {
                    ToastUtils.showShort("请开启蓝牙");
                    return;
                }
                BleManager.getInstance().scan(new BleScanCallback() {
                    @Override
                    public void onScanFinished(List<BleDevice> scanResultList) {
                    }

                    @Override
                    public void onScanStarted(boolean success) {
                        showDialog("正在打印");
                    }

                    @Override
                    public void onScanning(BleDevice bleDevice) {

                        if (TextUtils.equals(bleDevice.getMac(), new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getTicket())) {
                            BleManager.getInstance().cancelScan();
                            print();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mPrinterReceiver);
    }


    private BroadcastReceiver mPrinterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dismissDialog();
            PrinterService.STATUS status =
                    (PrinterService.STATUS) intent.getSerializableExtra(PrinterService.FILTER_ACTION);
            String errorMsg = "";
            if (intent.hasExtra(PrinterService.ERROR_MESSAGE)) {
                errorMsg = intent.getStringExtra(PrinterService.ERROR_MESSAGE);
            }
            switch (status) {
                case OPEN:// 打印机机盖开启。
                    Toast.makeText(context, "打印机机盖开启", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:// 打印机错误。
                    // ToastUtils.showShort(errorMsg);
                    if (TextUtils.equals(errorMsg, "0")) {
                        viewModel.confirm_print();
                    }
                    //Toast.makeText(context, "打印机错误: " + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
                case NO_PAPER:// 打印机缺纸。
                    Toast.makeText(context, "打印机缺纸", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECTED:// 打印机连接成功。
                    //Toast.makeText(context, "打印机连接成功", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECTING:// 正在连接打印机。
                    //Toast.makeText(context, "正在连接打印机", Toast.LENGTH_SHORT).show();
                    break;
                case DISCONNECTED:// 打印机断开连接。
                    Toast.makeText(context, "打印机断开连接", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECT_FAIL:// 打印机连接失败。
                    ToastUtils.showShort("打印机连接失败");
                    //Toast.makeText(context, "打印机连接失败：" + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
                case READY:// 打印机准备就绪。
                    Toast.makeText(context, "打印机准备就绪：" + errorMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void print() {
        // 构建 Intent 数据
        Intent intent = new Intent(getActivity(), PrinterService.class);
        // 打印模式 PrinterService.MODE.NORMAL 正常打印模式（默认） PrinterService.MODE.TEST 测试打印机
        intent.putExtra(PrinterService.PRINT_MODEL, PrinterService.MODE.NORMAL);
        // 蓝牙地址必须传(设置打印机的时候 存储到本地，如果没有 提示去设置打印机)
//        new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getTicket();
        intent.putExtra(PrinterService.BLUETOOTH_ADDRESS, new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getTicket());
        // Test 模式可以不传要打印的数据 * 正常模式必传。 （打印数据格式为 byte[]） 数据格式参考 PrinterFormat
        intent.putExtra(PrinterService.PRINT_DATA, PrinterFormat.getPrintData(viewModel.entity.get()));
        // 启动服务 自动打印。
        getActivity().startService(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BleManager.getInstance().destroy();
    }

}
