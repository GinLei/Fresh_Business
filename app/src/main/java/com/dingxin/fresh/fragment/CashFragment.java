package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentCashBinding;
import com.dingxin.fresh.vm.CashViewModel;
import com.yanzhenjie.sofia.Sofia;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;

public class CashFragment extends BaseFragment<FragmentCashBinding, CashViewModel> implements CustomAdapt {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cash;
    }

    @Override
    public int initVariableId() {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return BR.viewModel;
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
