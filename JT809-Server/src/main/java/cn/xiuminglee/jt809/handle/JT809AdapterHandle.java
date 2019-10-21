package cn.xiuminglee.jt809.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 21:21
 * @Version 1.0
 * @Describe: 适配处理器
 */
public class JT809AdapterHandle extends ChannelInboundHandlerAdapter {
    private static Logger log = LoggerFactory.getLogger(JT809AdapterHandle.class);


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("{}客户端已连接",ctx.name());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}客户端关闭",ctx.name());
        super.channelInactive(ctx);
    }


}
