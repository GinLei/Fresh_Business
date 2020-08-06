package com.dingxin.fresh.vm;

import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.R;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.UnitEntity;
import com.dingxin.fresh.e.UploadEntity;
import com.dingxin.fresh.fragment.ClassOneFragment;
import com.dingxin.fresh.fragment.ClassTwoFragment;
import com.dingxin.fresh.fragment.GoodsListFragment;
import com.dingxin.fresh.fragment.PhotoFragment;
import com.dingxin.fresh.fragment.UnitFragment;
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
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class UploadGoodsInfoViewModel extends BaseViewModel {
    public String destFileDir;
    public String destFileName;
    public ObservableField<String> goods_specs = new ObservableField<>("");
    public ObservableField<String> img_url = new ObservableField<>("");
    public ObservableField<String> target_name = new ObservableField<>("");
    public ObservableField<String> freight_fee = new ObservableField<>("");
    public ObservableField<String> service_fee = new ObservableField<>("");
    public ObservableField<String> target_id = new ObservableField<>("");
    public ObservableField<String> class_name_one = new ObservableField<>("点击选择一级分类");
    public ObservableField<String> class_id_one = new ObservableField<>("");
    public ObservableField<String> class_name_two = new ObservableField<>("点击选择二级分类");
    public ObservableField<String> class_id_two = new ObservableField<>("");
    public ObservableField<String> unit_name = new ObservableField<>("点击选择商品单位");
    public ObservableField<Integer> unit_type = new ObservableField<>(0);
    public ObservableField<String> goods_name = new ObservableField<>("请输入商品名称");
    public ObservableField<String> goods_remarks = new ObservableField<>("请输入商品备注");
    public SingleLiveEvent edit_goods_name_event = new SingleLiveEvent();
    public SingleLiveEvent edit_goods_remarks_event = new SingleLiveEvent();
    public SingleLiveEvent add_specs_event = new SingleLiveEvent();
    public SingleLiveEvent add_specs_event_first = new SingleLiveEvent();
    public SingleLiveEvent clear_specs_event = new SingleLiveEvent();
    public SingleLiveEvent choose_photo_way_event = new SingleLiveEvent();
    public SingleLiveEvent submit_event = new SingleLiveEvent();
    public SingleLiveEvent<String> pic_click_event = new SingleLiveEvent();
    public BindingCommand finish_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submit_event.call();
        }
    });

    public BindingCommand choose_photo_way = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(class_id_two.get())) {
                choose_photo_way_event.call();
            } else {
                ToastUtils.showShort("请选择商品分类");
            }
        }
    });
    public BindingCommand pic_click = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(img_url.get())) {
                return;
            }
            DownLoad();
        }
    });

    public BindingCommand to_fragment_class_one = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("target_id", target_id.get());
            startContainerActivity(ClassOneFragment.class.getCanonicalName(), bundle);
        }
    });
    public BindingCommand to_fragment_class_two = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(class_id_one.get())) {
                Bundle bundle = new Bundle();
                bundle.putString("target_id", class_id_one.get());
                startContainerActivity(ClassTwoFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showShort("请选择一级分类");
            }
        }
    });
    public BindingCommand to_fragment_unit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(class_id_two.get())) {
                Bundle bundle = new Bundle();
                bundle.putString("target_id", class_id_two.get());
                startContainerActivity(UnitFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showShort("请选择商品分类");
            }
        }
    });
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
    public BindingCommand add_specs_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (unit_type.get() != 0) {
                add_specs_event.call();
            } else {
                ToastUtils.showShort("请选择商品单位");
            }
        }
    });


    public UploadGoodsInfoViewModel(@NonNull Application application) {
        super(application);
        Messenger.getDefault().register(this, ClassOneItemViewModel.TAG, CommonEntity.class, new BindingConsumer<CommonEntity>() {
            @Override
            public void call(CommonEntity commonEntity) {
                if (commonEntity != null) {
                    class_name_one.set(commonEntity.getName());
                    class_id_one.set(String.valueOf(commonEntity.getId()));
                    class_name_two.set("请选择二级分类");
                    class_id_two.set("");
                    unit_name.set("请选择商品单位");
                    unit_type.set(0);
                    goods_name.set("请输入商品名称");
                    goods_remarks.set("请输入商品备注");
                    clear_specs_event.call();
                }
            }
        });
        Messenger.getDefault().register(this, ClassTwoItemViewModel.TAG, CommonEntity.class, new BindingConsumer<CommonEntity>() {
            @Override
            public void call(CommonEntity commonEntity) {
                if (commonEntity != null) {
                    class_name_two.set(commonEntity.getName());
                    class_id_two.set(String.valueOf(commonEntity.getId()));
                    unit_name.set("请选择商品单位");
                    unit_type.set(0);
                    goods_name.set("请输入商品名称");
                    goods_remarks.set("请输入商品备注");
                    clear_specs_event.call();
                }
            }
        });
        Messenger.getDefault().register(this, UnitItemViewModel.TAG, UnitEntity.class, new BindingConsumer<UnitEntity>() {
            @Override
            public void call(UnitEntity unitEntity) {
                if (unitEntity != null) {
                    unit_name.set(unitEntity.getUnit());
                    unit_type.set(unitEntity.getType());
                    clear_specs_event.call();
                    add_specs_event_first.call();
                }
            }
        });
    }

    public void new_price(TextView t, String price, String goods_weight) {
        RetrofitClient.getInstance().create(ApiService.class).new_price(String.valueOf(class_id_two.get()), price, unit_name.get(), goods_weight)
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
                        t.setText(entity.getNew_price());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
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

    public void submit() {
        RetrofitClient.getInstance().create(ApiService.class).greens_add(String.valueOf(class_id_two.get()), unit_name.get(), goods_name.get(), goods_remarks.get(), goods_specs.get(), img_url.get())
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
                        ToastUtils.showShort("添加菜品成功");
                        Messenger.getDefault().sendNoMsg(GoodsListFragment.TAG);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
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


}
