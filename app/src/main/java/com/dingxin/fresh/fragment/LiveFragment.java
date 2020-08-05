package com.dingxin.fresh.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentLiveBinding;
import com.dingxin.fresh.vm.LiveViewModel;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LiveFragment extends BaseFragment<FragmentLiveBinding, LiveViewModel> {
    private TXLivePushConfig config;
    private String rtmpUrl = "rtmp://107748.livepush.myqcloud.com/live/xiandaojia?txSecret=4b8ed2d233149b5d60f8a2d4bb7d6dee&txTime=5F1BE66F";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_live;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        TXLivePusher mLivePusher = new TXLivePusher(getActivity());
        config = new TXLivePushConfig();
        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {

            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
        mLivePusher.setConfig(config);
        mLivePusher.startCameraPreview(binding.TXCloudVideoView);
        mLivePusher.startScreenCapture();
        ToastUtils.showShort("" + mLivePusher.startPusher(rtmpUrl));
    }
}
