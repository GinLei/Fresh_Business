package com.dingxin.fresh.vm;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.e.EntryEntity;
import com.dingxin.fresh.fragment.AccountDataFragment;
import com.dingxin.fresh.fragment.GoodsListFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HomeItemTopViewModel extends ItemViewModel<HomeViewModel> {
    public final ObservableField<EntryEntity> entryEntities = new ObservableField<>();
    public Drawable drawable;

    public HomeItemTopViewModel(@NonNull HomeViewModel viewModel, EntryEntity entity) {
        super(viewModel);
        entryEntities.set(entity);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), entryEntities.get().getIcon());
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (entryEntities.get().getUrl()) {
                case "0":
                    viewModel.startContainerActivity(AccountDataFragment.class.getCanonicalName());
                    break;
                case "1":
                    break;
                case "2":
                    viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName());
                    break;
                case "3":
                    break;
            }
            ;
        }
    });
}
