package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentUploadgoodsinfoBinding;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.vm.UploadGoodsInfoViewModel;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;

import static android.app.Activity.RESULT_OK;

public class UploadGoodsInfoFragment extends BaseFragment<FragmentUploadgoodsinfoBinding, UploadGoodsInfoViewModel> implements CustomAdapt {
    private static final int CHOOSE_PHOTO = 2;
    private static final int TAKE_CAMERA = 3;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_uploadgoodsinfo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        viewModel.target_name.set(bundle.getString("target_name"));
        viewModel.target_id.set(bundle.getString("target_id"));
        viewModel.freight_fee.set(bundle.getString("freight_fee"));
        viewModel.service_fee.set(bundle.getString("service_fee"));
        //TODO
    }

    @Override
    public void initViewObservable() {
        viewModel.edit_goods_name_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                new MaterialDialog.Builder(getContext()).title("商品名称").inputRangeRes(1, 10, R.color.color_red).input("请输入商品名称", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.goods_name.set(dialog.getInputEditText().getText().toString());
                    }
                }).show();
            }
        });
        viewModel.edit_goods_remarks_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                new MaterialDialog.Builder(getContext()).title("商品备注").inputRangeRes(1, 10, R.color.color_red).input("请输入商品备注", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.goods_remarks.set(dialog.getInputEditText().getText().toString());
                    }
                }).show();
            }
        });
        viewModel.add_specs_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                addSpecs();
                binding.scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
        viewModel.clear_specs_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.container.removeAllViews();
            }
        });
        viewModel.add_specs_event_first.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.addSpecs.performClick();
            }
        });
        viewModel.choose_photo_way_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                new MaterialDialog.Builder(getActivity()).title("商品图片")
                        .items(new String[]{"相册", "拍照", "默认"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (TextUtils.equals(text, "相册")) {
                                    new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean granted) throws Exception {
                                            if (granted) {
                                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                                startActivityForResult(intent, CHOOSE_PHOTO);
                                            }
                                        }
                                    });
                                } else if (TextUtils.equals(text, "拍照")) {
                                    new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean granted) throws Exception {
                                            if (granted) {
                                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "fileImg.jpg")));
                                                startActivityForResult(intent, TAKE_CAMERA);
                                            }
                                        }
                                    });
                                }
                                return true;
                            }
                        })
                        .show();
            }
        });
        viewModel.pic_click_event.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Uri", ImageUtils.getImagePath(Uri.parse(s), getActivity()));
                    startContainerActivity(PhotoFragment.class.getCanonicalName(), bundle);
                }
            }
        });
        viewModel.submit_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<SpecsEntity> list = new ArrayList<>();
                for (int i = 0; i < binding.container.getChildCount(); i++) {
                    SpecsEntity entity = new SpecsEntity();
                    View view = binding.container.getChildAt(i);
                    switch (viewModel.unit_type.get()) {
                        case 1:
                            entity.setMin_title(((EditText) view.findViewById(R.id.edit_1)).getText().toString());
                            entity.setGoods_weight(((EditText) view.findViewById(R.id.edit_2)).getText().toString());
                            entity.setGoods_price(((EditText) view.findViewById(R.id.edit_3)).getText().toString());
                            entity.setSchedule_price(((EditText) view.findViewById(R.id.edit_4)).getText().toString());
                            break;
                        case 2:
                            entity.setMin_title(((EditText) view.findViewById(R.id.edit_1)).getText().toString());
                            entity.setMax_title(((EditText) view.findViewById(R.id.edit_2)).getText().toString());
                            entity.setGoods_price(((EditText) view.findViewById(R.id.edit_3)).getText().toString());
                            entity.setSchedule_price(((EditText) view.findViewById(R.id.edit_4)).getText().toString());
                            break;
                        case 3:
                            entity.setGoods_price(((EditText) view.findViewById(R.id.edit_1)).getText().toString());
                            entity.setSchedule_price(((EditText) view.findViewById(R.id.edit_2)).getText().toString());
                            break;
                    }
                    list.add(entity);
                }
                viewModel.goods_specs.set(new Gson().toJson(list));
                viewModel.submit();
            }
        });
    }

    private void addSpecs() {
        switch (viewModel.unit_type.get()) {
            //成品  规格名称  净重
            case 1:
                addTypeOne();
                break;
            //范围称重  斤/个
            case 2:
                addTypeTwo();
                break;
            //直接单价
            case 3:
                addTypeThree();
                break;
        }
    }

    private void addTypeOne() {
        View view = getLayoutInflater().inflate(R.layout.layout_specs_two, null);
        ((TextView) view.findViewById(R.id.freight_fee)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee)).setText("平台服务费：" + viewModel.service_fee.get());
        ((TextView) view.findViewById(R.id.freight_fee_s)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee_s)).setText("平台服务费：" + viewModel.service_fee.get());
        view.findViewById(R.id.deleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.container.removeView(view);
            }
        });
        view.findViewById(R.id.edit_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品规格").inputRangeRes(1, 10, R.color.color_red).input("请输入商品规格", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                    }
                }).show();
            }
        });

        view.findViewById(R.id.edit_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品净重").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品净重(单位kg)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        ((EditText) view.findViewById(R.id.edit_3)).setText("");
                        ((EditText) view.findViewById(R.id.edit_4)).setText("");
                        ((TextView) view.findViewById(R.id.price_1)).setText("0");
                        ((TextView) view.findViewById(R.id.price_2)).setText("0");
                    }
                }).show();
            }
        });

        view.findViewById(R.id.edit_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(((EditText) view.findViewById(R.id.edit_2)).getText().toString())) {
                    ToastUtils.showShort("请填写商品净重");
                    return;
                }
                new MaterialDialog.Builder(getContext()).title("商品单价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品单价(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_1), dialog.getInputEditText().getText().toString(), ((EditText) view.findViewById(R.id.edit_2)).getText().toString());
                    }
                }).show();
            }
        });
        view.findViewById(R.id.edit_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(((EditText) view.findViewById(R.id.edit_2)).getText().toString())) {
                    ToastUtils.showShort("请填写商品净重");
                    return;
                }
                new MaterialDialog.Builder(getContext()).title("商品预约价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品预约价格(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_2), dialog.getInputEditText().getText().toString(), ((EditText) view.findViewById(R.id.edit_2)).getText().toString());
                    }
                }).show();
            }
        });
        binding.container.addView(view);
    }

    private void addTypeTwo() {
        View view = getLayoutInflater().inflate(R.layout.layout_specs_one, null);
        ((TextView) view.findViewById(R.id.freight_fee)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee)).setText("平台服务费：" + viewModel.service_fee.get());
        ((TextView) view.findViewById(R.id.freight_fee_s)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee_s)).setText("平台服务费：" + viewModel.service_fee.get());
        ((TextView) view.findViewById(R.id.specs)).setText(viewModel.unit_name.get());
        view.findViewById(R.id.deleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.container.removeView(view);
            }
        });
        view.findViewById(R.id.edit_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("误差区间").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入最小值", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                    }
                }).show();
            }
        });
        view.findViewById(R.id.edit_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("误差区间").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入最大值", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                    }
                }).show();
            }
        });
        view.findViewById(R.id.edit_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品单价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品单价(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_1), dialog.getInputEditText().getText().toString(), "");
                    }
                }).show();
            }
        });
        view.findViewById(R.id.edit_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品预约价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品预约价格(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_2), dialog.getInputEditText().getText().toString(), "");
                    }
                }).show();
            }
        });
        binding.container.addView(view);

    }

    private void addTypeThree() {
        View view = getLayoutInflater().inflate(R.layout.layout_specs_three, null);
        ((TextView) view.findViewById(R.id.freight_fee)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee)).setText("平台服务费：" + viewModel.service_fee.get());
        ((TextView) view.findViewById(R.id.freight_fee_s)).setText("配送费：¥ " + viewModel.freight_fee.get());
        ((TextView) view.findViewById(R.id.service_fee_s)).setText("平台服务费：" + viewModel.service_fee.get());
        view.findViewById(R.id.deleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.container.removeView(view);
            }
        });
        view.findViewById(R.id.edit_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品单价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品单价(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_1), dialog.getInputEditText().getText().toString(), "");
                    }
                }).show();
            }
        });
        view.findViewById(R.id.edit_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).title("商品预约价").inputRangeRes(1, 10, R.color.color_red).inputType(8194).input("请输入商品预约价格(元)", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                }).positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((EditText) v).setText(dialog.getInputEditText().getText().toString());
                        viewModel.new_price(view.findViewById(R.id.price_2), dialog.getInputEditText().getText().toString(), "");
                    }
                }).show();
            }
        });
        binding.container.addView(view);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
                    String filePath = ImageUtils.getImagePath(data.getData(), getActivity());
                    ImageUtils.compressWithRx(filePath, new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {

                            viewModel.uploadImages(file);
                        }
                    });
                    break;
                case TAKE_CAMERA:
                    File file = new File(Environment.getExternalStorageDirectory(), "fileImg.jpg");
                    if (file.exists()) {
                        ImageUtils.compressWithRx(file.getAbsolutePath(), new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Exception {
                                viewModel.uploadImages(file);
                            }
                        });
                    }
                    break;
            }
        }
    }
}
