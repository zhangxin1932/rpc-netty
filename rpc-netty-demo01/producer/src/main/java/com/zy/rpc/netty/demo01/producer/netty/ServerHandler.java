package com.zy.rpc.netty.demo01.producer.netty;

import com.zy.rpc.netty.demo01.common.codec.hessian2.Hessian2Response;
import com.zy.rpc.netty.demo01.common.model.Request;
import com.zy.rpc.netty.demo01.common.model.Response;
import com.zy.rpc.netty.demo01.common.utils.ReflectUtils;
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
            Response response = new Response();
            try {
                Request request = (Request) msg;
                Long requestId = request.getRequestId();
                String interfaceName = request.getInterfaceName();
                Class<?> interfaceType = ReflectUtils.desc2class(interfaceName);
                String methodName = request.getMethodName();
                Class<?>[] argsType = ReflectUtils.desc2classArray(request.getArgsTypes());
                Object[] args = request.getArgs();

                Object bean = NettySpringBeanFactory.getBean(interfaceType, request.getImplCode());
                Method method = ReflectionUtils.findMethod(interfaceType, methodName, argsType);
                Object result = method.invoke(bean, args);

                response.setRequestId(requestId);
                response.setResult(result);
            } catch (Throwable e) {
                response.setE(e);
            }

            ctx.channel().writeAndFlush(new Hessian2Response(response));
        });
    }
}
