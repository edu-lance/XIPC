package com.enjoy.xipcsample;

import android.app.Application;
import android.os.Parcel;

import com.enjoy.xpic.XIPC;
import com.enjoy.xpic.XIPCConfig;
import com.enjoy.xpic.adapter.TypeAdapter;

public class MyApp extends Application {

    class UserAdapter implements TypeAdapter {

        @Override
        public boolean handles(Object o) {
            return o instanceof User;
        }

        @Override
        public void writeToParcel(Object o, Parcel dest) {
            User u = (User) o;
            dest.writeString(u.name);
            dest.writeString(u.pwd);
        }

        @Override
        public Object readFromParcel(Parcel in) {
            String name = in.readString();
            String pwd = in.readString();
            return new User(name,pwd);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Util.isMainProcess(this)) {
            XIPCConfig config = new XIPCConfig.Builder()
                    .context(this)
                    .addService(MusicService.class)
                    .addAdapter(new UserAdapter())
                    .build();
            XIPC.init(config);
        } else {
            XIPCConfig config = new XIPCConfig.Builder()
                    .addAdapter(new UserAdapter())
                    .context(this)
                    .build();
            XIPC.init(config);
            XIPC.register(new MusicImpl());
        }
    }
}
