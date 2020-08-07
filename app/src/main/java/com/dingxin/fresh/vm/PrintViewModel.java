package com.dingxin.fresh.vm;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.p.PrinterFormat;
import com.dingxin.fresh.p.PrinterService;
import com.dingxin.fresh.r.TestActivity;
import com.dingxin.fresh.utils.BluetoothConnectCallback;
import com.dingxin.fresh.utils.ConnectThread;
import com.dingxin.fresh.utils.PrintUtil;
import com.dingxin.fresh.utils.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PrintViewModel extends BaseViewModel {
    public int startPosition = 0;
    public int pageSize = 20;
    public ConnectThread thread;
    public ObservableField<BluetoothDevice> device = new ObservableField<>();
    public ObservableField<PrintEntity> entity = new ObservableField<>();
    public SingleLiveEvent print_event = new SingleLiveEvent();
    public BluetoothAdapter bluetoothAdapter;
    public SingleLiveEvent refreshEvent = new SingleLiveEvent();
    public SingleLiveEvent<String> phoneEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<PrintEntity>> dataEvent = new SingleLiveEvent<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public PrintViewModel(@NonNull Application application) {
        super(application);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void RefreshPrint() {
        RetrofitClient.getInstance().create(ApiService.class).get_print_list(startPosition = 0, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<PrintEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<PrintEntity> entity) {
                        dismissDialog();
                        dataEvent.setValue(entity);
                        dataEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void LoadMorePrint() {
        RetrofitClient.getInstance().create(ApiService.class).get_print_list(startPosition += pageSize, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<PrintEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<PrintEntity> entity) {
                        dismissDialog();
                        dataEvent.setValue(entity);
                        dataEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void confirm_print() {
        RetrofitClient.getInstance().create(ApiService.class).print(String.valueOf(entity.get().getOrder_id()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<PrintEntity>>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onResult(List<PrintEntity> entity) {
                        refreshEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void callPhone(String phone) {
        phoneEvent.setValue(phone);
        phoneEvent.call();
    }

    public void print() {
        print_event.call();
    }
}
