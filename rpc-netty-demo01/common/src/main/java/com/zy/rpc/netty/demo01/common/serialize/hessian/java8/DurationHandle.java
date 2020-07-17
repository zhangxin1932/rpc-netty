package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;
import java.time.Duration;

public class DurationHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -4367309317780077156L;

    private long seconds;
    private int nanos;

    public DurationHandle() {
    }

    public DurationHandle(Object o) {
        try {
            Duration duration = (Duration) o;
            this.seconds = duration.getSeconds();
            this.nanos = duration.getNano();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return Duration.ofSeconds(seconds, nanos);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
