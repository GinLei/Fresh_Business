package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.adapter.BannerImageAdapter;
import com.dingxin.fresh.databinding.FragmentHomeBinding;
import com.dingxin.fresh.e.BannerEntity;
import com.dingxin.fresh.l.BannerDataEnable;
import com.dingxin.fresh.vm.HomeViewModel;
import com.yanzhenjie.sofia.Sofia;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.Indicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.jessyan.autosize.internal.CustomAdapt;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements CustomAdapt {
    public static final String TAG = "HomeFragment";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).statusBarBackgroundAlpha(0).invasionStatusBar();
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        IndicatorConfig.Margins margins = new IndicatorConfig.Margins();
        margins.bottomMargin = 36;
        binding.banner.addBannerLifecycleObserver(getViewLifecycleOwner())
                .setIndicator(new RectangleIndicator(getContext()))
                .setIndicatorSelectedColorRes(R.color.white)
                .setIndicatorSpace(30)
                .setIndicatorHeight(9)
                .setIndicatorNormalWidth(42)
                .setIndicatorSelectedWidth(72)
                .setIndicatorMargins(margins)
                .start();

        binding.SwipeRefreshLayout.setColorSchemeResources(R.color.color_orange_2);
        binding.SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshBannerData();
            }
        });
        viewModel.RequestBannerData();
    }

    @Override
    public void initViewObservable() {
        viewModel.event.observe(getViewLifecycleOwner(), new Observer<List<BannerEntity>>() {
            @Override
            public void onChanged(List<BannerEntity> bannerEntities) {
                if (bannerEntities != null) {
                    IndicatorConfig.Margins margins = new IndicatorConfig.Margins();
                    margins.bottomMargin = 36;
                    binding.banner.setAdapter(new BannerImageAdapter(bannerEntities));
                }
            }
        });
        viewModel.refresh_event.observe(getViewLifecycleOwner(), new Observer<List<BannerEntity>>() {
            @Override
            public void onChanged(List<BannerEntity> bannerEntities) {
                if (bannerEntities != null) {
                    IndicatorConfig.Margins margins = new IndicatorConfig.Margins();
                    margins.bottomMargin = 36;
                    binding.banner.setAdapter(new BannerImageAdapter(bannerEntities));
                }
                binding.SwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 690;
    }
}
