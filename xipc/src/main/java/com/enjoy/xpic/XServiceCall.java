package com.enjoy.xpic;

import android.content.Context;
import android.os.RemoteException;


import com.enjoy.xpic.aidl.XIPCServiceInterface;
import com.enjoy.xpic.annotation.Process;
import com.enjoy.xpic.annotation.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;

public class XServiceCall<T> {
    private String service;
    String serviceName;
    String processName;
    Class<?> interfaceCls;
    Context context;

    public XServiceCall(Class<T> interfaceCls) {
        this.interfaceCls = interfaceCls;
        context = XIPC.getXipc().context;
        service = interfaceCls.getAnnotation(Service.class).value();
        //如果是两个APP通信，则存在Process
        if (interfaceCls.isAnnotationPresent(Process.class)) {
            processName = interfaceCls.getAnnotation(Process.class).value();
            serviceName = XIPC.getXipc().services.get(processName);
        } else {
            serviceName = XIPC.getXipc().services.get(service);
        }

    }

    public interface Listener<T> {
        void create(T t);
    }

    public void connect(Listener<T> listener) {
        XThreadCaller.post(new Runnable() {
            @Override
            public void run() {
                //获得服务端的binder代理对象
                XIPCServiceInterface xipcServiceInterface = XServiceManager.getInstance()
                        .connect(context, processName, serviceName);
                T proxy = (T) Proxy.newProxyInstance(
                        interfaceCls.getClassLoader(),
                        new Class<?>[]{interfaceCls},
                        new IPCInvocationHandler(xipcServiceInterface));
                listener.create(proxy);
            }
        });
    }

    public void disconnect() {
        XServiceManager.getInstance().disConnect(context, processName, serviceName);
    }

    class IPCInvocationHandler implements InvocationHandler {

        //服务端的Binder代理对象
        private XIPCServiceInterface xipcServiceInterface;

        public IPCInvocationHandler(XIPCServiceInterface xipcServiceInterface) {
            this.xipcServiceInterface = xipcServiceInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            XWrapperParcelable xWrapperParcelable = new XWrapperParcelable(args);
            XWrapperParcelable result = null;
            try {
                //java方法重载
                Class<?>[] parameterTypes = method.getParameterTypes();
                // 参数、music（服务）、方法、参数类型
                result = xipcServiceInterface
                        .invoke(xWrapperParcelable, service, method.getName(), parameterTypes);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (result.target != null) {
                return result.target[0];
            }
            return null;
        }

    }
}
