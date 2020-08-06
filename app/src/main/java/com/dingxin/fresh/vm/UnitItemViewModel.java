package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.UnitEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class UnitItemViewModel extends ItemViewModel<UnitViewModel> {
    public static final String TAG = "com.dingxin.fresh.vm.UnitViewModel";
    public ObservableField<UnitEntity> entityObservableField = new ObservableField<>();
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(entityObservableField.get(), TAG);
            viewModel.finish();
        }
    });

    public UnitItemViewModel(@NonNull UnitViewModel viewModel, UnitEntity entity) {
        super(viewModel);
        entityObservableField.set(entity);
    }
}
