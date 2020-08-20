package com.dingxin.fresh.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.MWeightOrderAdapter;
import com.dingxin.fresh.databinding.FragmentMweighBinding;
import com.dingxin.fresh.e.WeightEntity;
import com.dingxin.fresh.vm.MWeighViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;
import me.tatarka.bindingcollectionadapter2.BR;

public class MWeighFragment extends BaseFragment<FragmentMweighBinding, MWeighViewModel> {
    private MWeightOrderAdapter adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_mweigh;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.RefreshWeightData();
        binding.SmartRefreshLayout.setEnableLoadMore(false);
        binding.SmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.LoadMoreWeightData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                adapter = null;
                viewModel.RefreshWeightData();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.data_event.observe(getViewLifecycleOwner(), new Observer<List<WeightEntity>>() {
            @Override
            public void onChanged(List<WeightEntity> list) {
                if (list != null) {
                    if (list.size() == viewModel.pageSize) {
                        binding.SmartRefreshLayout.setEnableLoadMore(true);
                    } else {
                        binding.SmartRefreshLayout.setEnableLoadMore(false);
                    }
                    if (adapter == null) {
                        binding.rv.setAdapter(adapter = new MWeightOrderAdapter(getContext(), list, viewModel));
                        binding.SmartRefreshLayout.finishRefresh();
                    } else {
                        binding.SmartRefreshLayout.finishLoadMore();
                        adapter.getList().addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        viewModel.phone_event.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
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
        viewModel.weight_event.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                if (list != null) {
                    List<WeightEntity> entities = adapter.getList();
                    for (int i = 0; i < entities.size(); i++) {
                        WeightEntity entity = entities.get(i);
                        if (TextUtils.equals(viewModel.order_id.get(), String.valueOf(entity.getOrder_id()))) {
                            List<WeightEntity.OrderGoodsListBean> order_goods_list = entity.getOrder_goods_list();
                            for (int j = 0; j < order_goods_list.size(); j++) {
                                WeightEntity.OrderGoodsListBean goodsListBean = order_goods_list.get(j);
                                if (TextUtils.equals(viewModel.spec_id.get(), goodsListBean.getSpec_id())) {
                                    goodsListBean.setWeight(list.get(0));
                                    goodsListBean.setMessage(list.get(1));
                                    goodsListBean.setWeight_status(1);
                                    adapter.notifyDataSetChanged();
                                    viewModel.lock.set(true);
                                    return;
                                }
                            }
                        }
                    }
                } else {
                    viewModel.lock.set(true);
                }
            }
        });
        viewModel.cancel_event.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    List<WeightEntity> entities = adapter.getList();
                    for (int i = 0; i < entities.size(); i++) {
                        WeightEntity entity = entities.get(i);
                        if (TextUtils.equals(viewModel.order_id.get(), String.valueOf(entity.getOrder_id()))) {
                            List<WeightEntity.OrderGoodsListBean> order_goods_list = entity.getOrder_goods_list();
                            for (int j = 0; j < order_goods_list.size(); j++) {
                                WeightEntity.OrderGoodsListBean goodsListBean = order_goods_list.get(j);
                                if (TextUtils.equals(viewModel.spec_id.get(), goodsListBean.getSpec_id())) {
                                    goodsListBean.setIs_canceled(integer.intValue());
                                    adapter.notifyDataSetChanged();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        });
        viewModel.commit_weight_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<WeightEntity> entities = adapter.getList();
                for (int i = 0; i < entities.size(); i++) {
                    WeightEntity entity = entities.get(i);
                    if (TextUtils.equals(viewModel.order_id.get(), String.valueOf(entity.getOrder_id()))) {
                        entities.remove(entity);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }
}
