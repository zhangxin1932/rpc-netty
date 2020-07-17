package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.MonthDay;

public class MonthDayHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 5288238558666577745L;

    private int month;
    private int day;

    public MonthDayHandle() {
    }

    public MonthDayHandle(Object o) {
        try {
            MonthDay monthDay = (MonthDay) o;
            this.month = monthDay.getMonthValue();
            this.day = monthDay.getDayOfMonth();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return MonthDay.of(month, day);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
