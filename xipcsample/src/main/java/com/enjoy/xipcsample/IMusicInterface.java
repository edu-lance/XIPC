package com.enjoy.xipcsample;

import com.enjoy.xpic.annotation.Service;

@Service("music")
public interface IMusicInterface {

    void play(User user);

    void stop();
}
