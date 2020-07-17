package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.YearMonth;

public class YearMonthHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -4150786187896925314L;

    private int year;
    private int month;

    public YearMonthHandle() {
    }

    public YearMonthHandle(Object o) {
        try {
            YearMonth yearMonth = (YearMonth) o;
            this.year = yearMonth.getYear();
            this.month = yearMonth.getMonthValue();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return YearMonth.of(year, month);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
