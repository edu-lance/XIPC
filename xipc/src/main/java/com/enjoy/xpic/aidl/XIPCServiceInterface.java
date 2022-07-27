/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.enjoy.xpic.aidl;


import android.os.IInterface;

import com.enjoy.xpic.XWrapperParcelable;

import java.io.Serializable;

public interface XIPCServiceInterface extends android.os.IInterface {

    public static abstract class Stub extends android.os.Binder implements XIPCServiceInterface {
        private static final String DESCRIPTOR = "com.enjoy.xipc.aidl.IPCInvokeBridge";


        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static XIPCServiceInterface asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof XIPCServiceInterface)) {
                return (XIPCServiceInterface) iin;
            }
            return new Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case TRANSACTION_invoke: {
                    data.enforceInterface(DESCRIPTOR);
                    XWrapperParcelable parameter;
                    if ((0 != data.readInt())) {
                        parameter = XWrapperParcelable.CREATOR.createFromParcel(data);
                    } else {
                        parameter = null;
                    }
                    //服务名
                    String serviceName = data.readString();
                    //方法名
                    String methodName = data.readString();
                    int length = data.readInt();
                    Class<?>[] parameterTypes = new Class[length];
                    for (int i = 0; i < length; i++) {
                        parameterTypes[i] = (Class<?>) data.readSerializable();
                    }
                    XWrapperParcelable _result = this.invoke(parameter, serviceName, methodName, parameterTypes);
                    reply.writeNoException();
                    if ((_result != null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements XIPCServiceInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }


            @Override
            public XWrapperParcelable invoke(XWrapperParcelable data, String serviceName, String methodName,
                                             Class<?>[] parameterTypes) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                XWrapperParcelable _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(serviceName);
                    _data.writeString(methodName);
                    //没有参数
                    _data.writeInt(parameterTypes.length);
                    for (Class<?> parameterType : parameterTypes) {
                        _data.writeSerializable(parameterType);
                    }
                    mRemote.transact(Stub.TRANSACTION_invoke, _data, _reply, 0);
                    _reply.readException();
                    if ((0 != _reply.readInt())) {
                        _result = XWrapperParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

        }

        static final int TRANSACTION_invoke = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

    }

    public XWrapperParcelable invoke(XWrapperParcelable data, String serviceName, String methodName, Class<?>[] parameterTypes) throws android.os.RemoteException;
}
