package com.dingxin.fresh.vm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.dingxin.fresh.activity.MainActivity;
import com.dingxin.fresh.api.ApiService;
import com.dingxin.fresh.e.LoginEntity;
import com.dingxin.fresh.e.ScalesEntity;
import com.dingxin.fresh.fragment.InsideFragment;
import com.dingxin.fresh.utils.RetrofitClient;
import com.google.gson.Gson;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;

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
