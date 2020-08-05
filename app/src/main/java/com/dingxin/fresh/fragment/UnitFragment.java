package com.dingxin.fresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentUnitBinding;
import com.dingxin.fresh.e.CommonEntity;
import com.dingxin.fresh.e.UnitEntity;
import com.dingxin.fresh.vm.UnitViewModel;
import com.yanzhenjie.sofia.Sofia;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BR;

public class UnitFragment extends BaseFragment<FragmentUnitBinding, UnitViewModel> {
    private TagAdapter<UnitEntity> unitAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Sofia.with(getActivity()).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        return R.layout.fragment_unit;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.target_id.set(getArguments().getString("target_id"));
        viewModel.unit_name();
    }

    @Override
    public void initViewObservable() {
        viewModel.unit_data.observe(getViewLifecycleOwner(), new Observer<List<UnitEntity>>() {
            @Override
            public void onChanged(List<UnitEntity> units) {
                if (units != null) {
                    unitAdapter = new TagAdapter<UnitEntity>(units) {
                        @Override
                        public View getView(FlowLayout parent, int position, UnitEntity unitEntity) {
                            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.layout_tag,
                                    parent, false);
                            tv.setText(unitEntity.getUnit());
                            return tv;
                        }
                    };
                    binding.tab.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            Set<Integer> selectedList = binding.tab.getSelectedList();
                            if (selectedList.size() != 0) {
                                viewModel.unit_submit.set(UnitFragment.this.unitAdapter.getItem(position));
                            } else {
                                viewModel.unit_submit.set(null);
                            }
                            return false;
                        }
                    });
                    binding.tab.setAdapter(unitAdapter);
                }
            }
        });
    }
}
