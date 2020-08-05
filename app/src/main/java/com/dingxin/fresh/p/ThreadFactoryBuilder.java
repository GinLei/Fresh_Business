package com.dingxin.fresh.p;

import androidx.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryBuilder implements ThreadFactory {

    private String name;

    public ThreadFactoryBuilder(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable, name);
        thread.setName("ThreadFactoryBuilder_" + name);
        return thread;
    }
}