package com.enjoy.xpic;

import android.os.Parcel;
import android.os.Parcelable;

import com.enjoy.xpic.adapter.TypeAdapter;


public class XWrapperParcelable implements Parcelable {

    private static final int NO_DATA = 0;
    private static final int HAS_DATA = 1;

    Object[] target;

    XWrapperParcelable() {
    }


    XWrapperParcelable(Object... args) {
        this.target = args;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (target == null) {
            dest.writeInt(NO_DATA);
            return;
        }
        dest.writeInt(HAS_DATA);
        dest.writeInt(target.length);
        // int,String,Parcelable
        for (Object o : target) {
            TypeAdapter adapter = XIPC.getXipc().callAdapter(o);
            if (adapter != null) {
                dest.writeString(adapter.getClass().getName());
                adapter.writeToParcel(o, dest);
            }
        }
    }

    void readFromParcel(Parcel in) {
        int hasData = in.readInt();
        if (hasData == HAS_DATA) {
            int length = in.readInt();
            target = new Object[length];
            for (int i = 0; i < length; i++) {
                String transferClass = in.readString();
                TypeAdapter adapter = XIPC.getXipc().callAdapter(transferClass);
                target[i] = adapter.readFromParcel(in);
            }

        }
    }

    public static final Creator<XWrapperParcelable> CREATOR = new Creator<XWrapperParcelable>() {
        @Override
        public XWrapperParcelable createFromParcel(Parcel in) {
            XWrapperParcelable o = new XWrapperParcelable();
            o.readFromParcel(in);
            return o;
        }

        @Override
        public XWrapperParcelable[] newArray(int size) {
            return new XWrapperParcelable[size];
        }
    };
}