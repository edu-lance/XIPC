package com.enjoy.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        //创建动态代理对象！
        ITest proxy = (ITest) Proxy.newProxyInstance(
                ITest.class.getClassLoader(),
                new Class<?>[]{ITest.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {


                        return null;
                    }
                });
        proxy.test();
    }
}