package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalTime;

public class LocalTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -5892919085390462315L;

    private int hour;
    private int minute;
    private int second;
    private int nano;

    public LocalTimeHandle() {
    }

    public LocalTimeHandle(Object o) {
        try {
            LocalTime localTime = (LocalTime) o;
            this.hour = localTime.getHour();
            this.minute = localTime.getMinute();
            this.second = localTime.getSecond();
            this.nano = localTime.getNano();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return LocalTime.of(hour, minute, second, nano);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
