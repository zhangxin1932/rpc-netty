package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -3269846941421652860L;

    private LocalTime localTime;
    private ZoneOffset zoneOffset;

    public OffsetTimeHandle() {
    }

    public OffsetTimeHandle(Object o) {
        try {
            OffsetTime offsetTime = (OffsetTime) o;
            this.zoneOffset = offsetTime.getOffset();
            this.localTime = offsetTime.toLocalTime();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return OffsetTime.of(localTime, zoneOffset);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
