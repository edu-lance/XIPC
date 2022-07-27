package com.enjoy.xipcsample;

import android.os.Parcel;
import android.os.Parcelable;

public class MyData implements Parcelable {
    public String data;

    public MyData(String data) {
        this.data = data;
    }

    protected MyData(Parcel in) {
        data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };
}
