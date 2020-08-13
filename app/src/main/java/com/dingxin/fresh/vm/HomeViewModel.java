package com.dingxin.fresh.vm;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.BannerEntity;
import com.dingxin.fresh.fragment.AccountDataFragment;
import com.dingxin.fresh.fragment.GoodsListFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeViewModel extends BaseViewModel {
    public SingleLiveEvent<List<BannerEntity>> refresh_event = new SingleLiveEvent<>();
    public SingleLiveEvent<List<BannerEntity>> event = new SingleLiveEvent<>();
    public ItemBinding<HomeItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_home_item);
    public ObservableList<HomeItemViewModel> listItemViewModels = new ObservableArrayList<>();
    public Drawable[] drawables = new Drawable[]{ContextCompat.getDrawable(getApplication(), R.mipmap.home_one), ContextCompat.getDrawable(getApplication(), R.mipmap.home_two), ContextCompat.getDrawable(getApplication(), R.mipmap.home_three), ContextCompat.getDrawable(getApplication(), R.mipmap.home_four)};
    public String[] texts = new String[]{"商户资料", "摊位管理", "商品管理", "用户管理"};

    public HomeViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, HomeItemViewModel.class.getSimpleName(), String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                switch (s) {
                    case "商户资料":
                        startContainerActivity(AccountDataFragment.class.getCanonicalName());
                        break;
                    case "商品管理":
                        startContainerActivity(GoodsListFragment.class.getCanonicalName());
                        break;
                    case "用户资料":
                    case "摊位管理":
                        ToastUtils.showShort("敬请期待");
                        break;
                }
            }
        });
        for (int i = 0; i < drawables.length; i++) {
            HomeItemViewModel viewModel = new HomeItemViewModel(HomeViewModel.this, drawables[i], texts[i]);
            listItemViewModels.add(viewModel);
        }
    }

    public void RequestBannerData() {
        RetrofitClient.getInstance().create(ApiService.class).RequestBannerList()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<BannerEntity>>() {
                    @Override
                    public void onResult(List<BannerEntity> bannerEntities) {
                        event.setValue(bannerEntities);
                        event.call();
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

    public void refreshBannerData() {
        RetrofitClient.getInstance().create(ApiService.class).RequestBannerList()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<BannerEntity>>() {
                    @Override
                    public void onResult(List<BannerEntity> bannerEntities) {
                        refresh_event.setValue(bannerEntities);
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
}
