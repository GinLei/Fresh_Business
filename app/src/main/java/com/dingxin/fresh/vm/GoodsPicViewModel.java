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
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class GoodsPicViewModel extends BaseViewModel {
    public ObservableField<String> greens_cate_id = new ObservableField<>("");
    public ItemBinding<GoodsPicItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_goods_pic);
    public ObservableList<GoodsPicItemViewModel> listItemViewModels = new ObservableArrayList<>();

    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public GoodsPicViewModel(@NonNull Application application) {
        super(application);
    }

    public void pic_list() {
        RetrofitClient.getInstance().create(ApiService.class).pic_list(greens_cate_id.get())
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
                    public void onResult(List<CommonEntity> entities) {
                        for (CommonEntity entity : entities) {
                            GoodsPicItemViewModel itemViewModel = new GoodsPicItemViewModel(GoodsPicViewModel.this, entity);
                            listItemViewModels.add(itemViewModel);
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
