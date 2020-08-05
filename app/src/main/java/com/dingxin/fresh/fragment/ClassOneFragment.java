package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentClassoneBinding;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.vm.ClassOneViewModel;
import com.yanzhenjie.sofia.Sofia;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BR;

public class ClassOneFragment extends BaseFragment<FragmentClassoneBinding, ClassOneViewModel> {
    private TagAdapter<CommonEntity> tagAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_classone;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.target_id.set(getArguments().getString("target_id"));
        viewModel.one_class_name();
    }

    @Override
    public void initViewObservable() {
        viewModel.target_data.observe(getViewLifecycleOwner(), new Observer<List<CommonEntity>>() {
            @Override
            public void onChanged(List<CommonEntity> commonEntities) {
                if (commonEntities != null) {
                    tagAdapter = new TagAdapter<CommonEntity>(commonEntities) {
                        @Override
                        public View getView(FlowLayout parent, int position, CommonEntity commonEntity) {
                            TextView tag = (TextView) getLayoutInflater().inflate(R.layout.layout_tag,
                                    parent, false);
                            tag.setText(commonEntity.getName());
                            return tag;
                        }
                    };
                    binding.tab.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            Set<Integer> selectedList = binding.tab.getSelectedList();
                            if (selectedList.size() != 0) {
                                viewModel.target_submit.set(tagAdapter.getItem(position));
                            } else {
                                viewModel.target_submit.set(null);
                            }
                            return false;
                        }
                    });
                    binding.tab.setAdapter(tagAdapter);
                }
            }
        });
    }
}
