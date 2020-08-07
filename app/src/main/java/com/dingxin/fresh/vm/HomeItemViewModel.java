package com.dingxin.fresh.vm;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dingxin.fresh.R;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class HomeItemViewModel extends ItemViewModel<HomeViewModel> {
    public Drawable home_one, home_two, home_three, home_four;
    public String text_one, text_two, text_three, text_four;

    public HomeItemViewModel(@NonNull HomeViewModel viewModel) {
        super(viewModel);
        home_one = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.home_one);
        home_two = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.home_two);
        home_three = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.home_three);
        home_four = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.home_four);
        text_one = "用户资料";
        text_two = "摊位管理";
        text_three = "商品管理";
        text_four = "用户资料";
    }
}
