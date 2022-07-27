package com.enjoy.xipcsample;

public class MusicImpl implements IMusicInterface {


    @Override
    public void play(User user) {
        System.out.println("服务端执行：play:" + user.name);
    }

    @Override
    public void stop() {
        System.out.println("服务端执行：stop");
    }
}
