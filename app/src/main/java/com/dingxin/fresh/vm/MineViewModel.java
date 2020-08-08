package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.AccountInfoEntity;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.fragment.BalanceFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;

public class MineViewModel extends BaseViewModel {
    public ObservableField<AccountInfoEntity> entity = new ObservableField<>();
    public SingleLiveEvent refresh_event = new SingleLiveEvent();
    public BindingCommand to_balance = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(BalanceFragment.class.getCanonicalName());
        }
    });

    public MineViewModel(@NonNull Application application) {
        super(application);
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
                        entity.get().setIs_show(m_entity.getIs_show());
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
