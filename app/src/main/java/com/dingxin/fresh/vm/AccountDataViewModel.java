package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.BusinessInfoEntity;
import com.dingxin.fresh.fragment.ModifyAccountDataFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.RxUtils;
import okhttp3.ResponseBody;

public class AccountDataViewModel extends BaseViewModel {

    public final ObservableField<String> market_shop_name = new ObservableField<>("");
    public final ObservableField<String> real_name = new ObservableField<>("");
    public final ObservableField<String> mobile = new ObservableField<>("");
    public final ObservableField<String> tab = new ObservableField<>("");
    public final ObservableField<String> bazaar_name = new ObservableField<>("");
    public final ObservableField<String> market_shop_content = new ObservableField<>("");
    public final ObservableField<String> is_upload = new ObservableField<>("");
    public final ObservableField<String> booth_image = new ObservableField<>("");
    public final SingleLiveEvent finishEvent = new SingleLiveEvent();
    public final SingleLiveEvent modificationEvent = new SingleLiveEvent();
    public final SingleLiveEvent<String> PhotoEvent = new SingleLiveEvent<>();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finishEvent.call();
        }
    });
    public BindingCommand modification_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            modificationEvent.call();
        }
    });
    public BindingCommand photo_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            DownLoad(booth_image.get());
        }
    });

    public AccountDataViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, ModifyAccountDataFragment.TAG, new BindingAction() {
            @Override
            public void call() {
                RequestBusinessInfoData();
            }
        });
    }

    public void RequestBusinessInfoData() {

        RetrofitClient.getInstance().create(ApiService.class).RequestBusinessInfoData()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<BusinessInfoEntity>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(BusinessInfoEntity entity) {
                        market_shop_name.set(entity.getMarket_shop_name());
                        real_name.set(entity.getRealname());
                        mobile.set(entity.getMobile());
                        tab.set(entity.getTab());
                        bazaar_name.set(entity.getBazaar_name());
                        market_shop_content.set(entity.getMarket_shop_content());
                        is_upload.set(entity.getIs_upload());
                        booth_image.set(entity.getBooth_image());
                        dismissDialog();
                    }
                });
    }

    public void DownLoad(String loadUrl) {
        String destFileDir = getApplication().getCacheDir().getPath();
        String destFileName = System.currentTimeMillis() + ".jpg";
        DownLoadManager.getInstance().load(loadUrl, new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void onStart() {
                //RxJava的onStart()
            }

            @Override
            public void onCompleted() {
                //RxJava的onCompleted()
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                //下载成功的回调
                PhotoEvent.setValue(destFileDir + "/" + destFileName);
                PhotoEvent.call();
            }

            @Override
            public void progress(final long progress, final long total) {
                //下载中的回调 progress：当前进度 ，total：文件总大小
            }

            @Override
            public void onError(Throwable e) {
                //下载错误回调
            }
        });
    }

}
