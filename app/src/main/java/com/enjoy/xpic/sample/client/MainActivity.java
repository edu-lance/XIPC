package com.enjoy.xpic.sample.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XServiceCall;
import com.enjoy.xpic.sample.IMusicService;
import com.enjoy.xpic.sample.R;

public class MainActivity extends AppCompatActivity {

    private XServiceCall<IMusicService> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call = XIPC.newCall(IMusicService.class);
        XServiceCall.Listener<IMusicService> listener = iMusicService -> {
            boolean result = iMusicService.play("客户端传递数据！");
            System.out.println("服务器返回结果:" + result);

            iMusicService.stop();
            iMusicService.play("xxxx");
        };
        call.connect(listener);// bindService -> binderProxy

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.disconnect();
    }
}