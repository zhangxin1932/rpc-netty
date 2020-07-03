package com.zy.rpc.netty.demo01.common.netty;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RpcImplement {
    /**
     * 实现类的接口类型
     * @return
     */
    Class<?> interfaceType() default void.class;

    /**
     * 实现类的编码
     * @return
     */
    String implCode() default "default";
}
