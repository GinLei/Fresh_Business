package com.dingxin.fresh.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentPosBinding;
import com.dingxin.fresh.utils.BluetoothUtils;
import com.dingxin.fresh.vm.PosViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;


public class PosFragment extends BaseFragment<FragmentPosBinding, PosViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_pos;
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
                    viewModel.Scan(getActivity());
                }
            }
        });
    }
}
