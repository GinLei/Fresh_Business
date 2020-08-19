package com.dingxin.fresh.vm;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.AccountInfoEntity;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.fragment.BalanceFragment;
import com.dingxin.fresh.fragment.ReservationFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;

public class MineViewModel extends BaseViewModel {
    public ObservableField<AccountInfoEntity> entity = new ObservableField<>();
    public SingleLiveEvent refresh_event = new SingleLiveEvent();
    public SingleLiveEvent<Boolean> switch_event = new SingleLiveEvent<>();
    public SingleLiveEvent to_reservation_event = new SingleLiveEvent();
    public BindingCommand change_isShow = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            change_isShow();
        }
    });
    public BindingCommand to_balance = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(BalanceFragment.class.getCanonicalName());
        }
    });

    public BindingCommand to_reservation = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            to_reservation_event.call();
        }
    });


    public MineViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, CashViewModel.class.getSimpleName(), new BindingAction() {
            @Override
            public void call() {
                getAccountInfo();
            }
        });
    }

    public void getAccountInfo() {
        RetrofitClient.getInstance().create(ApiService.class).RequestPosData()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<AccountInfoEntity>() {
                    @Override
                    public void onResult(AccountInfoEntity accountInfoEntity) {
                        entity.set(accountInfoEntity);
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    public void RefreshAccountInfo() {
        RetrofitClient.getInstance().create(ApiService.class).RequestPosData()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<AccountInfoEntity>() {
                    @Override
                    public void onResult(AccountInfoEntity accountInfoEntity) {
                        entity.set(accountInfoEntity);
                        refresh_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refresh_event.call();
                    }

                    @Override
                    public void onComplete() {
                        refresh_event.call();
                    }
                });
    }

    public void change_isShow() {
        RetrofitClient.getInstance().create(ApiService.class).change_isShow()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<CommonEntity>() {
                    @Override
                    public void onResult(CommonEntity m_entity) {
                        switch_event.setValue(m_entity.getIs_show());
                        switch_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
