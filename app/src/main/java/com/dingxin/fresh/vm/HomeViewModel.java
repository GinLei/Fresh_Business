package com.dingxin.fresh.vm;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;

public class HomeViewModel extends BaseViewModel {
    public List<Drawable> drawables = new ArrayList<>();
    public List<String> titles = new ArrayList<>();
    public SingleLiveEvent<List<BannerEntity>> refresh_event = new SingleLiveEvent<>();
    public SingleLiveEvent<List<BannerEntity>> event = new SingleLiveEvent<>();
    public BindingCommand one = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(AccountDataFragment.class.getCanonicalName());
        }
    });
    public BindingCommand two = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
    public BindingCommand three = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(GoodsListFragment.class.getCanonicalName());
        }
    });
    public BindingCommand four = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    public HomeViewModel(@NonNull Application application) {
        super(application);
        drawables.add(ContextCompat.getDrawable(getApplication(), R.mipmap.list));
        drawables.add(ContextCompat.getDrawable(getApplication(), R.mipmap.list));
        drawables.add(ContextCompat.getDrawable(getApplication(), R.mipmap.list));
        drawables.add(ContextCompat.getDrawable(getApplication(), R.mipmap.list));
        titles.add("商户资料");
        titles.add("摊位管理");
        titles.add("商品管理");
        titles.add("用户管理");
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
                    }
                });
    }
}
