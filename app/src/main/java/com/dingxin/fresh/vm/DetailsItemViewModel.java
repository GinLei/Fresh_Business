package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.DetailEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class DetailsItemViewModel extends ItemViewModel<DetailsViewModel> {
    public ObservableField<DetailEntity> observableField = new ObservableField<>();

    public DetailsItemViewModel(@NonNull DetailsViewModel viewModel, DetailEntity entity) {
        super(viewModel);
        observableField.set(entity);
    }
}
