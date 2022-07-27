package com.enjoy.xpic.sample.service;

import com.enjoy.xpic.sample.IMusicService;

public class MusicServiceImpl implements IMusicService {

    @Override
    public boolean play(String music) {
        System.out.println("服务器接收：" + music);
        return true;
    }

    @Override
    public void stop() {

    }
}