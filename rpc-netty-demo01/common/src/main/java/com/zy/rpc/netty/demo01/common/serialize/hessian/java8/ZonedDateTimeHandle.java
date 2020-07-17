package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -6933460123278647569L;

    private LocalDateTime dateTime;
    private ZoneOffset offset;
    private String zoneId;


    public ZonedDateTimeHandle() {
    }

    public ZonedDateTimeHandle(Object o) {
        try {
            ZonedDateTime zonedDateTime = (ZonedDateTime) o;
            this.dateTime = zonedDateTime.toLocalDateTime();
            this.offset = zonedDateTime.getOffset();
            ZoneId zone = zonedDateTime.getZone();
            if (zone != null) {
                this.zoneId = zone.getId();
            }
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return ZonedDateTime.ofLocal(dateTime, ZoneId.of(zoneId), offset);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
