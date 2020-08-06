package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ClassTwoViewModel extends BaseViewModel {
    public ObservableField<String> target_id = new ObservableField<>("");
    public ItemBinding<ClassTwoItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_tag_two);
    public ObservableList<ClassTwoItemViewModel> classTwoItemViewModels = new ObservableArrayList<>();


    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public ClassTwoViewModel(@NonNull Application application) {
        super(application);
    }

    public void two_class_name() {
        RetrofitClient.getInstance().create(ApiService.class).level_class(String.valueOf(target_id.get()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
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
                    public void onResult(List<CommonEntity> entitys) {
                        for (CommonEntity entity : entitys) {
                            ClassTwoItemViewModel itemViewModel = new ClassTwoItemViewModel(ClassTwoViewModel.this, entity);
                            classTwoItemViewModels.add(itemViewModel);
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
