package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.AccountInfoEntity;
import com.dingxin.fresh.fragment.CashFragment;
import com.dingxin.fresh.fragment.DetailsFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import java.util.ArrayList;
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

public class BalanceViewModel extends BaseViewModel {

    public ObservableField<AccountInfoEntity> entityObservableField = new ObservableField<>();
    public ObservableField<Integer> count = new ObservableField<>(0);
    public ObservableField<String> amount = new ObservableField<>("");
    public ObservableField<String> weight = new ObservableField<>("");
    public ObservableField<Integer> after_service = new ObservableField<>(0);
    public SingleLiveEvent dataEvent = new SingleLiveEvent();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand detail_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(DetailsFragment.class.getCanonicalName());
        }
    });
    public BindingCommand cash_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(CashFragment.class.getCanonicalName());
        }
    });

    public BalanceViewModel(@NonNull Application application) {
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
                        entityObservableField.set(accountInfoEntity);
                        dataEvent.call();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }
}
