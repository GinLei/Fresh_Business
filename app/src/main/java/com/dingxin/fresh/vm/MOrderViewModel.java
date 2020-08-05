package com.dingxin.fresh.vm;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.clj.fastble.BleManager;
import com.dingxin.fresh.fragment.CompletedFragment;
import com.dingxin.fresh.fragment.MWeighFragment;
import com.dingxin.fresh.fragment.PosFragment;
import com.dingxin.fresh.fragment.PrintFragment;
import com.dingxin.fresh.fragment.ScalesFragment;
import com.dingxin.fresh.utils.AppUtils;

import java.time.Instant;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MOrderViewModel extends BaseViewModel {
    public BindingCommand to_weight = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!AppUtils.isBindScale()) {
                if (!BleManager.getInstance().isBlueEnable()) {
                    ToastUtils.showShort("请开启蓝牙");
                    return;
                }
                startContainerActivity(ScalesFragment.class.getCanonicalName());
            } else {
                startContainerActivity(MWeighFragment.class.getCanonicalName());
            }
        }
    });
    public BindingCommand to_print = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!AppUtils.isBindPrint()) {
                if (!BleManager.getInstance().isBlueEnable()) {
                    ToastUtils.showShort("请开启蓝牙");
                    return;
                }
                startContainerActivity(PosFragment.class.getCanonicalName());
            } else {
                startContainerActivity(PrintFragment.class.getCanonicalName());
            }
        }
    });
    public BindingCommand to_completed = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(CompletedFragment.class.getCanonicalName());
        }
    });

    public MOrderViewModel(@NonNull Application application) {
        super(application);
    }
}
