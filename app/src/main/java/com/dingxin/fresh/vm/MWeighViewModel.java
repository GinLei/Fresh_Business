package com.dingxin.fresh.vm;

import android.app.Application;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.WeighFinalEntity;
import com.dingxin.fresh.e.WeightEntity;
import com.dingxin.fresh.utils.RetrofitClient;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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

public class MWeighViewModel extends BaseViewModel {
    public int startPosition = 0;
    public int pageSize = 20;
    private BleDevice bleDevice;
    private String scale;
    private String service_uuid, characteristic_uuid;
    public ObservableField<Boolean> lock = new ObservableField<>(true);
    public ObservableField<String> goods_id = new ObservableField<>();
    public ObservableField<String> order_id = new ObservableField<>();
    public ObservableField<String> spec_id = new ObservableField<>();
    public SingleLiveEvent<List<WeightEntity>> data_event = new SingleLiveEvent();
    public SingleLiveEvent<Integer> cancel_event = new SingleLiveEvent();
    public SingleLiveEvent<String> phone_event = new SingleLiveEvent<>();
    public SingleLiveEvent commit_weight_event = new SingleLiveEvent();
    public ObservableField<String> finalWeigh = new ObservableField<>();
    public SingleLiveEvent<List<String>> weight_event = new SingleLiveEvent<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public MWeighViewModel(@NonNull Application application) {
        super(application);
        scale = new Gson().fromJson(SPUtils.getInstance().getString("user_info"), LoginEntity.class).getScale();
    }

    public void cancelGoods() {
        RetrofitClient.getInstance().create(ApiService.class).cancel_order_goods(goods_id.get(), order_id.get(), spec_id.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<CommonEntity>() {
                    @Override
                    public void onResult(CommonEntity entity) {
                        cancel_event.setValue(entity.getCancel_weight_status());
                        cancel_event.call();
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void RefreshWeightData() {
        RetrofitClient.getInstance().create(ApiService.class).RequestWeightData(startPosition = 0, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<WeightEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<WeightEntity> entity) {
                        dismissDialog();
                        data_event.setValue(entity);
                        data_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void LoadMoreWeightData() {
        RetrofitClient.getInstance().create(ApiService.class).RequestWeightData(startPosition += pageSize, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<WeightEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<WeightEntity> entity) {
                        dismissDialog();
                        data_event.setValue(entity);
                        data_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void callPhone(String phone) {
        phone_event.setValue(phone);
        phone_event.call();
    }

    public void commit_weight() {
        RetrofitClient.getInstance().create(ApiService.class).commit_weight(order_id.get())
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
                        commit_weight_event.call();
                        dismissDialog();
                        ToastUtils.showShort("称重提交成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                        dismissDialog();
                    }
                });
    }

    public void weight() {
        if (!BleManager.getInstance().isBlueEnable()) {
            ToastUtils.showShort("请打开蓝牙");
            return;
        }

        BleManager.getInstance().connect(scale, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                showDialog("正在称重");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                dismissDialog();
                ToastUtils.showShort("称重失败");
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                MWeighViewModel.this.bleDevice = bleDevice;
                List<BluetoothGattService> services = gatt.getServices();
                BluetoothGattService service = services.get(2);
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                BluetoothGattCharacteristic characteristic = characteristics.get(1);
                BleManager.getInstance().notify(MWeighViewModel.this.bleDevice, service_uuid = service.getUuid().toString(), characteristic_uuid = characteristic.getUuid().toString(), bleNotifyCallback);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {

            }
        });
    }

    public void RequestWeight(String weights) {
        RetrofitClient.getInstance().create(ApiService.class).RequestWeight(goods_id.get(), weights, order_id.get(), spec_id.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<WeighFinalEntity>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(WeighFinalEntity entity) {
                        dismissDialog();
                        List<String> list = new ArrayList<>();
                        list.add(entity.getWeight());
                        list.add(entity.getMessage());
                        weight_event.setValue(list);
                        weight_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }
                });
    }

    BleNotifyCallback bleNotifyCallback = new BleNotifyCallback() {
        @Override
        public void onNotifySuccess() {
        }

        @Override
        public void onNotifyFailure(BleException exception) {

        }

        @Override
        public void onCharacteristicChanged(byte[] data) {
            if (data.length == 20) {
                try {
                    if (lock.get()) {
                        BleManager.getInstance().stopNotify(bleDevice, service_uuid, characteristic_uuid);
                        lock.set(false);
                        finalWeigh.set(new String(data, "UTF-8"));
                        RequestWeight(finalWeigh.get());
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
