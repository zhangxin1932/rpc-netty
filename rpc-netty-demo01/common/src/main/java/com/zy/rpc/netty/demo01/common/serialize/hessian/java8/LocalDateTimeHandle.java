package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 7563825215275989361L;

    private LocalDate date;
    private LocalTime time;

    public LocalDateTimeHandle() {
    }

    public LocalDateTimeHandle(Object o) {
        try {
            LocalDateTime localDateTime = (LocalDateTime) o;
            date = localDateTime.toLocalDate();
            time = localDateTime.toLocalTime();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return LocalDateTime.of(date, time);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
