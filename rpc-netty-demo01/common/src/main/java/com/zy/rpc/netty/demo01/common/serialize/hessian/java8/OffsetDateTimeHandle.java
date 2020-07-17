package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -7823900532640515312L;

    private LocalDateTime dateTime;
    private ZoneOffset offset;

    public OffsetDateTimeHandle() {
    }

    public OffsetDateTimeHandle(Object o) {
        try {
            OffsetDateTime offsetDateTime = (OffsetDateTime) o;
            this.dateTime = offsetDateTime.toLocalDateTime();
            this.offset = offsetDateTime.getOffset();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return OffsetDateTime.of(dateTime, offset);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
