package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.GreensEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ClassOneItemViewModel extends ItemViewModel<ClassOneViewModel> {
    public static final String TAG = "com.dingxin.fresh.vm.ClassOneViewModel";
    public ObservableField<CommonEntity> entityObservableField = new ObservableField<>();
    public BindingCommand submit_command = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(entityObservableField.get(), TAG);
            viewModel.finish();
        }
    });

    public ClassOneItemViewModel(@NonNull ClassOneViewModel viewModel, CommonEntity entity) {
        super(viewModel);
        entityObservableField.set(entity);
    }
}
