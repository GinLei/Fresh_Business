package com.dingxin.fresh.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class UnitViewModel extends BaseViewModel {
    public ObservableField<String> target_id = new ObservableField<>("");
    public ItemBinding<UnitItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_unit);
    public ObservableList<UnitItemViewModel> unitItemViewModels = new ObservableArrayList<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
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
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<UnitEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<UnitEntity> entitys) {
                        for (UnitEntity entity : entitys) {
                            UnitItemViewModel itemViewModel = new UnitItemViewModel(UnitViewModel.this, entity);
                            unitItemViewModels.add(itemViewModel);
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }
                });
    }
}
