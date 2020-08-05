package com.dingxin.fresh.vm;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.GreensEntity;

import com.dingxin.fresh.fragment.ModifyGreensInfoFragment;
import com.dingxin.fresh.utils.RetrofitClient;


import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;


public class GoodsListItemViewModel extends ItemViewModel<GoodsListViewModel> {
    public static final String TAG = "com.dingxin.fresh.vm.GoodsListItemViewModel";
    public ObservableField<GreensEntity> greensEntityObservableField = new ObservableField<>();
    public Drawable sale_up, sale_down;
    public String sale_up_text = "上架", sale_down_text = "下架";

    public BindingCommand specs_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            specs();
        }
    });
    public BindingCommand sale_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            sale();
        }
    });
    public BindingCommand delete_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            delete();
        }
    });
    public BindingCommand modify_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            GreensEntity entity = greensEntityObservableField.get();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", entity);
            viewModel.startContainerActivity(ModifyGreensInfoFragment.class.getCanonicalName(), bundle);
        }
    });

    public GoodsListItemViewModel(@NonNull GoodsListViewModel viewModel, GreensEntity entity) {
        super(viewModel);
        sale_up = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.grounding);
        sale_down = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.undercarriage);
        greensEntityObservableField.set(entity);
    }

    public void specs() {
        viewModel.specs(greensEntityObservableField.get().getId());
    }

    public void sale() {
        RetrofitClient.getInstance().create(ApiService.class).sale_edit(String.valueOf(greensEntityObservableField.get().getId()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        viewModel.showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<CommonEntity>() {
                    @Override
                    public void onComplete() {
                        viewModel.dismissDialog();
                    }

                    @Override
                    public void onResult(CommonEntity entity) {
                        viewModel.dismissDialog();
                        greensEntityObservableField.get().setOn_sale(entity.getOn_sale());
                        greensEntityObservableField.notifyChange();
                    }

                    @Override
                    public void onError(Throwable e) {
                        viewModel.dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void delete() {
        viewModel.deleteItemLiveData.setValue(GoodsListItemViewModel.this);
        RetrofitClient.getInstance().create(ApiService.class).greens_del(String.valueOf(greensEntityObservableField.get().getId()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        viewModel.showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<Object>() {
                    @Override
                    public void onComplete() {
                        viewModel.dismissDialog();
                    }

                    @Override
                    public void onResult(Object o) {
                        viewModel.dismissDialog();
                        viewModel.deleteItem(GoodsListItemViewModel.this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        viewModel.dismissDialog();
                        super.onError(e);
                    }
                });
    }
}
