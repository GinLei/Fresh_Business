package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
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

public class ClassOneViewModel extends BaseViewModel {
    public static final String TAG = "com.dingxin.fresh.vm.ClassOneViewModel";
    public ObservableField<String> target_id = new ObservableField<>("");
    public SingleLiveEvent<List<CommonEntity>> target_data = new SingleLiveEvent<>();
    public ObservableField<CommonEntity> target_submit = new ObservableField<>();
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CommonEntity entity = target_submit.get();
            if (entity != null) {
                Messenger.getDefault().send(entity, TAG);
                finish();
            } else {
                ToastUtils.showShort("请选择商品分类");
            }
        }
    });
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public ClassOneViewModel(@NonNull Application application) {
        super(application);
    }

    public void one_class_name() {
        RetrofitClient.getInstance().create(ApiService.class).level_class(String.valueOf(target_id.get()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<CommonEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<CommonEntity> entity) {
                        target_data.setValue(entity);
                        target_data.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
