package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentReservationBinding;
import com.dingxin.fresh.e.ReservationEntity;
import com.dingxin.fresh.vm.ReservationViewModel;
import com.google.android.material.tabs.TabLayout;
import com.yanzhenjie.sofia.Sofia;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BR;

public class ReservationFragment extends BaseFragment<FragmentReservationBinding, ReservationViewModel> {
    private LayoutInflater inflater;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().invasionNavigationBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_reservation;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.day.set(getArguments().getString("day"));
        inflater = getLayoutInflater();
        viewModel.get_apk_schedule_goodslist();

    }

    @Override
    public void initViewObservable() {

        viewModel.data_event.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.tab.removeAllTabs();
                TabLayout.Tab tab_1 = binding.tab.newTab().setText("订单");
                TabLayout.Tab tab_2 = binding.tab.newTab().setText("统计");
                binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        binding.container.removeAllViews();
                        switch (tab.getText().toString()) {
                            case "订单":
                                List<ReservationEntity.OrderListBean> order_list = viewModel.reservation_entity.get().getOrder_list();
                                for (ReservationEntity.OrderListBean orderListBean : order_list) {
                                    View layout_reservation_order = inflater.inflate(R.layout.layout_reservation_order, null);
                                    TextView tv1 = layout_reservation_order.findViewById(R.id.tv_1);
                                    tv1.setText(orderListBean.getTime());
                                    LinearLayout container = layout_reservation_order.findViewById(R.id.container);
                                    List<ReservationEntity.OrderListBean.OrderGoodsBean> order_goods = orderListBean.getOrder_goods();
                                    for (ReservationEntity.OrderListBean.OrderGoodsBean orderGoodsBean : order_goods) {
                                        View layout_reservation_order_child = inflater.inflate(R.layout.layout_reservation_order_child, null);
                                        ImageView img = layout_reservation_order_child.findViewById(R.id.img);
                                        Glide.with(getActivity()).load(orderGoodsBean.getThumb()).into(img);
                                        TextView tv_1 = layout_reservation_order_child.findViewById(R.id.tv_1);
                                        TextView tv_2 = layout_reservation_order_child.findViewById(R.id.tv_2);
                                        TextView tv_3 = layout_reservation_order_child.findViewById(R.id.tv_3);
                                        TextView tv_4 = layout_reservation_order_child.findViewById(R.id.tv_4);
                                        tv_1.setText(orderGoodsBean.getGoods_name());
                                        //tv_2.setText("¥" + orderGoodsBean.getGoods_price());
                                        container.addView(layout_reservation_order_child);
                                    }
                                    binding.container.addView(layout_reservation_order);
                                }
                                break;
                            case "统计":
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
            }
        });
    }
}
