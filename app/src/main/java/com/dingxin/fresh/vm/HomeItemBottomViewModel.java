package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.AccountInfoEntity;
import com.dingxin.fresh.e.EntryEntity;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class HomeItemBottomViewModel extends ItemViewModel<HomeViewModel> {
    public final ObservableField<AccountInfoEntity> entityObservableField = new ObservableField<>();

    public HomeItemBottomViewModel(@NonNull HomeViewModel viewModel, AccountInfoEntity entity) {
        super(viewModel);
        entityObservableField.set(entity);
    }
}
