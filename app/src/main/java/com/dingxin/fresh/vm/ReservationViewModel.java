package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.databinding.FragmentReservationBinding;
import com.dingxin.fresh.e.ReservationEntity;
import com.dingxin.fresh.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ReservationViewModel extends BaseViewModel {
    public ObservableField<String> day = new ObservableField<>("");
    public SingleLiveEvent data_event = new SingleLiveEvent<>();
    public ObservableField<ReservationEntity> reservation_entity = new ObservableField<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public ReservationViewModel(@NonNull Application application) {
        super(application);
    }

    public void get_apk_schedule_goodslist() {
        RetrofitClient.getInstance().create(ApiService.class).get_apk_schedule_goodslist(day.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<ReservationEntity>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(ReservationEntity entity) {
                        dismissDialog();
                        reservation_entity.set(entity);
                        data_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }
                });
    }
}
