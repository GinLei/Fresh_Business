package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import io.reactivex.functions.Consumer;

import androidx.lifecycle.Observer;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentModifyaccountdataBinding;
import com.dingxin.fresh.vm.ModifyAccountDataViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.io.File;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ModifyAccountDataFragment extends BaseFragment<FragmentModifyaccountdataBinding, ModifyAccountDataViewModel> {
    public static final int CHOOSE_PHOTO = 2;
    public static final int DATA_CHANGE = 3;
    public static final String TAG = "ModifyAccountDataFragment";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_modifyaccountdata;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        Bundle bundle = getArguments();
        viewModel.real_name.set(bundle.getString("real_name"));
        viewModel.mobile.set(bundle.getString("mobile"));
        viewModel.bazaar_name.set(bundle.getString("bazaar_name"));
        viewModel.market_shop_content.set(bundle.getString("market_shop_content"));
        viewModel.booth_image.set(bundle.getString("booth_image"));
    }

    @Override
    public void initViewObservable() {

        viewModel.finishEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                getActivity().finish();
            }
        });
        viewModel.modificationEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent, CHOOSE_PHOTO);
                        }
                    }
                });
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
