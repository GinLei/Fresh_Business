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
                                        tv_2.setText(orderGoodsBean.getGoods_price());
                                        tv_3.setText(orderGoodsBean.getGreens_weight());
                                        tv_4.setText("总价:" + orderGoodsBean.getFinal_price());
                                        container.addView(layout_reservation_order_child);
                                    }
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 30, 0, 0);
                                    layout_reservation_order.setLayoutParams(params);
                                    binding.container.addView(layout_reservation_order);
                                }
                                break;
                            case "统计":
                                ReservationEntity.StatisticsBean statistics = viewModel.reservation_entity.get().getStatistics();
                                View layout_reservation_count = inflater.inflate(R.layout.layout_reservation_count, null);
                                TextView tv_1 = layout_reservation_count.findViewById(R.id.tv_1);
                                TextView tv_2 = layout_reservation_count.findViewById(R.id.tv_2);
                                tv_1.setText("预付款:" + statistics.getTotal_price());
                                tv_2.setText("总重量:" + statistics.getGreens_weight());
                                LinearLayout container = layout_reservation_count.findViewById(R.id.container);
                                List<ReservationEntity.StatisticsBean.WeightListBean> weight_list = statistics.getWeight_list();
                                for (ReservationEntity.StatisticsBean.WeightListBean weightListBean : weight_list) {
                                    View layout_reservation_count_child = inflater.inflate(R.layout.layout_reservation_count_child, null);
                                    ImageView img = layout_reservation_count_child.findViewById(R.id.img);
                                    Glide.with(getActivity()).load(weightListBean.getThumb()).into(img);
                                    TextView tv1 = layout_reservation_count_child.findViewById(R.id.tv_1);
                                    TextView tv2 = layout_reservation_count_child.findViewById(R.id.tv_2);
                                    TextView tv3 = layout_reservation_count_child.findViewById(R.id.tv_3);
                                    tv1.setText(weightListBean.getGoods_name());
                                    tv2.setText(weightListBean.getGoods_price());
                                    tv3.setText(weightListBean.getGreens_weight());
                                    container.addView(layout_reservation_count_child);
                                }
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(0, 30, 0, 0);
                                layout_reservation_count.setLayoutParams(params);
                                binding.container.addView(layout_reservation_count);
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
