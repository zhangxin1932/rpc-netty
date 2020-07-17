package com.zy.rpc.netty.demo01.common.serialize.hessian.java8;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;
import java.io.IOException;

public class ZoneIdSerializer extends AbstractSerializer {

    private static final ZoneIdSerializer SERIALIZER = new ZoneIdSerializer();

    public static ZoneIdSerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (obj == null) {
            out.writeNull();
        } else {
            out.writeObject(new ZoneIdHandle(obj));
        }
    }

}
