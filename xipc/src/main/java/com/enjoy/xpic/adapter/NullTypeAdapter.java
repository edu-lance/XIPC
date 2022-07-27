package com.enjoy.xpic.adapter;

import android.os.Parcel;

public class NullTypeAdapter implements TypeAdapter {

    @Override
    public boolean handles(Object o) {
        return o == null;
    }

    @Override
    public void writeToParcel(Object o, Parcel dest) {
    }

    @Override
    public Object readFromParcel(Parcel in) {
        return null;
    }
}
