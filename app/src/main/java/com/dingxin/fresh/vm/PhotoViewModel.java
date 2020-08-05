package com.dingxin.fresh.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.InputStream;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import okhttp3.ResponseBody;

public class PhotoViewModel extends BaseViewModel {
    public PhotoViewModel(@NonNull Application application) {
        super(application);
    }
}
