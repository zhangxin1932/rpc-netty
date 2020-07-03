package com.zy.rpc.netty.demo01.producer.netty;

import com.alibaba.fastjson.JSON;
import com.zy.rpc.netty.demo01.common.netty.Request;
import com.zy.rpc.netty.demo01.common.netty.Response;
import com.zy.rpc.netty.demo01.producer.config.NettySpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Request request = JSON.parseObject(msg, Request.class);
        Long requestId = request.getRequestId();
        Class<?> classType = request.getInterfaceType();
        String methodName = request.getMethodName();
        Class<?>[] argsType = request.getArgsType();
        Object[] args = request.getArgs();

        Response response = new Response();
        response.setRequestId(requestId);
        try {
            Object bean = NettySpringBeanFactory.getBean(request.getInterfaceType(), request.getImplCode());
            Method method = ReflectionUtils.findMethod(classType, methodName, argsType);
            Object result = method.invoke(bean, args);
            response.setResult(result);
        } catch (Throwable e) {
            response.setE(e);
        }

        ctx.writeAndFlush(JSON.toJSONString(response));
    }
}
