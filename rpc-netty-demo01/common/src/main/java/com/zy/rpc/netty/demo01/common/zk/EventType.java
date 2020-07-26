package com.zy.rpc.netty.demo01.common.zk;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {
    None(-1),
    NodeCreated(1),
    NodeDeleted(2),
    NodeDataChanged(3),
    NodeChildrenChanged(4),
    CONNECTION_SUSPENDED(11),
    CONNECTION_RECONNECTED(12),
    CONNECTION_LOST(12),
    INITIALIZED(10);

    private final int intValue;
}
