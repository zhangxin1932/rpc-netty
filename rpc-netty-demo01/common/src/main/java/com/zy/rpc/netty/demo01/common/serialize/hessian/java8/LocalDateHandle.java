package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.LocalDate;

public class LocalDateHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 166018689500019951L;

    private int year;
    private int month;
    private int day;

    public LocalDateHandle() {
    }

    public LocalDateHandle(Object o) {
        try {
            LocalDate localDate = (LocalDate) o;
            this.year = localDate.getYear();
            this.month = localDate.getMonthValue();
            this.day = localDate.getDayOfMonth();
        } catch (Throwable t) {
            // ignore
        }
    }

    public Object readResolve() {
        try {
            return LocalDate.of(year, month, day);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
