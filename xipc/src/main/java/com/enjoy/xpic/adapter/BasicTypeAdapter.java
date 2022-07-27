package com.enjoy.xpic.adapter;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class BasicTypeAdapter implements TypeAdapter {

    private static final int TYPE_UNDEFINED = 0;
    private static final int TYPE_INT = 1;
    private static final int TYPE_FLOAT = 2;
    private static final int TYPE_BOOLEAN = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_BYTE = 5;
    private static final int TYPE_LONG = 6;
    private static final int TYPE_DOUBLE = 7;

    private static final int TYPE_INT_ARRAY = 8;
    private static final int TYPE_FLOAT_ARRAY = 9;
    private static final int TYPE_BOOLEAN_ARRAY = 10;
    private static final int TYPE_STRING_ARRAY = 11;
    private static final int TYPE_BYTE_ARRAY = 12;
    private static final int TYPE_LONG_ARRAY = 13;
    private static final int TYPE_DOUBLE_ARRAY = 14;


    @Override
    public boolean handles(Object o) {

        if (o instanceof Integer) {
            return true;
        } else if (o instanceof Float) {
            return true;
        } else if (o instanceof Boolean) {
            return true;
        } else if (o instanceof String) {
            return true;
        } else if (o instanceof Byte) {
            return true;
        } else if (o instanceof Long) {
            return true;
        } else if (o instanceof Double) {
            return true;
        } else if (o.getClass().isArray()) {
            if (o instanceof int[] || o instanceof Integer[]) {
                return true;
            } else if (o instanceof float[] || o instanceof Float[]) {
                return true;
            } else if (o instanceof boolean[] || o instanceof Boolean[]) {
                return true;
            } else if (o instanceof String[]) {
                return true;
            } else if (o instanceof byte[] || o instanceof Byte[]) {
                return true;
            } else if (o instanceof long[] || o instanceof Long[]) {
                return true;
            } else if (o instanceof double[] || o instanceof Double[]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeToParcel(@NonNull Object o, @NonNull Parcel dest) {
        if (o instanceof Integer) {
            dest.writeInt(TYPE_INT);
            dest.writeInt((Integer) o);
        } else if (o instanceof Float) {
            dest.writeInt(TYPE_FLOAT);
            dest.writeFloat((Float) o);
        } else if (o instanceof Boolean) {
            dest.writeInt(TYPE_BOOLEAN);
            dest.writeInt(((Boolean) o) ? 1 : 0);
        } else if (o instanceof String) {
            dest.writeInt(TYPE_STRING);
            dest.writeString((String) o);
        } else if (o instanceof Byte) {
            dest.writeInt(TYPE_BYTE);
            dest.writeByte((Byte) o);
        } else if (o instanceof Long) {
            dest.writeInt(TYPE_LONG);
            dest.writeLong((Long) o);
        } else if (o instanceof Double) {
            dest.writeInt(TYPE_DOUBLE);
            dest.writeDouble((Double) o);
        } else if (o.getClass().isArray()) {
            if (o instanceof int[] || o instanceof Integer[]) {
                dest.writeInt(TYPE_INT_ARRAY);
                dest.writeIntArray((int[]) o);
            } else if (o instanceof float[] || o instanceof Float[]) {
                dest.writeInt(TYPE_FLOAT_ARRAY);
                dest.writeFloatArray((float[]) o);
            } else if (o instanceof boolean[] || o instanceof Boolean[]) {
                dest.writeInt(TYPE_BOOLEAN_ARRAY);
                dest.writeBooleanArray((boolean[]) o);
            } else if (o instanceof String[]) {
                dest.writeInt(TYPE_STRING_ARRAY);
                dest.writeStringArray((String[]) o);
            } else if (o instanceof byte[] || o instanceof Byte[]) {
                dest.writeInt(TYPE_BYTE_ARRAY);
                dest.writeByteArray((byte[]) o);
            } else if (o instanceof long[] || o instanceof Long[]) {
                dest.writeInt(TYPE_LONG_ARRAY);
                dest.writeLongArray((long[]) o);
            } else if (o instanceof double[] || o instanceof Double[]) {
                dest.writeInt(TYPE_DOUBLE_ARRAY);
                dest.writeDoubleArray((double[]) o);
            }
        } else {
            dest.writeInt(TYPE_UNDEFINED);
        }
    }

    @Override
    public Object readFromParcel(@NonNull Parcel in) {
        final int type = in.readInt();
        switch (type) {
            case TYPE_STRING: {
                return in.readString();
            }
            case TYPE_INT: {
                return in.readInt();
            }
            case TYPE_FLOAT: {
                return in.readFloat();
            }
            case TYPE_BOOLEAN: {
                return in.readInt() == 1;
            }
            case TYPE_LONG: {
                return in.readLong();
            }
            case TYPE_BYTE: {
                return in.readByte();
            }
            case TYPE_DOUBLE: {
                return in.readDouble();
            }
            case TYPE_INT_ARRAY:
                return in.createIntArray();
            case TYPE_STRING_ARRAY:
                return in.createStringArray();
            case TYPE_FLOAT_ARRAY:
                return in.createFloatArray();
            case TYPE_DOUBLE_ARRAY:
                return in.createDoubleArray();
            case TYPE_BOOLEAN_ARRAY:
                return in.createBooleanArray();
            case TYPE_LONG_ARRAY:
                return in.createLongArray();
            case TYPE_BYTE_ARRAY:
                return in.createByteArray();
            case TYPE_UNDEFINED:
            default: {
            }
        }
        return null;
    }
}
