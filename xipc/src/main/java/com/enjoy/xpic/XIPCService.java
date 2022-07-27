package com.enjoy.xpic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;


import com.enjoy.xpic.aidl.XIPCServiceInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class XIPCService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private XIPCServiceInterface.Stub binder = new XIPCServiceInterface.Stub() {
        @Override
        public XWrapperParcelable invoke(XWrapperParcelable data, String serviceName, String methodName, Class<?>[] parameterTypes) throws RemoteException {
            System.out.println("客户端请求：" + serviceName);
            Object server = XIPC.getInterface(serviceName);
            try {
                Method serverMethod = server.getClass().getMethod(methodName, parameterTypes);
                Object result = serverMethod.invoke(server, data.target);
                return new XWrapperParcelable(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    };

}
