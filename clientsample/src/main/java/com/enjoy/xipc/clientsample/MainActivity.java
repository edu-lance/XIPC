package com.enjoy.xipc.clientsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.enjoy.xipc.servicesample.ITestService;
import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XIPCConfig;
import com.enjoy.xpic.XServiceCall;

public class MainActivity extends AppCompatActivity {

    private XServiceCall<ITestService> xServiceCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XIPCConfig xipcConfig = new XIPCConfig.Builder().context(this)
                .addService("com.enjoy.xipc.servicesample",
                        "com.enjoy.xipc.servicesample.MainService")
                .build();
        XIPC.init(xipcConfig);

        xServiceCall = XIPC.newCall(ITestService.class);

    }

    public void request(View view) {
        xServiceCall.connect(new XServiceCall.Listener<ITestService>() {
            @Override
            public void create(ITestService iTestService) {
                float[] test = iTestService.test(1, 2);
                System.out.println(test);
                for (float s : test) {
                    System.out.println(s);
                }
            }
        });
    }
}