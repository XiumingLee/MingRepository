package cn.xiuminglee.jt809.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 16:22
 * @Version 1.0
 * @Describe:
 */
public class JT809IdleStateHandler extends IdleStateHandler {
    private static Logger log = LoggerFactory.getLogger(JT809IdleStateHandler.class);

    private static final int READER_IDLE_TIME = 60;

    public JT809IdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
