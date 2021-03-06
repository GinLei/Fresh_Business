package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.BusinessInfoEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.fragment.ModifyAccountDataFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ModifyAccountDataViewModel extends BaseViewModel {
    public final ObservableField<String> real_name = new ObservableField<>("");
    public final ObservableField<String> mobile = new ObservableField<>("");
    public final ObservableField<String> bazaar_name = new ObservableField<>("");
    public final ObservableField<String> market_shop_content = new ObservableField<>("");
    public final ObservableField<String> booth_image = new ObservableField<>("");
    public final SingleLiveEvent finishEvent = new SingleLiveEvent();
    public final SingleLiveEvent modificationEvent = new SingleLiveEvent();
    public final SingleLiveEvent<String> PhotoEvent = new SingleLiveEvent<>();
    public BindingCommand photo_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            DownLoad(booth_image.get());
        }
    });

    public ModifyAccountDataViewModel(@NonNull Application application) {
        super(application);
    }

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
    public BindingCommand confirm_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            update(real_name.get(), mobile.get(), bazaar_name.get(), market_shop_content.get(), booth_image.get());
        }
    });

    public void uploadImages(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("upload", file.getName(), requestBody);
        MultipartBody body = builder.build();

        RetrofitClient.getInstance().create(ApiService.class).upload(body)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<UploadEntity>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(UploadEntity uploadEntity) {
                        booth_image.set(uploadEntity.getThumb());
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void update(String realname, String mobile, String market_shop_name, String market_shop_content, String booth_image) {

        RetrofitClient.getInstance().create(ApiService.class).update(realname, mobile, market_shop_name, market_shop_content, booth_image)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        ToastUtils.showShort("修改成功");
                        dismissDialog();
                        Messenger.getDefault().sendNoMsg(ModifyAccountDataFragment.TAG);
                        finishEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
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
