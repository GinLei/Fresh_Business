package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentMineBinding;
import com.dingxin.fresh.vm.MineViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.jessyan.autosize.internal.CustomAdapt;

public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> implements CustomAdapt {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 724;
    }

    @Override
    public void initData() {
        binding.SwipeRefreshLayout.setColorSchemeResources(R.color.color_orange_2);
        binding.SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.RefreshAccountInfo();
            }
        });
        viewModel.getAccountInfo();
    }

    @Override
    public void initViewObservable() {
        viewModel.refresh_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.SwipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.switch_event.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null) binding.Switch.setChecked(aBoolean);
            }
        });
        viewModel.to_reservation_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                new MaterialDialog.Builder(getActivity()).title("预约订单")
                        .items(new String[]{"明天", "后天"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Bundle bundle = new Bundle();
                                switch (text.toString()) {
                                    case "明天":
                                        bundle.putString("day", "1");
                                        break;
                                    case "后天":
                                        bundle.putString("day", "2");
                                        break;
                                }
                                startContainerActivity(ReservationFragment.class.getCanonicalName(), bundle);
                                return true;
                            }
                        }).show();
            }
        });
    }
}
