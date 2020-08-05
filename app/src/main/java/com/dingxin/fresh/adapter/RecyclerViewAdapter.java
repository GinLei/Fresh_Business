package com.dingxin.fresh.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class RecyclerViewAdapter extends BindingRecyclerViewAdapter {
    @Override
    public void onBindBinding(@NonNull ViewDataBinding binding, int variableId, int layoutRes, int position, Object item) {
        getItemBinding().bind(binding, item);
    }
}
