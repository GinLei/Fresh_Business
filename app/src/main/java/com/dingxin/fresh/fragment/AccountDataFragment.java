package com.dingxin.fresh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentAccountdataBinding;
import com.dingxin.fresh.vm.AccountDataViewModel;
import com.yanzhenjie.sofia.Sofia;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.jessyan.autosize.internal.CustomAdapt;


public class AccountDataFragment extends BaseFragment<FragmentAccountdataBinding, AccountDataViewModel> implements CustomAdapt {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_accountdata;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.RequestBusinessInfoData();
    }

    @Override
    public void initViewObservable() {
        viewModel.finishEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                getActivity().finish();
            }
        });
        viewModel.modificationEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Bundle bundle = new Bundle();
                bundle.putString("real_name", viewModel.real_name.get());
                bundle.putString("mobile", viewModel.mobile.get());
                bundle.putString("bazaar_name", viewModel.bazaar_name.get());
                bundle.putString("market_shop_content", viewModel.market_shop_content.get());
                bundle.putString("booth_image", viewModel.booth_image.get());
                startContainerActivity(ModifyAccountDataFragment.class.getCanonicalName(), bundle);
            }
        });
        viewModel.PhotoEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Uri", ImageUtils.getImagePath(Uri.parse(s), getActivity()));
                    startContainerActivity(PhotoFragment.class.getCanonicalName(), bundle);
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
