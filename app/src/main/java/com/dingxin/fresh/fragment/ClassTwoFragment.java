package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentClasstwoBinding;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.vm.ClassTwoViewModel;
import com.yanzhenjie.sofia.Sofia;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BR;

public class ClassTwoFragment extends BaseFragment<FragmentClasstwoBinding, ClassTwoViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_classtwo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.target_id.set(getArguments().getString("target_id"));
        viewModel.two_class_name();
    }
}
