package com.dingxin.fresh.vm;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dingxin.fresh.R;
import com.dingxin.fresh.fragment.AccountDataFragment;
import com.dingxin.fresh.fragment.GoodsListFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class HomeItemViewModel extends ItemViewModel<HomeViewModel> {
    public Drawable drawable;
    public String text;
    public SingleLiveEvent route_event = new SingleLiveEvent();

    public HomeItemViewModel(@NonNull HomeViewModel viewModel, Drawable drawable, String text) {
        super(viewModel);
        this.drawable = drawable;
        this.text = text;
    }

    public BindingCommand bindingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(text, HomeItemViewModel.class.getSimpleName());
        }
    });
//    public BindingCommand one = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            viewModel.startContainerActivity(AccountDataFragment.class.getCanonicalName());
//        }
//    });
//    public BindingCommand two = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//
//        }
//    });
//    public BindingCommand three = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName());
//        }
//    });
//    public BindingCommand four = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//
//        }
//    });
}
