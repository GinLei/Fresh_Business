package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentBalanceBinding;
import com.dingxin.fresh.vm.BalanceViewModel;
import com.google.android.material.tabs.TabLayout;
import com.yanzhenjie.sofia.Sofia;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;

public class BalanceFragment extends BaseFragment<FragmentBalanceBinding, BalanceViewModel> implements CustomAdapt {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_balance;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.getAccountInfo();
    }

    @Override
    public void initViewObservable() {
        viewModel.dataEvent.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.tab.removeAllTabs();
                TabLayout.Tab tab_1 = binding.tab.newTab().setText("今日统计");
                TabLayout.Tab tab_2 = binding.tab.newTab().setText("本周统计");
                TabLayout.Tab tab_3 = binding.tab.newTab().setText("本月统计");
                TabLayout.Tab tab_4 = binding.tab.newTab().setText("本季统计");
                binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getText().toString()) {
                            case "今日统计":
                                viewModel.count.set(viewModel.entityObservableField.get().getList1().getCount());
                                viewModel.amount.set(viewModel.entityObservableField.get().getList1().getAmount());
                                viewModel.weight.set(viewModel.entityObservableField.get().getList1().getWeight());
                                viewModel.after_service.set(viewModel.entityObservableField.get().getList1().getAfter_service());
                                break;
                            case "本周统计":
                                viewModel.count.set(viewModel.entityObservableField.get().getList2().getCount());
                                viewModel.amount.set(viewModel.entityObservableField.get().getList2().getAmount());
                                viewModel.weight.set(viewModel.entityObservableField.get().getList2().getWeight());
                                viewModel.after_service.set(viewModel.entityObservableField.get().getList2().getAfter_service());
                                break;
                            case "本月统计":
                                viewModel.count.set(viewModel.entityObservableField.get().getList3().getCount());
                                viewModel.amount.set(viewModel.entityObservableField.get().getList3().getAmount());
                                viewModel.weight.set(viewModel.entityObservableField.get().getList3().getWeight());
                                viewModel.after_service.set(viewModel.entityObservableField.get().getList3().getAfter_service());
                                break;
                            case "本季统计":
                                viewModel.count.set(viewModel.entityObservableField.get().getList4().getCount());
                                viewModel.amount.set(viewModel.entityObservableField.get().getList4().getAmount());
                                viewModel.weight.set(viewModel.entityObservableField.get().getList4().getWeight());
                                viewModel.after_service.set(viewModel.entityObservableField.get().getList4().getAfter_service());
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                binding.tab.addTab(tab_1, true);
                binding.tab.addTab(tab_2);
                binding.tab.addTab(tab_3);
                binding.tab.addTab(tab_4);
            }
        });
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 724;
    }
}
