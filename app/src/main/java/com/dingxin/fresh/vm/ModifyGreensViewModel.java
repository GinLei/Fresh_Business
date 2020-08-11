package com.dingxin.fresh.vm;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.fragment.GoodsListFragment;
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

public class ModifyGreensViewModel extends BaseViewModel {
    public ObservableField<String> deliver_fee = new ObservableField<>("");
    public ObservableField<String> procedure_points = new ObservableField<>("");
    public SingleLiveEvent picWayEvent = new SingleLiveEvent();
    public SingleLiveEvent Submit_event = new SingleLiveEvent();
    public SingleLiveEvent<List<CommonEntity>> pic_event = new SingleLiveEvent<>();
    public ObservableField<Integer> page = new ObservableField<>(1);
    public ObservableField<Boolean> is_haveData = new ObservableField<>(true);
    public ObservableField<Integer> id = new ObservableField<>();
    public ObservableField<String> goods_name = new ObservableField<>("");
    public ObservableField<String> goods_content = new ObservableField<>("");
    public ObservableField<Integer> delete_visible = new ObservableField<>(View.GONE);
    public ObservableField<String> pic_uri = new ObservableField<>("");
    public ObservableField<String> target_name = new ObservableField<>("");
    public ObservableField<String> class_name = new ObservableField<>("");
    public ObservableField<String> unit_name = new ObservableField<>("");
    public ObservableField<Integer> limit_num = new ObservableField<>(0);
    public ObservableField<Boolean> is_check = new ObservableField<>(false);
    public ObservableField<Integer> is_stander = new ObservableField<>(0);
    public ObservableField<Integer> two_level_class_id = new ObservableField<>(0);
    public ObservableField<String> goods_weight = new ObservableField<>("");
    public SingleLiveEvent add_event = new SingleLiveEvent();
    public SingleLiveEvent delete_event = new SingleLiveEvent();
    public SingleLiveEvent<String> PhotoEvent = new SingleLiveEvent<>();
    public BindingCommand photo_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            DownLoad(pic_uri.get());
        }
    });
    public BindingCommand pic_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            picWayEvent.call();
        }
    });
    public BindingCommand delete_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            delete_event.call();
        }
    });
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand Submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Submit_event.call();
        }
    });
    public BindingCommand add_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            add_event.call();
        }
    });

    public void new_price(TextView tv_1, TextView tv, String price, String goods_weight) {
        RetrofitClient.getInstance().create(ApiService.class).new_price(String.valueOf(two_level_class_id.get()), price, unit_name.get(), goods_weight)
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
                        tv.setText(entity.getNew_price());
                        tv_1.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public ModifyGreensViewModel(@NonNull Application application) {
        super(application);
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

    public void Submit(String goods_specs) {

        if (TextUtils.isEmpty(goods_name.get())) {
            ToastUtils.showShort("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(goods_content.get())) {
            ToastUtils.showShort("请输入商品简介");
            return;
        }
        if (TextUtils.isEmpty(goods_specs)) {
            ToastUtils.showShort("请添加商品规格");
            return;
        }
        if (TextUtils.isEmpty(pic_uri.get())) {
            ToastUtils.showShort("请添加商品图片");
            return;
        }
//        RetrofitClient.getInstance().create(ApiService.class).greens_edit(String.valueOf(id.get()), String.valueOf(two_level_class_id.get()), unit_name.get(), goods_name.get(), goods_content.get(), is_check.get() ? "1" : "0", goods_specs, pic_uri.get(), String.valueOf(is_stander.get()))
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        showDialog();
//                    }
//                })
//                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
//                .compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.exceptionTransformer())
//                .subscribe(new ApiDisposableObserver<Object>() {
//                    @Override
//                    public void onComplete() {
//                        dismissDialog();
//                    }
//
//                    @Override
//                    public void onResult(Object entity) {
//                        dismissDialog();
//                        ToastUtils.showShort("修改菜品成功");
//                        Messenger.getDefault().sendNoMsg(GoodsListFragment.TAG);
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        dismissDialog();
//                        super.onError(e);
//                    }
//                });
    }

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
                        pic_uri.set(uploadEntity.getThumb());
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
