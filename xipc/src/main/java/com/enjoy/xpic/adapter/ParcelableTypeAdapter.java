package com.enjoy.xpic.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Constructor;

public class ParcelableTypeAdapter implements TypeAdapter {
    @Override
    public boolean handles(Object o) {
        return o instanceof Parcelable;
    }

    @Override
    public void writeToParcel(Object o, Parcel dest) {
        Parcelable parcelable= (Parcelable) o;
        dest.writeString(parcelable.getClass().getName());
        parcelable.writeToParcel(dest,0);
    }

    @Override
    public Object readFromParcel(Parcel in) {
        return in.readParcelable(getClass().getClassLoader());
    }
}
