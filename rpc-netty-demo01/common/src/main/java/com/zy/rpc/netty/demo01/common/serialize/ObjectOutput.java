package com.zy.rpc.netty.demo01.common.serialize;

import java.io.IOException;

public interface ObjectOutput extends DataOutput {

    void writeObject(Object obj) throws IOException;

    void writeObject(Object obj, Class<?> clazz) throws IOException;

}