package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentGoodspicBinding;
import com.dingxin.fresh.vm.GoodsPicViewModel;
import com.yanzhenjie.sofia.Sofia;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;


public class GoodsPicFragment extends BaseFragment<FragmentGoodspicBinding, GoodsPicViewModel> implements CustomAdapt {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_goodspic;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.greens_cate_id.set(getArguments().getString("greens_cate_id"));
        viewModel.pic_list();
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }
}
