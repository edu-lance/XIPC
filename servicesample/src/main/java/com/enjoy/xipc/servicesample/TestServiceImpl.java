package com.enjoy.xipc.servicesample;

public class TestServiceImpl implements ITestService {
    @Override
    public float[] test(int... msg) {
        System.out.println("执行");
        for (int s : msg) {
            System.out.println("服务端接收:" + s);
        }
        return new float[]{1, 2, 3};
    }
}
