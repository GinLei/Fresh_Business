package com.dingxin.fresh.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentPhotoBinding;
import com.dingxin.fresh.vm.PhotoViewModel;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;
import com.yanzhenjie.sofia.Sofia;

import java.io.File;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SDCardUtils;

public class PhotoFragment extends BaseFragment<FragmentPhotoBinding, PhotoViewModel> {
    private String uri;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_photo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackgroundAlpha(0);
        uri = getArguments().getString("Uri");
        binding.imageView.setImage(new FileBitmapDecoderFactory(new File(uri)));
    }

    @Override
    public void onDestroy() {
        File file = new File(uri);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
        super.onDestroy();
    }
}
