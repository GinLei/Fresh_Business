package com.dingxin.fresh.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.UnitEntity;
import com.dingxin.fresh.utils.RetrofitClient;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class UnitViewModel extends BaseViewModel {
    public static final String TAG = "com.dingxin.fresh.vm.UnitViewModel";
    public ObservableField<String> target_id = new ObservableField<>("");
    public SingleLiveEvent<List<UnitEntity>> unit_data = new SingleLiveEvent<>();
    public ObservableField<UnitEntity> unit_submit = new ObservableField<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            UnitEntity unitEntity = unit_submit.get();
            if (unitEntity != null) {
                Messenger.getDefault().send(unit_submit.get(), TAG);
                finish();
            } else {
                ToastUtils.showShort("请选择商品单位");
            }
        }
    });

    public UnitViewModel(@NonNull Application application) {
        super(application);
    }

    public void unit_name() {
        RetrofitClient.getInstance().create(ApiService.class).unit(String.valueOf(target_id.get()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<UnitEntity>>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onResult(List<UnitEntity> entity) {
                        unit_data.setValue(entity);
                        unit_data.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
