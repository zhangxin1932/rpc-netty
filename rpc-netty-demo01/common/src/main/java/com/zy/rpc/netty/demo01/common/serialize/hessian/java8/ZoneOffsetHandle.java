package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.ZoneOffset;

public class ZoneOffsetHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 8841589723587858789L;

    private int seconds;

    public ZoneOffsetHandle() {
    }

    public ZoneOffsetHandle(Object o) {
        try {
            ZoneOffset zoneOffset = (ZoneOffset) o;
            this.seconds = zoneOffset.getTotalSeconds();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return ZoneOffset.ofTotalSeconds(seconds);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
