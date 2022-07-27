package com.enjoy.xpic.sample;


import com.enjoy.xpic.annotation.Service;

@Service("music")
public interface IMusicService {
    boolean play(String music);

    void stop();
}
