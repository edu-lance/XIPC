package com.enjoy.xipc.servicesample;

import android.app.Application;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XIPCConfig;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XIPCConfig xipcConfig = new XIPCConfig.Builder().context(this)
                .build();
        XIPC.init(xipcConfig);
        XIPC.register(new TestServiceImpl());
    }
}
