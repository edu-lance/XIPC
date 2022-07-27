package com.enjoy.xpic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;


import com.enjoy.xpic.aidl.XIPCServiceInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


class XServiceManager {
    private static volatile XServiceManager sInstance;
    private Map<String, IPCServiceWrapper> mServiceMap = new ConcurrentHashMap<>();

    private XServiceManager() {
    }

    public static XServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (XServiceManager.class) {
                if (sInstance == null) {
                    sInstance = new XServiceManager();
                }
            }
        }
        return sInstance;
    }

    String generateServiceKey(String packageName, String serviceName) {
        return TextUtils.isEmpty(packageName) ? serviceName : packageName + "/" + serviceName;
    }

    XIPCServiceInterface connect(Context context,
                                 String processName, String serviceName) {
        String serviceKey = generateServiceKey(processName, serviceName);
        IPCServiceWrapper serviceWrapper;
        synchronized (mServiceMap) {
            serviceWrapper = mServiceMap.get(serviceKey);
            if (serviceWrapper != null) {
                try {
                    serviceWrapper.latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return serviceWrapper.serviceInterface;
            }
            serviceWrapper = new IPCServiceWrapper();
            mServiceMap.put(serviceKey, serviceWrapper);
        }
        IPCServiceWrapper sw = serviceWrapper;
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                sw.serviceInterface = XIPCServiceInterface.Stub.asInterface(service);
//                意外断开
                //service.linkToDeath();
                sw.serviceConnection = this;
                sw.latch.countDown();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServiceMap.remove(serviceKey);
                sw.latch.countDown();
                sw.serviceInterface = null;
                sw.serviceConnection = null;
            }
        };
        Intent intent = null;
        if (TextUtils.isEmpty(processName)) {
            try {
                intent = new Intent(context, Class.forName(serviceName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            intent = new Intent();
            intent.setClassName(processName, serviceName);
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        try {
            sw.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sw.serviceInterface;
    }

    void disConnect(Context context, String processName, String serviceName) {
        String serviceKey = generateServiceKey(processName, serviceName);
        IPCServiceWrapper serviceWrapper;
        synchronized (mServiceMap) {
            serviceWrapper = mServiceMap.get(serviceKey);
            if (serviceWrapper != null && serviceWrapper.serviceConnection != null) {
                context.unbindService(serviceWrapper.serviceConnection);
                mServiceMap.remove(serviceKey);
            }
        }
    }


    private static class IPCServiceWrapper {
        XIPCServiceInterface serviceInterface;
        CountDownLatch latch = new CountDownLatch(1);
        ServiceConnection serviceConnection;
    }
}
