package com.cmprocess.ipc.helper.ipcbus;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class TransformBinder extends Binder {

    private ServerInterface serverInterface;
    private Object server;

    public TransformBinder(ServerInterface serverInterface, Object server) {
        this.serverInterface = serverInterface;
        this.server = server;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == INTERFACE_TRANSACTION) {
            reply.writeString(serverInterface.getInterfaceName());
            return true;
        }
        IPCMethod method = serverInterface.getIPCMethod(code);
        if (method != null) {
            try {
                method.handleTransact(server, data, reply);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
