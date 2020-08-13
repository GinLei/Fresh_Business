package com.dingxin.fresh.vm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.GreensEntity;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.fragment.GoodsListFragment;
import com.dingxin.fresh.fragment.UploadGoodsInfoFragment;
import com.dingxin.fresh.utils.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class GoodsListViewModel extends BaseViewModel {
    public SingleLiveEvent<GoodsListItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();
    public ItemBinding<GoodsListItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.fragment_goodslist_rv_item);
    public ObservableList<GoodsListItemViewModel> listItemViewModels = new ObservableArrayList<>();
    public ObservableField<List<SpecsEntity>> specs = new ObservableField<>();
    public SingleLiveEvent specs_event = new SingleLiveEvent();
    public ObservableField<String> spec_id = new ObservableField<>();
    public SingleLiveEvent<Boolean> spec_sale_event = new SingleLiveEvent<>();
    public int startPosition = 0;
    public int pageSize = 20;
    public SingleLiveEvent<Boolean> Refresh_Event = new SingleLiveEvent();
    public SingleLiveEvent LoadMore_Event = new SingleLiveEvent();
    public ObservableField<Integer> spec_count = new ObservableField<>();
    public ObservableField<String> goods_id = new ObservableField<>();
    public SingleLiveEvent set_greens_delete_event = new SingleLiveEvent();
    public ObservableField<Activity> activity = new ObservableField<>();

    public GoodsListViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, GoodsListFragment.TAG, new BindingAction() {
            @Override
            public void call() {
                greens_list_refresh();
            }
        });
    }

    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand upload_goods_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            current();
        }
    });

    public void current() {
        RetrofitClient.getInstance().create(ApiService.class).current()
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

                    }

                    @Override
                    public void onResult(List<CommonEntity> entity) {
                        Bundle bundle = new Bundle();
                        bundle.putString("target_name", entity.get(0).getName());
                        bundle.putString("target_id", String.valueOf(entity.get(0).getId()));
                        bundle.putString("freight_fee", entity.get(0).getFreight_fee());
                        bundle.putString("service_fee", entity.get(0).getService_fee());
                        startContainerActivity(UploadGoodsInfoFragment.class.getCanonicalName(), bundle);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void greens_list_loadMore() {
        RetrofitClient.getInstance().create(ApiService.class).greens_list(startPosition += pageSize, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<GreensEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<GreensEntity> entity) {
                        for (int i = 0; i < entity.size(); i++) {
                            GreensEntity greensEntity = entity.get(i);
                            GoodsListItemViewModel model = new GoodsListItemViewModel(GoodsListViewModel.this, greensEntity);
                            listItemViewModels.add(model);
                        }
                        LoadMore_Event.call();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }
                });
    }

    public void greens_list_refresh() {
        RetrofitClient.getInstance().create(ApiService.class).greens_list(startPosition = 0, pageSize)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<GreensEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<GreensEntity> entity) {
                        listItemViewModels.clear();
                        for (int i = 0; i < entity.size(); i++) {
                            GreensEntity greensEntity = entity.get(i);
                            GoodsListItemViewModel model = new GoodsListItemViewModel(GoodsListViewModel.this, greensEntity);
                            listItemViewModels.add(model);
                        }
                        Refresh_Event.setValue(!(listItemViewModels.size() < pageSize));
                        Refresh_Event.call();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }
                });
    }

    public void specs() {
        RetrofitClient.getInstance().create(ApiService.class).greens_spec_info(goods_id.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<SpecsEntity>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onResult(List<SpecsEntity> entity) {
                        specs.set(entity);
                        specs_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void deleteItem(GoodsListItemViewModel itemViewModel) {
        listItemViewModels.remove(itemViewModel);
    }

    public void set_greens_spec_sale() {
        RetrofitClient.getInstance().create(ApiService.class).set_greens_spec_sale(spec_id.get())
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
                    public void onComplete() {

                    }

                    @Override
                    public void onResult(CommonEntity entity) {
                        spec_sale_event.setValue(entity.getIs_on_sale());
                        spec_sale_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void set_greens_delete() {
        RetrofitClient.getInstance().create(ApiService.class).set_greens_delete(spec_id.get())
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
                        set_greens_delete_event.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
