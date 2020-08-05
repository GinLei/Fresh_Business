package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.ScalesEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class PosItemViewModel extends ItemViewModel<PosViewModel> {
    public ObservableField<ScalesEntity> scalesEntityObservableField = new ObservableField<>();

    public PosItemViewModel(@NonNull PosViewModel viewModel, ScalesEntity entity) {
        super(viewModel);
        scalesEntityObservableField.set(entity);
    }

    public BindingCommand OnItemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.pos.set(scalesEntityObservableField.get().getScale_mac());
            viewModel.name.set(scalesEntityObservableField.get().getScale_name());
            viewModel.bind_pos();
        }
    });
}
