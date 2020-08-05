package com.dingxin.fresh.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentMorderBinding;
import com.dingxin.fresh.vm.MOrderViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.BR;

public class MOrderFragment extends BaseFragment<FragmentMorderBinding, MOrderViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_morder;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
