package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.CommonEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class ClassTwoItemViewModel extends ItemViewModel<ClassTwoViewModel> {
    public static final String TAG = "com.dingxin.fresh.vm.ClassTwoViewModel";
    public ObservableField<CommonEntity> entityObservableField = new ObservableField<>();
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(entityObservableField.get(), TAG);
            viewModel.finish();
        }
    });

    public ClassTwoItemViewModel(@NonNull ClassTwoViewModel viewModel, CommonEntity entity) {
        super(viewModel);
        entityObservableField.set(entity);
    }
}
