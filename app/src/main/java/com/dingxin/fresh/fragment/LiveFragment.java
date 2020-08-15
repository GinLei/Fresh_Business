package com.dingxin.fresh.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.dingxin.fresh.BR;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.FragmentLiveBinding;
import com.dingxin.fresh.vm.LiveViewModel;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.graphics.BitmapFactory.decodeResource;

public class LiveFragment extends BaseFragment<FragmentLiveBinding, LiveViewModel> implements ITXLivePushListener {
    private TXLivePushConfig config;
    private String rtmpUrl = "rtmp://www.7xdaojia.com/live/110?txSecret=294ddea5e0853026ea9d453c0165c333&txTime=5F38D3C5";
    private boolean mIsPushing;
    private TXLivePusher mLivePusher;
    private int mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960;
    private int mBeautyLevel = 5;                                   // 美颜等级
    private int mBeautyStyle = TXLiveConstants.BEAUTY_STYLE_SMOOTH; // 美颜样式
    private int mWhiteningLevel = 3;                                // 美白等级
    private int mRuddyLevel = 2;                                    // 红润等级
    private boolean mFrontCamera = true;

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
        mLivePusher = new TXLivePusher(getContext());
        config = new TXLivePushConfig();
        binding.livepusherBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsPushing) {
                    startRTMPPush();
                } else {
                    stopRTMPPush();
                }
            }
        });
        binding.livepusherBtnSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null || (Boolean) v.getTag()) {
                    v.setTag(false);
                    v.setBackgroundResource(R.mipmap.livepusher_camera_back_btn);
                    mFrontCamera = false;
                } else {
                    v.setTag(true);
                    v.setBackgroundResource(R.mipmap.livepusher_camera_front);
                    mFrontCamera = true;
                }
                mLivePusher.switchCamera();
            }
        });
    }

    private boolean startRTMPPush() {
        binding.TXCloudVideoView.setVisibility(View.VISIBLE);
        // 添加播放回调
        mLivePusher.setPushListener(this);
        // 添加后台垫片推流参数
        Bitmap bitmap = decodeResource(getResources(), R.mipmap.livepusher_pause_publish);
        config.setPauseImg(bitmap);
        config.setPauseImg(300, 5);
        // 设置推流分辨率
        config.setVideoResolution(mCurrentVideoResolution);

        // 设置美颜
        mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);

        // 开启麦克风推流相关
        mLivePusher.setMute(false);

        mLivePusher.setRenderRotation(0);
        // 是否开启观众端镜像观看
        mLivePusher.setMirror(false);

        // 是否打开曝光对焦
        config.setTouchFocus(true);
        // 是否打开手势放大预览画面
        config.setEnableZoom(true);
        // 设置推流配置
        mLivePusher.setConfig(config);
        // 设置本地预览View
        mLivePusher.startCameraPreview(binding.TXCloudVideoView);
        if (!mFrontCamera) mLivePusher.switchCamera();
        // 发起推流
        int ret = mLivePusher.startPusher(rtmpUrl);
        if (ret == -5) {
            String errInfo = getString(R.string.livepusher_license_check_fail);
            int start = (errInfo + getString(R.string.livepusher_license_click_info)).length();
            int end = (errInfo + getString(R.string.livepusher_license_click_use_info)).length();
            SpannableStringBuilder spannableStrBuidler = new SpannableStringBuilder(errInfo + getString(R.string.livepusher_license_click_use_info));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://cloud.tencent.com/document/product/454/34750");
                    intent.setData(content_url);
                    startActivity(intent);
                }
            };
            spannableStrBuidler.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStrBuidler.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView tv = new TextView(getContext());
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(spannableStrBuidler);
            tv.setPadding(20, 0, 20, 0);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            dialogBuilder.setTitle(getString(R.string.livepusher_push_fail)).setView(tv).setPositiveButton(getString(R.string.livepusher_comfirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    stopRTMPPush();
                }
            });
            dialogBuilder.show();
            return false;
        }
        TXLivePushConfig config = mLivePusher.getConfig();
        mLivePusher.setConfig(config);
        mIsPushing = true;
        binding.livepusherBtnStart.setBackgroundResource(R.mipmap.livepusher_stop);
        return true;
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
        if (event < 0) {
            Toast.makeText(getContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT
                || event == TXLiveConstants.PUSH_ERR_INVALID_ADDRESS
                || event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL
                || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL) {
            stopRTMPPush();
        } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            Toast.makeText(getContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            config.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
            mLivePusher.setConfig(config);
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
        } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
        } else if (event == TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC) {
            mLivePusher.turnOnFlashLight(false);
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    private void stopRTMPPush() {
        mLivePusher.stopBGM();
        mLivePusher.stopCameraPreview(true);
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
        binding.TXCloudVideoView.setVisibility(View.GONE);
        config.setPauseImg(null);
        mIsPushing = false;
        binding.livepusherBtnStart.setBackgroundResource(R.mipmap.livepusher_start);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            stopRTMPPush();
        }
    }
}
