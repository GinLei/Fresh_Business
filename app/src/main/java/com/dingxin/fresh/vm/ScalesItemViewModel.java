package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.ScalesEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ScalesItemViewModel extends ItemViewModel<ScalesViewModel> {
    public ObservableField<ScalesEntity> scalesEntityObservableField = new ObservableField<>();

    public ScalesItemViewModel(@NonNull ScalesViewModel viewModel, ScalesEntity entity) {
        super(viewModel);
        scalesEntityObservableField.set(entity);
    }

    public BindingCommand OnItemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.name.set(scalesEntityObservableField.get().getScale_name());
            viewModel.scale.set(scalesEntityObservableField.get().getScale_mac());
            viewModel.bind_scale();
        }
    });
}
