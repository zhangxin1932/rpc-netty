package com.zy.rpc.netty.demo01.common.zk;

public interface DataListener {
    void dataChanged(String path, Object value, EventType eventType);
}
