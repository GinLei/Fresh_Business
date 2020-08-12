package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentInsideBinding;
import com.dingxin.fresh.e.MarketEntity;
import com.dingxin.fresh.e.TargetEntity;
import com.dingxin.fresh.vm.InsideViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;

public class InsideFragment extends BaseFragment<FragmentInsideBinding, InsideViewModel> implements CustomAdapt {
    private HashMap<String, String> market_info = new HashMap<>();
    public static final int CHOOSE_STALL_PHOTO = 2;
    public static final int CHOOSE_CARD_PHOTO = 3;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_inside;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.location_event.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                new RxPermissions(getActivity()).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            viewModel.requestMarketList("", "");
                        }
                    }
                });
            }
        });
        viewModel.market_event.observe(this, new Observer<List<MarketEntity>>() {
            @Override
            public void onChanged(List<MarketEntity> m_marketEntities) {
                if (m_marketEntities != null) {
                    String[] market_names = new String[m_marketEntities.size()];
                    for (int i = 0; i < market_names.length; i++) {
                        String name = m_marketEntities.get(i).getName();
                        String id = String.valueOf(m_marketEntities.get(i).getId());
                        market_names[i] = name;
                        market_info.put(name, id);
                    }
                    new MaterialDialog.Builder(getActivity()).title("市场选择")
                            .items(market_names)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    viewModel.market_id.set(market_info.get(text.toString()));
                                    viewModel.market_name.set(text.toString());
                                    return true;
                                }
                            }).show();
                }
            }
        });

        viewModel.target_event.observe(getViewLifecycleOwner(), new Observer<List<TargetEntity>>() {
            @Override
            public void onChanged(List<TargetEntity> m_targetEntities) {

                if (m_targetEntities != null) {
                    String[] target_names = new String[m_targetEntities.size()];
                    for (int i = 0; i < target_names.length; i++) {
                        target_names[i] = m_targetEntities.get(i).getName();
                    }
                    new MaterialDialog.Builder(getActivity()).title("标签选择")
                            .items(target_names)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    viewModel.target_name.set(text.toString());
                                    return true;
                                }
                            }).show();
                }
            }
        });
        viewModel.modification_stall_Event.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if (TextUtils.isEmpty(s)) {
                        new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) throws Exception {
                                if (granted) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(intent, CHOOSE_STALL_PHOTO);
                                }
                            }
                        });
                    } else {
                        viewModel.DownLoad(s);
                    }
                }
            }
        });
        viewModel.modification_card_Event.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if (TextUtils.isEmpty(s)) {
                        new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) throws Exception {
                                if (granted) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(intent, CHOOSE_CARD_PHOTO);
                                }
                            }
                        });
                    } else {
                        viewModel.DownLoad(s);
                    }
                }
            }
        });
        viewModel.PhotoEvent.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Uri", ImageUtils.getImagePath(Uri.parse(s), getActivity()));
                    startContainerActivity(PhotoFragment.class.getCanonicalName(), bundle);
                }
            }
        });
        viewModel.modification_img.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent, integer);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String filePath = ImageUtils.getImagePath(uri, getActivity());
                if (!TextUtils.isEmpty(filePath)) {
                    ImageUtils.compressWithRx(filePath, new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
                            switch (requestCode) {
                                case CHOOSE_STALL_PHOTO:
                                    viewModel.uploadImages(file, CHOOSE_STALL_PHOTO);
                                    break;
                                case CHOOSE_CARD_PHOTO:
                                    viewModel.uploadImages(file, CHOOSE_CARD_PHOTO);
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }
}
