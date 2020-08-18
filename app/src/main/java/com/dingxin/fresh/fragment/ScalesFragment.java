package com.dingxin.fresh.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentScalesBinding;
import com.dingxin.fresh.vm.InsideViewModel;
import com.dingxin.fresh.vm.ScalesViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ScalesFragment extends BaseFragment<FragmentScalesBinding, ScalesViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_scales;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    viewModel.Scan();
                }
            }
        });
    }
}
