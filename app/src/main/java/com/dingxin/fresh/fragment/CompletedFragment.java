package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.CompletedAdapter;
import com.dingxin.fresh.adapter.PrintOrderAdapter;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.databinding.FragmentCompletedBinding;
import com.dingxin.fresh.e.CompletedEntity;
import com.dingxin.fresh.e.GreensEntity;
import com.dingxin.fresh.e.PrintEntity;
import com.dingxin.fresh.utils.RetrofitClient;
import com.dingxin.fresh.vm.CompletedViewModel;
import com.dingxin.fresh.vm.GoodsListItemViewModel;
import com.dingxin.fresh.vm.GoodsListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CompletedFragment extends BaseFragment<FragmentCompletedBinding, CompletedViewModel> implements CustomAdapt {
    private CompletedAdapter adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().invasionNavigationBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_completed;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        viewModel.refresh_finishList();
        binding.SmartRefreshLayout.setEnableLoadMore(false);
        binding.SmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.loadMore_finishList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                adapter = null;
                viewModel.refresh_finishList();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.dataEvent.observe(this, new Observer<List<CompletedEntity>>() {
            @Override
            public void onChanged(List<CompletedEntity> completedEntities) {
                if (completedEntities != null) {
                    if (completedEntities.size() == viewModel.pageSize) {
                        binding.SmartRefreshLayout.setEnableLoadMore(true);
                    } else {
                        binding.SmartRefreshLayout.setEnableLoadMore(false);
                    }
                    if (adapter == null) {
                        adapter = new CompletedAdapter(getActivity(), completedEntities, viewModel);
                        binding.rv.setAdapter(adapter);
                        binding.SmartRefreshLayout.finishRefresh();
                    } else {
                        binding.SmartRefreshLayout.finishLoadMore();
                        adapter.getList().addAll(completedEntities);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        viewModel.phoneEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    new RxPermissions(getActivity()).request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Uri data = Uri.parse("tel:" + s);
                                intent.setData(data);
                                startActivity(intent);
                            }
                        }
                    });
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
        return 727;
    }
}
