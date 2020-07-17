package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;
import java.io.Serializable;
import java.time.Period;

public class PeriodHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 4399720381283781186L;

    private int years;
    private int months;
    private int days;

    public PeriodHandle() {
    }

    public PeriodHandle(Object o) {
        try {
            Period period = (Period) o;
            this.years = period.getYears();
            this.months = period.getMonths();
            this.days = period.getDays();
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            return Period.of(years, months, days);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
