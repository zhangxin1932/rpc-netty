package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;
import java.time.Year;

public class YearHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -6299552890287487926L;

    private int year;

    public YearHandle() {
    }

    public YearHandle(Object o) {
        try {
            Year y = (Year) o;
            this.year = y.getValue();
        } catch (Throwable t) {
            // ignore
        }

    }

    private Object readResolve() {
        try {
            return Year.of(year);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
