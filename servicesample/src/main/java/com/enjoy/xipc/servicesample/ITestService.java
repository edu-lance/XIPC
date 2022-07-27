package com.enjoy.xipc.servicesample;


import com.enjoy.xpic.annotation.Process;
import com.enjoy.xpic.annotation.Service;

@Process("com.enjoy.xipc.servicesample")
@Service(value = "test")
public interface ITestService {
    float[] test(int... msg);
}
