package com.dingxin.fresh.vm;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.CommonEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class GoodsPicItemViewModel extends ItemViewModel<GoodsPicViewModel> {
    public ObservableField<CommonEntity> entityObservableField = new ObservableField<>();
    public BindingCommand pic_click = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(entityObservableField.get().getThumb(), GoodsPicItemViewModel.class.getSimpleName());
            viewModel.finish();
        }
    });

    public GoodsPicItemViewModel(@NonNull GoodsPicViewModel viewModel, CommonEntity entity) {
        super(viewModel);
        entityObservableField.set(entity);
    }
}
