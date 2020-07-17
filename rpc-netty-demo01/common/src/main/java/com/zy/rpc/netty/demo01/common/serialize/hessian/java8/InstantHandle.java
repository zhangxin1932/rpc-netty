package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.Instant;

public class InstantHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -4367309317780077156L;

    private long seconds;
    private int nanos;

    public InstantHandle() {
    }

    public InstantHandle(Object o) {
        try {
            Instant instant = (Instant) o;
            this.seconds = instant.getEpochSecond();
            this.nanos = instant.getNano();
        } catch (Throwable t) {
            // ignore
        }
    }


    private Object readResolve() {
        try {
            return Instant.ofEpochSecond(seconds, nanos);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
