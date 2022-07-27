package com.enjoy.xpic.adapter;

import android.os.Parcel;


public interface TypeAdapter {
    boolean handles(Object o);

    void writeToParcel(Object o, Parcel dest);

    Object readFromParcel(Parcel in);
}
