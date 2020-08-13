package com.dingxin.fresh.vm;

import android.app.Application;
import android.text.TextUtils;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.activity.TabBarActivity;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.fragment.InsideFragment;
import com.dingxin.fresh.utils.AppUtils;
import com.dingxin.fresh.utils.RetrofitClient;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
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

public class LoginViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public SingleLiveEvent yzEvent = new SingleLiveEvent();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });
    public BindingCommand InsideOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(InsideFragment.class.getCanonicalName());
        }
    });
    public BindingCommand yzOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getYz();
        }
    });

    public void login() {

        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入验证码！");
            return;
        }
        RetrofitClient.getInstance().create(ApiService.class).Login(userName.get(), password.get(), AppUtils.getMacAddress())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<LoginEntity>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(LoginEntity entity) {
                        dismissDialog();
                        SPUtils.getInstance().put("user_info", new Gson().toJson(entity));
                        SPUtils.getInstance().put("token", entity.getToken());
                        //JPushInterface.setAlias(getApplication(), 0, userName.get());
                        //JPushInterface.setPowerSaveMode(getApplication(), true);
                        startActivity(TabBarActivity.class);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void getYz() {

        RetrofitClient.getInstance().create(ApiService.class).getYz(userName.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<Object>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onResult(Object o) {
                        ToastUtils.showShort("验证码已发送");
                        yzEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
