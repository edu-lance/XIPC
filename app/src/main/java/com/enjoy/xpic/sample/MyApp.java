package com.enjoy.xpic.sample;

import android.app.Application;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XIPCConfig;
import com.enjoy.xpic.sample.service.MusicServiceImpl;
import com.enjoy.xpic.sample.service.MusicService;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XIPCConfig xipcConfig;
        if (Util.isMainProcess(this)) {
            //主进程 客户端进程
            xipcConfig = new XIPCConfig.Builder()
                    .context(this)
                    .addService(MusicService.class)
                    .build();
            XIPC.init(xipcConfig);
        } else {
            // 子进程 服务端进程
            xipcConfig = new XIPCConfig.Builder()
                    .context(this)
                    .build();
            XIPC.init(xipcConfig);
            XIPC.register(new MusicServiceImpl());
        }

    }


}
