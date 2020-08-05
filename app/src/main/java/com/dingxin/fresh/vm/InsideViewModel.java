package com.dingxin.fresh.vm;

import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.AboutEntity;
import com.dingxin.fresh.e.MarketEntity;
import com.dingxin.fresh.e.TargetEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.fragment.InsideFragment;
import com.dingxin.fresh.activity.RichActivity;
import com.dingxin.fresh.fragment.ScalesFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import java.io.File;
import java.util.List;

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
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class InsideViewModel extends BaseViewModel {
    public static final String TAG = InsideViewModel.class.getSimpleName();
    public final ObservableField<Boolean> is_check = new ObservableField<>(false);
    public final ObservableField<String> market_sn = new ObservableField<>("");
    public final ObservableField<String> market_cn = new ObservableField<>("");
    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> phone = new ObservableField<>("");
    public final ObservableField<String> market_id = new ObservableField<>("");
    public final ObservableField<String> stall_img = new ObservableField<>("");
    public final ObservableField<String> card_img = new ObservableField<>("");
    public final ObservableField<String> market_shop_name = new ObservableField<>("");
    public final SingleLiveEvent<String> modification_stall_Event = new SingleLiveEvent();
    public final SingleLiveEvent<String> modification_card_Event = new SingleLiveEvent();
    public final SingleLiveEvent<Integer> modification_img = new SingleLiveEvent();
    public final SingleLiveEvent location_event = new SingleLiveEvent();
    public Drawable stall_drawable = ContextCompat.getDrawable(getApplication(), R.mipmap.upload_stall);
    public Drawable card_drawable = ContextCompat.getDrawable(getApplication(), R.mipmap.upload_id_card);
    public final SingleLiveEvent<List<MarketEntity>> market_event = new SingleLiveEvent<>();
    public final SingleLiveEvent<List<TargetEntity>> target_event = new SingleLiveEvent<>();
    public final SingleLiveEvent<String> PhotoEvent = new SingleLiveEvent<>();
    public final ObservableField<String> market_name = new ObservableField<>("点击选择所属市场");
    public final ObservableField<String> target_name = new ObservableField<>("点击选择摊位标签");
    public BindingCommand declare_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            about();
        }
    });
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submit();
        }
    });
    public BindingCommand modification_stall_img_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            modification_img.setValue(InsideFragment.CHOOSE_STALL_PHOTO);
            modification_img.call();
        }
    });
    public BindingCommand modification_card_img_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            modification_img.setValue(InsideFragment.CHOOSE_CARD_PHOTO);
            modification_img.call();
        }
    });

    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand market_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            location_event.call();
        }
    });
    public BindingCommand target_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestTargetList();
        }
    });

    public BindingCommand scales_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (BleManager.getInstance().isSupportBle()) {
                if (BleManager.getInstance().isBlueEnable()) {
                    startContainerActivity(ScalesFragment.class.getCanonicalName());
                } else {
                    ToastUtils.showShort("蓝牙未打开");
                }
            } else {
                ToastUtils.showShort("设备不支持BLE蓝牙");
            }
        }
    });
    public BindingCommand modification_stall_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            modification_stall_Event.setValue(stall_img.get());
            modification_stall_Event.call();
        }
    });
    public BindingCommand modification_card_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            modification_card_Event.setValue(card_img.get());
            modification_card_Event.call();
        }
    });

    public InsideViewModel(@NonNull Application application) {
        super(application);
    }

    public void requestMarketList(String lng, String lat) {

        RetrofitClient.getInstance().create(ApiService.class).requestMarketList(lng, lat)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<MarketEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<MarketEntity> entity) {
                        market_event.setValue(entity);
                        market_event.call();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void requestTargetList() {

        RetrofitClient.getInstance().create(ApiService.class).requestTargetList()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<TargetEntity>>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(List<TargetEntity> entity) {
                        target_event.setValue(entity);
                        target_event.call();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void uploadImages(File file, int code) {
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
                        if (code == InsideFragment.CHOOSE_STALL_PHOTO) {
                            stall_img.set(uploadEntity.getThumb());
                        } else if (code == InsideFragment.CHOOSE_CARD_PHOTO) {
                            card_img.set(uploadEntity.getThumb());
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
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

    public void submit() {
        if (TextUtils.isEmpty(name.get())) {
            ToastUtils.showShort("请填写摊主姓名");
            return;
        }
        if (TextUtils.isEmpty(phone.get())) {
            ToastUtils.showShort("请填写手机号");
            return;
        }
        if (TextUtils.isEmpty(market_id.get())) {
            ToastUtils.showShort("请选择所属市场");
            return;
        }
        if (TextUtils.isEmpty(target_name.get())) {
            ToastUtils.showShort("请选择摊位标签");
            return;
        }
        if (TextUtils.isEmpty(market_shop_name.get())) {
            ToastUtils.showShort("请填写摊位名称");
            return;
        }
        if (TextUtils.isEmpty(market_sn.get())) {
            ToastUtils.showShort("请填写摊位编号");
            return;
        }
        if (TextUtils.isEmpty(market_cn.get())) {
            ToastUtils.showShort("请填写摊位简介");
            return;
        }
        if (TextUtils.isEmpty(stall_img.get())) {
            ToastUtils.showShort("请上传摊位照片");
            return;
        }
        if (TextUtils.isEmpty(card_img.get())) {
            ToastUtils.showShort("请上传手持身份证照片");
            return;
        }
        if (!is_check.get()) {
            ToastUtils.showShort("请勾选用户协议");
            return;
        }
        RetrofitClient.getInstance().create(ApiService.class).become_seller(name.get(), phone.get(), market_id.get(), target_name.get(), market_shop_name.get(), market_sn.get(), market_cn.get(), stall_img.get(), card_img.get())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<Object>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(Object o) {
                        dismissDialog();
                        ToastUtils.showShort("入驻成功");
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void about() {
        RetrofitClient.getInstance().create(ApiService.class).about()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<AboutEntity>() {
                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }

                    @Override
                    public void onResult(AboutEntity entity) {
                        Bundle bundle = new Bundle();
                        bundle.putString("rich", entity.getContent());
                        startActivity(RichActivity.class, bundle);
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }
}
