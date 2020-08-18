package com.dingxin.fresh.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dingxin.fresh.BR;
import com.dingxin.fresh.J.JUtil;
import com.dingxin.fresh.R;
import com.dingxin.fresh.databinding.ActivityLoginBinding;
import com.dingxin.fresh.vm.LoginViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.sofia.Sofia;

import java.util.Locale;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements CustomAdapt {
    private CountDownTimer timer;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0);
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        //JUtil.requestPermission(this);
        ignoreBatteryOptimization(this);
        new RxPermissions(this).request(Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {

            }
        });
        MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this).title("允许应用自启动");
        builder.positiveText("确认").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dismissDialog();
            }
        });
        if (isHuawei()) {
            builder.content("操作步骤：应用启动管理 -> 关闭应用开关 -> 打开允许自启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goHuaweiSetting();
                }
            });
        } else if (isXiaomi()) {
            builder.content("操作步骤：授权管理 -> 自启动管理 -> 允许应用自启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goXiaomiSetting();
                }
            });
        } else if (isOPPO()) {
            builder.content("操作步骤：权限隐私 -> 自启动管理 -> 允许应用自启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goOPPOSetting();
                }
            });
        } else if (isVIVO()) {
            builder.content("操作步骤：权限管理 -> 自启动 -> 允许应用自启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goVIVOSetting();
                }
            });
        } else if (isMeizu()) {
            builder.content("操作步骤：权限管理 -> 后台管理 -> 点击应用 -> 允许后台运行");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goMeizuSetting();
                }
            });
        } else if (isSamsung()) {
            builder.content("操作步骤：自动运行应用程序 -> 打开应用开关 -> 电池管理 -> 未监视的应用程序 -> 添加应用");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goSamsungSetting();
                }
            });
        } else if (isLeTV()) {
            builder.content("操作步骤：自启动管理 -> 允许应用自启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goLetvSetting();
                }
            });
        } else if (isSmartisan()) {
            builder.content("操作步骤：权限管理 -> 自启动权限管理 -> 点击应用 -> 允许被系统启动");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    goSmartisanSetting();
                }
            });
        }
        builder.show();
    }


    public void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
                /**
                 * 判断当前APP是否有加入电池优化的白名单，
                 * 如果没有，弹出加入电池优化的白名单的设置对话框
                 * */
                if (!hasIgnored) {
                    Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initViewObservable() {
        viewModel.yzEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.btYz.setClickable(false);
                timer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btYz.setEnabled(false);
                        binding.btYz.setText("已发送(" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        binding.btYz.setClickable(true);
                        binding.btYz.setEnabled(true);
                        binding.btYz.setText("请重新发送");
                    }
                }.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot()) {
                moveTaskToBack(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showActivity(@NonNull String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(intent);
    }

    private void showActivity(@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }

    private void goHuaweiSetting() {
        try {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }

    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }

    private void goXiaomiSetting() {
        showActivity("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }

    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }

    private void goOPPOSetting() {
        try {
            showActivity("com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity("com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity("com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity("com.coloros.safecenter");
                }
            }
        }
    }

    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }

    private void goVIVOSetting() {
        showActivity("com.iqoo.secure");
    }

    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }

    private void goMeizuSetting() {
        showActivity("com.meizu.safe");
    }

    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }

    private void goSamsungSetting() {
        try {
            showActivity("com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity("com.samsung.android.sm");
        }
    }

    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }

    private void goLetvSetting() {
        showActivity("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity");
    }

    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }

    private void goSmartisanSetting() {
        showActivity("com.smartisanos.security");
    }

    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window _window = getWindow();
            WindowManager.LayoutParams params = _window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            _window.setAttributes(params);
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }
}