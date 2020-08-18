package com.dingxin.fresh.vm;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.data.BleScanState;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.ScalesEntity;
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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ScalesViewModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> scale = new ObservableField<>();
    public ItemBinding<ScalesItemViewModel> scalesItemViewModelItemBinding = ItemBinding.of(BR.viewModel, R.layout.fragment_scales_rv_item);
    public ObservableList<ScalesItemViewModel> listObservableField = new ObservableArrayList<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public SingleLiveEvent bindEvent = new SingleLiveEvent();

    public ScalesViewModel(@NonNull Application application) {
        super(application);
    }

    public void Scan() {
        BleManager.getInstance().scan(bleScanCallback);
    }

    private BleScanCallback bleScanCallback = new BleScanCallback() {
        @Override
        public void onScanFinished(List<BleDevice> scanResultList) {
            BleManager.getInstance().destroy();
        }

        @Override
        public void onScanStarted(boolean success) {
            ToastUtils.showShort("正在扫描");
        }

        @Override
        public void onScanning(BleDevice bleDevice) {
            if (TextUtils.isEmpty(bleDevice.getName())) return;
            ScalesEntity entity = new ScalesEntity();
            entity.setScale_name(bleDevice.getName());
            entity.setScale_rssI("信号强度" + bleDevice.getRssi());
            entity.setScale_mac(bleDevice.getMac());
            ScalesItemViewModel itemViewModel = new ScalesItemViewModel(ScalesViewModel.this, entity);
            listObservableField.add(itemViewModel);
        }
    };

    public void bind_scale() {
        RetrofitClient.getInstance().create(ApiService.class).bind_scale(scale.get(), name.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<Object>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(Object o) {
                        LoginEntity entity = new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class);
                        entity.setScale(scale.get());
                        entity.setScale_name(name.get());
                        SPUtils.getInstance().put("user_info", new Gson().toJson(entity));
                        ToastUtils.showShort("绑定成功");
                        dismissDialog();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }
                });
    }

    @Override
    public void onDestroy() {
        BleScanState scanSate = BleManager.getInstance().getScanSate();
        if (scanSate == BleScanState.STATE_SCANNING) {
            BleManager.getInstance().cancelScan();
        }
        BleManager.getInstance().destroy();
    }
}
