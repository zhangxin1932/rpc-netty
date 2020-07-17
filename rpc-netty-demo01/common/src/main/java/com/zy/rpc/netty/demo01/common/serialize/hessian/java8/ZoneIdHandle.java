package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.ZoneId;

public class ZoneIdHandle implements HessianHandle, Serializable {

    private static final long serialVersionUID = 8789182864066905552L;

    private String zoneId;

    public ZoneIdHandle() {
    }

    public ZoneIdHandle(Object o) {
        try {
            ZoneId zoneId = (ZoneId) o;
            this.zoneId = zoneId.getId();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return ZoneId.of(this.zoneId);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
