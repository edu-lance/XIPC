package com.enjoy.xpic;

import android.content.Context;


import com.enjoy.xpic.adapter.BasicTypeAdapter;
import com.enjoy.xpic.adapter.NullTypeAdapter;
import com.enjoy.xpic.adapter.ParcelableTypeAdapter;
import com.enjoy.xpic.adapter.TypeAdapter;
import com.enjoy.xpic.annotation.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class XIPCConfig {
    List<TypeAdapter> adapters;
    Map<String, String> services;
    Context context;

    public XIPCConfig(Context context, List<TypeAdapter> adapters,
                      Map<String, String> services) {
        this.context = context;
        this.adapters = adapters;
        this.services = services;
    }


    public static class Builder {
        private List<TypeAdapter> adapters = new ArrayList<>();
        private Map<String, String> services = new HashMap<>();
        private Context context;

        public Builder context(Context context) {
            this.context = context.getApplicationContext();
            return this;
        }

        public Builder addAdapter(TypeAdapter typeAdapter) {
            adapters.add(typeAdapter);
            return this;
        }

        public Builder addService(Class<? extends XIPCService> service) {
            String serviceName = service.getAnnotation(Service.class).value();
            services.put(serviceName, service.getName());
            return this;
        }

        public Builder addService(String packageName,
                                  String serviceName) {
            services.put(packageName, serviceName);
            return this;
        }


        public XIPCConfig build() {
            if (context == null) {
                throw new IllegalStateException("Context required.");
            }
            List<TypeAdapter> adapters = new ArrayList<>();
            adapters.add(new NullTypeAdapter());
            adapters.add(new BasicTypeAdapter());
            adapters.add(new ParcelableTypeAdapter());
            adapters.addAll(this.adapters);
            return new XIPCConfig(context, adapters, services);
        }

    }

}
