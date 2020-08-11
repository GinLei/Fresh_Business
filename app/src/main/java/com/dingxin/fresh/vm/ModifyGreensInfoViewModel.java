package com.dingxin.fresh.vm;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.GreensEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.fragment.GoodsListFragment;
import com.dingxin.fresh.utils.RetrofitClient;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
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

public class ModifyGreensInfoViewModel extends BaseViewModel {
    public String destFileDir;
    public String destFileName;
    public SingleLiveEvent submit_event = new SingleLiveEvent();
    public ObservableField<String> unit_name = new ObservableField<>("");
    public ObservableField<String> class_id_two = new ObservableField<>("");
    public ObservableField<String> cid = new ObservableField<>("");
    public ObservableField<String> goods_specs = new ObservableField<>("");
    public ObservableField<String> freight_fee = new ObservableField<>("");
    public ObservableField<String> service_fee = new ObservableField<>("");
    public ObservableField<Integer> unit_type = new ObservableField<>(0);
    public SingleLiveEvent<String> pic_click_event = new SingleLiveEvent();
    public ObservableField<String> img_url = new ObservableField<>();
    public ObservableField<String> target_name = new ObservableField<>("");
    public SingleLiveEvent edit_goods_name_event = new SingleLiveEvent();
    public SingleLiveEvent edit_goods_remarks_event = new SingleLiveEvent();
    public SingleLiveEvent add_specs_event = new SingleLiveEvent();
    public ObservableField<String> spec_id = new ObservableField<>();
    public ObservableField<String> goods_name = new ObservableField<>("");
    public ObservableField<String> goods_remarks = new ObservableField<>("");
    public SingleLiveEvent choose_photo_way_event = new SingleLiveEvent();

    public BindingCommand edit_goods_name = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            edit_goods_name_event.call();
        }
    });
    public BindingCommand edit_goods_remarks = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            edit_goods_remarks_event.call();
        }
    });
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand choose_photo_way = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            choose_photo_way_event.call();
        }
    });
    public BindingCommand pic_click = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            DownLoad();
        }
    });
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submit_event.call();
        }
    });
    public BindingCommand add_specs_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            add_specs_event.call();
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
                        img_url.set(uploadEntity.getThumb());
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public void DownLoad() {
        destFileDir = getApplication().getCacheDir().getPath();
        destFileName = System.currentTimeMillis() + ".jpg";
        DownLoadManager.getInstance().load(img_url.get(), new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                pic_click_event.setValue(destFileDir + "/" + destFileName);
                pic_click_event.call();
            }

            @Override
            public void progress(final long progress, final long total) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void new_price(TextView t, String price, String goods_weight) {
        RetrofitClient.getInstance().create(ApiService.class).new_price(class_id_two.get(), price, unit_name.get(), goods_weight)
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
                        ToastUtils.showShort("价格" + entity.getNew_price());
                        t.setText(entity.getNew_price());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void Submit() {
        Log.e("上传的规格", goods_specs.get().toString());
        RetrofitClient.getInstance().create(ApiService.class).greens_edit(class_id_two.get(), cid.get(), unit_name.get(), goods_name.get(), goods_remarks.get(), goods_specs.get(), img_url.get(), spec_id.get())
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
                    public void onResult(Object entity) {
                        dismissDialog();
                        ToastUtils.showShort("修改菜品成功");
                        Messenger.getDefault().sendNoMsg(GoodsListFragment.TAG);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        super.onError(e);
                    }
                });
    }

    public ModifyGreensInfoViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, GoodsPicItemViewModel.class.getSimpleName(), String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                if (!TextUtils.isEmpty(s)) {
                    img_url.set(s);
                }
            }
        });
    }
}
