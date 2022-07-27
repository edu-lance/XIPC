package com.enjoy.xpic;

import android.content.Context;
import android.text.TextUtils;


import com.enjoy.xpic.adapter.TypeAdapter;
import com.enjoy.xpic.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XIPC {
    private static volatile XIPC xipc;
    private List<TypeAdapter> adapters;
    Map<String, String> services;
    Context context;
    private static volatile boolean isInitialized;

    public XIPC(Context context, List<TypeAdapter> adapters,
                Map<String, String> services) {
        this.context = context;
        this.adapters = adapters;
        this.services = services;
    }

    private static void checkInitialize() {
        if (!isInitialized) {
            throw new IllegalStateException(
                    "Not Initialized");
        }
    }

    protected static XIPC getXipc() {
        return xipc;
    }

    public static void init(XIPCConfig xipcConfig) {
        if (xipc == null) {
            synchronized (XIPC.class) {
                if (xipc == null) {
                    xipc = new XIPC(xipcConfig.context, xipcConfig.adapters, xipcConfig.services);
                }
            }
        }
        isInitialized = true;
    }


    protected TypeAdapter callAdapter(Object object) {
        for (TypeAdapter adapter : adapters) {
            if (adapter.handles(object)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("no adapter can transfer:" + object.getClass());
    }

    protected TypeAdapter callAdapter(String adapterName) {
        for (TypeAdapter adapter : adapters) {
            if (TextUtils.equals(adapterName, adapter.getClass().getName())) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("no adapter is:" + adapterName);
    }


    public static <T> XServiceCall<T> newCall(Class<T> service) {
        checkInitialize();
        return new XServiceCall<T>(service);
    }


    /**
     * 服务端使用： 注册服务
     */
    Map<String, Object> interfaceCache = new HashMap<>();

    public static void register(Object serviceObj) {
        checkInitialize();
        Class<?> cls = serviceObj.getClass();
        _register(cls,serviceObj);

    }

    private static void _register(Class<?> cls, Object serviceObj) {
        if (cls.isAnnotationPresent(Service.class)) {
            Service service = cls.getAnnotation(Service.class);
            xipc.interfaceCache.put(service.value(), serviceObj);
        }
        Class<?>[] classes = cls.getInterfaces();
        if (classes != null) {
            for (Class<?> c : classes) {
                _register(c, serviceObj);
            }
        }
    }


    public static Object getInterface(String name) {
        checkInitialize();
        return xipc.interfaceCache.get(name);
    }


}
