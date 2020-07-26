package com.zy.rpc.netty.demo01.common.zk;

public interface StateListener {
    int DISCONNECTED = 0;

    int CONNECTED = 1;

    int RECONNECTED = 2;

    void stateChanged(int connected);
}
