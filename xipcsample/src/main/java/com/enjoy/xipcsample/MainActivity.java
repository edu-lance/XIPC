package com.enjoy.xipcsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XServiceCall;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XServiceCall<IMusicInterface> call = XIPC.newCall(IMusicInterface.class);
        call.connect(new XServiceCall.Listener<IMusicInterface>() {
            @Override
            public void create(IMusicInterface iMusicInterface) {
                System.out.println(iMusicInterface.getClass().getName());
                iMusicInterface.play(new User("lance","123"));
                iMusicInterface.stop();
            }
        });
    }
}