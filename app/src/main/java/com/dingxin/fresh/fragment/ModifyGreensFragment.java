package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.PicAdapter;
import com.dingxin.fresh.databinding.FragmentModifygreensBinding;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.view.OnRecyclerViewScrollListener;
import com.dingxin.fresh.vm.ModifyGreensViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BR;

public class ModifyGreensFragment extends BaseFragment<FragmentModifygreensBinding, ModifyGreensViewModel> implements PicAdapter.OnItemClickListener {
    private List<CommonEntity> pic_list = new ArrayList<>();
    private PicAdapter picAdapter;
    private MaterialDialog pic_dialog;
    private static final int CHOOSE_PHOTO = 2;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_modifygreens;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        Bundle bundle = getArguments();
        viewModel.goods_weight.set(bundle.getString("goods_weight"));
        viewModel.goods_name.set(bundle.getString("goods_name"));
        viewModel.goods_content.set(bundle.getString("note_name"));
        viewModel.pic_uri.set(bundle.getString("thumb"));
        viewModel.limit_num.set(bundle.getInt("limit_num"));
        viewModel.is_stander.set(bundle.getInt("is_stander"));
        viewModel.unit_name.set(bundle.getString("unit_name"));
        viewModel.target_name.set(bundle.getString("target_name"));
        viewModel.class_name.set(bundle.getString("class_name"));
        viewModel.id.set(bundle.getInt("id"));
        viewModel.two_level_class_id.set(bundle.getInt("two_level_class_id"));
        viewModel.deliver_fee.set(getArguments().getString("freight_fee"));
        viewModel.procedure_points.set(getArguments().getString("service_fee"));
        List<SpecsEntity> specsEntities = new Gson().fromJson(bundle.getString("goods_specs"), new TypeToken<List<SpecsEntity>>() {
        }.getType());
        for (int i = 0; i < specsEntities.size(); i++) {
            addGreens(specsEntities.get(i));
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.pic_event.observe(getViewLifecycleOwner(), new Observer<List<CommonEntity>>() {
            @Override
            public void onChanged(List<CommonEntity> commonEntities) {
                if (commonEntities != null) {
                    pic_list.addAll(commonEntities);
                    if (picAdapter == null) {
                        picAdapter = new PicAdapter(getContext().getApplicationContext(), pic_list);
                        picAdapter.setOnItemClickListener(ModifyGreensFragment.this);
                        pic_dialog = new MaterialDialog.Builder(getContext()).title("选择图片").adapter(picAdapter, new GridLayoutManager(getContext(), 3)).show();
                        pic_dialog.getRecyclerView().addOnScrollListener(new OnRecyclerViewScrollListener() {
                            @Override
                            public void onBottom() {
                                viewModel.pic_list();
                            }
                        });
                        pic_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                pic_list.clear();
                                picAdapter = null;
                                viewModel.is_haveData.set(true);
                                viewModel.page.set(1);
                            }
                        });
                    } else {
                        picAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        viewModel.PhotoEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Uri", ImageUtils.getImagePath(Uri.parse(s), getActivity()));
                    startContainerActivity(PhotoFragment.class.getCanonicalName(), bundle);
                }
            }
        });
        viewModel.Submit_event.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                List<SpecsEntity> list = new ArrayList<>();
                if (viewModel.is_stander.get() == 0) {
                    for (int i = 0; i < binding.container.getChildCount(); i++) {
                        View child = binding.container.getChildAt(i);
                        EditText edit_1 = child.findViewById(R.id.edit_1);
                        EditText edit_2 = child.findViewById(R.id.edit_2);
                        EditText edit_3 = child.findViewById(R.id.edit_3);
                        EditText edit_4 = child.findViewById(R.id.edit_4);
                        if (TextUtils.isEmpty(edit_1.getText().toString()) || TextUtils.isEmpty(edit_2.getText().toString()) || TextUtils.isEmpty(edit_3.getText().toString()) || TextUtils.isEmpty(edit_4.getText().toString())) {
                            ToastUtils.showShort("请补全规格信息");
                            return;
                        }
                        SpecsEntity specsEntity = new SpecsEntity();
                        specsEntity.setMin_title(edit_1.getText().toString());
                        specsEntity.setMax_title(edit_2.getText().toString());
                        specsEntity.setGoods_price(edit_3.getText().toString());
                        specsEntity.setSchedule_price(edit_4.getText().toString());
                        list.add(specsEntity);
                    }
                } else if (viewModel.is_stander.get() == 1) {
                    for (int i = 0; i < binding.container.getChildCount(); i++) {
                        View child = binding.container.getChildAt(i);
                        EditText edit_1 = child.findViewById(R.id.edit_1);
                        EditText edit_2 = child.findViewById(R.id.edit_2);
                        EditText edit_3 = child.findViewById(R.id.edit_3);
                        EditText edit_4 = child.findViewById(R.id.edit_4);
                        if (viewModel.unit_name.get().equals("斤") || viewModel.unit_name.get().equals("两")) {
                            if (TextUtils.isEmpty(edit_2.getText().toString()) || TextUtils.isEmpty(edit_3.getText().toString())) {
                                ToastUtils.showShort("请补全规格信息");
                                return;
                            }
                        } else {
                            if (TextUtils.isEmpty(edit_1.getText().toString()) || TextUtils.isEmpty(edit_2.getText().toString()) || TextUtils.isEmpty(edit_3.getText().toString()) || TextUtils.isEmpty(edit_4.getText().toString())) {
                                ToastUtils.showShort("请补全规格信息");
                                return;
                            }
                        }
                        SpecsEntity specsEntity = new SpecsEntity();
                        specsEntity.setMin_title(edit_1.getText().toString());
                        specsEntity.setGoods_price(edit_2.getText().toString());
                        specsEntity.setSchedule_price(edit_3.getText().toString());
                        specsEntity.setGoods_weight(edit_4.getText().toString());
                        list.add(specsEntity);
                    }
                }
                viewModel.Submit(new Gson().toJson(list));
            }
        });
        viewModel.add_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                addGreens(null);
            }
        });
        viewModel.delete_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.container.removeViewAt(binding.container.getChildCount() - 1);
                if (binding.container.getChildCount() == 0) {
                    viewModel.delete_visible.set(View.GONE);
                }
            }
        });
        viewModel.PhotoEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Uri", ImageUtils.getImagePath(Uri.parse(s), getActivity()));
                    startContainerActivity(PhotoFragment.class.getCanonicalName(), bundle);
                }
            }
        });
        viewModel.picWayEvent.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                new MaterialDialog.Builder(getActivity())
                        .title("选择图片")
                        .content("是否自行上传")
                        .positiveText("是")
                        .negativeText("否")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
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
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                if (viewModel.two_level_class_id.get() == 0) {
                                    ToastUtils.showShort("请选择商品分类");
                                    return;
                                }
                                viewModel.pic_list();
                            }
                        }).show();
            }
        });
    }

    private void addGreens(SpecsEntity entity) {
        if (binding.container.getChildCount() == viewModel.limit_num.get()) {
            ToastUtils.showShort("添加规格超过限制数量");
            return;
        }
        View view = null;
        switch (viewModel.is_stander.get()) {
            case 0:
                //非标品
                view = getLayoutInflater().inflate(R.layout.layout_specs_one, null);
                TextView specs = view.findViewById(R.id.specs);
                specs.setText("斤/" + viewModel.unit_name.get());
                EditText edit1 = view.findViewById(R.id.edit_1);
                EditText edit2 = view.findViewById(R.id.edit_2);
                EditText edit3 = view.findViewById(R.id.edit_3);
                EditText edit4 = view.findViewById(R.id.edit_4);
                TextView price_1 = view.findViewById(R.id.price_1);
                TextView price_2 = view.findViewById(R.id.price_2);
                TextView tv_1 = view.findViewById(R.id.tv_1);
                TextView tv_2 = view.findViewById(R.id.tv_2);
                View finalView = view;
                view.findViewById(R.id.deleted).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.container.removeView(finalView);
                    }
                });
                ((TextView) view.findViewById(R.id.freight_fee)).setText("配送费：¥ " + viewModel.deliver_fee.get());
                ((TextView) view.findViewById(R.id.service_fee)).setText("平台服务费：" + viewModel.procedure_points.get());
                ((TextView) view.findViewById(R.id.freight_fee_s)).setText("配送费：¥ " + viewModel.deliver_fee.get());
                ((TextView) view.findViewById(R.id.service_fee_s)).setText("平台服务费：" + viewModel.procedure_points.get());
                if (entity != null) {
                    edit1.setText(entity.getMin_title());
                    edit2.setText(entity.getMax_title());
                    edit3.setText(entity.getGoods_price());
                    edit4.setText(entity.getSchedule_price());
                    tv_1.setVisibility(View.VISIBLE);
                    tv_2.setVisibility(View.VISIBLE);
                    price_1.setVisibility(View.VISIBLE);
                    price_2.setVisibility(View.VISIBLE);
                    price_1.setText(entity.getPrice_old());
                    price_2.setText(entity.getMoney_old());
                }
                edit3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .inputRangeRes(1, 10, R.color.color_red)
                                .inputType(8194)
                                .input("请输入价格", "", new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                    }
                                })
                                .positiveText("确定")
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String content = dialog.getInputEditText().getText().toString();
                                        edit3.setText(content);
                                        viewModel.new_price(tv_1, price_1, content, "");
                                    }
                                }).show();

                    }
                });
                edit4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .inputRangeRes(1, 20, R.color.color_red)
                                .inputType(8194)
                                .input("请输入价格", "", new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                    }
                                })
                                .positiveText("确定")
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String content = dialog.getInputEditText().getText().toString();
                                        edit4.setText(content);
                                        viewModel.new_price(tv_2, price_2, content, "");
                                    }
                                }).show();
                    }
                });
                break;
            case 1:
                //标品
                view = getLayoutInflater().inflate(R.layout.layout_specs_two, null);
                if (viewModel.unit_name.get().equals("斤") || viewModel.unit_name.get().equals("两")) {
                    view.findViewById(R.id.edit_1).setVisibility(View.GONE);
                    view.findViewById(R.id.edit_4).setVisibility(View.GONE);
                }
                EditText edit6 = view.findViewById(R.id.edit_2);
                EditText edit7 = view.findViewById(R.id.edit_3);
                TextView price_3 = view.findViewById(R.id.price_1);
                TextView price_4 = view.findViewById(R.id.price_2);
                TextView tv_3 = view.findViewById(R.id.tv_1);
                TextView tv_4 = view.findViewById(R.id.tv_2);
                View finalView1 = view;
                view.findViewById(R.id.deleted).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.container.removeView(finalView1);
                    }
                });
                ((TextView) view.findViewById(R.id.freight_fee)).setText("配送费：¥ " + viewModel.deliver_fee.get());
                ((TextView) view.findViewById(R.id.service_fee)).setText("平台服务费：" + viewModel.procedure_points.get());
                ((TextView) view.findViewById(R.id.freight_fee_s)).setText("配送费：¥ " + viewModel.deliver_fee.get());
                ((TextView) view.findViewById(R.id.service_fee_s)).setText("平台服务费：" + viewModel.procedure_points.get());
                if (entity != null) {
                    ((EditText) view.findViewById(R.id.edit_1)).setText(entity.getMin_title());
                    ((EditText) view.findViewById(R.id.edit_4)).setText(entity.getGoods_weight());
                    edit6.setText(entity.getGoods_price());
                    edit7.setText(entity.getSchedule_price());
                    tv_3.setVisibility(View.VISIBLE);
                    tv_4.setVisibility(View.VISIBLE);
                    price_3.setVisibility(View.VISIBLE);
                    price_4.setVisibility(View.VISIBLE);
                    price_3.setText(entity.getPrice_old());
                    price_4.setText(entity.getMoney_old());
                    //view.findViewById(R.id.edit_4).setVisibility(View.GONE);
                }

                edit6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .inputRangeRes(1, 20, R.color.color_red)
                                .inputType(8194)
                                .input("请输入价格", "", new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                    }
                                })
                                .positiveText("确定")
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String content = dialog.getInputEditText().getText().toString();
                                        edit6.setText(content);
                                        viewModel.new_price(tv_3, price_3, content, ((EditText) finalView1.findViewById(R.id.edit_4)).getText().toString());
                                    }
                                }).show();
                    }
                });
                edit7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getContext())
                                .inputRangeRes(1, 20, R.color.color_red)
                                .inputType(8194)
                                .input("请输入价格", "", new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                    }
                                })
                                .positiveText("确定")
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String content = dialog.getInputEditText().getText().toString();
                                        edit7.setText(content);
                                        viewModel.new_price(tv_4, price_4, content, ((EditText) finalView1.findViewById(R.id.edit_4)).getText().toString());
                                    }
                                }).show();
                    }
                });
                break;
        }
        binding.container.addView(view);
    }


    @Override
    public void onClick(String uri) {
        pic_dialog.dismiss();
        viewModel.pic_uri.set(uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
                    Uri uri = data.getData();
                    String filePath = ImageUtils.getImagePath(uri, getActivity());
                    if (!TextUtils.isEmpty(filePath)) {
                        ImageUtils.compressWithRx(filePath, new Consumer<File>() {
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
