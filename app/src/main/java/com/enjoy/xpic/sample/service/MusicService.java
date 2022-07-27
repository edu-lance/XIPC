package com.enjoy.xpic.sample.service;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XIPCConfig;
import com.enjoy.xpic.XIPCService;
import com.enjoy.xpic.annotation.Service;
import com.enjoy.xpic.sample.IMusicService;

@Service("music")
public class MusicService extends XIPCService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
