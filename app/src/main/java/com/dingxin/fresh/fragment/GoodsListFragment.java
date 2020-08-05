package com.dingxin.fresh.fragment;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.GreensAdapter;
import com.dingxin.fresh.databinding.FragmentGoodslistBinding;
import com.dingxin.fresh.e.SpecsEntity;
import com.dingxin.fresh.vm.GoodsListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;


public class GoodsListFragment extends BaseFragment<FragmentGoodslistBinding, GoodsListViewModel> implements CustomAdapt {
    public static final String TAG = "GoodsListFragment";
    private GreensAdapter adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_goodslist;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        viewModel.greens_list_refresh();
        binding.SmartRefreshLayout.setEnableLoadMore(false);
        binding.SmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.greens_list_loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.greens_list_refresh();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.specs.observe(getViewLifecycleOwner(), new Observer<List<SpecsEntity>>() {
            @Override
            public void onChanged(List<SpecsEntity> specsEntities) {
                if (specsEntities != null) {
                    new MaterialDialog.Builder(getContext()).title("规格列表").adapter(adapter = new GreensAdapter(getContext(), specsEntities, viewModel), new LinearLayoutManager(getContext())).show();
                }
            }
        });
        viewModel.Refresh_Event.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                if (isLoadMore != null) {
                    binding.SmartRefreshLayout.setEnableLoadMore(isLoadMore);
                    binding.SmartRefreshLayout.finishRefresh();
                }
            }
        });
        viewModel.LoadMore_Event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.SmartRefreshLayout.finishLoadMore();
            }
        });
        viewModel.spec_sale_event.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null) {
                    List<SpecsEntity> list = adapter.getList();
                    for (SpecsEntity entity : list) {
                        if (TextUtils.equals(entity.getSpec_id(), viewModel.spec_id.get())) {
                            entity.setIs_on_sale(aBoolean);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }
        });
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
