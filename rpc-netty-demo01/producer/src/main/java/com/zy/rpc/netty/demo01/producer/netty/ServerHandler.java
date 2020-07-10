package com.zy.rpc.netty.demo01.producer.netty;

import com.zy.rpc.netty.demo01.common.codec.hessian2.Hessian2Response;
import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.producer.config.NettySpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final ExecutorService EXECUTOR_SERVICE = new DefaultEventExecutorGroup(
            Runtime.getRuntime().availableProcessors() * 2,
            new DefaultThreadFactory("NettyServerHandler", true)
    );

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        EXECUTOR_SERVICE.submit(() -> {
            // Request request = JSON.parseObject(msg, Request.class);
            Request request = (Request) msg;
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

            // ctx.channel().writeAndFlush(JSON.toJSONString(response));
            ctx.channel().writeAndFlush(new Hessian2Response(response));
        });
    }
}
