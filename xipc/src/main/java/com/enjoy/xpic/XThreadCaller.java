package com.enjoy.xpic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class XThreadCaller {

    private static volatile XThreadCaller sImpl;
    private ExecutorService executorService;

    private static XThreadCaller getImpl() {
        if (sImpl == null) {
            synchronized (XThreadCaller.class) {
                if (sImpl == null) {
                    sImpl = new XThreadCaller();
                }
            }
        }
        return sImpl;
    }

    private XThreadCaller() {
        executorService = Executors.newScheduledThreadPool(3, new ThreadFactory() {
            int index = 0;

            @Override
            public Thread newThread(Runnable r) {
                StringBuilder stringBuilder = new StringBuilder("IPCThreadPool#Thread-");
                int i = this.index;
                this.index = i + 1;
                Thread thread = new Thread(r, stringBuilder.append(i).toString());
                return thread;
            }
        });
    }

    static void post(Runnable runnable) {
        getImpl().executorService.execute(runnable);
    }

}
