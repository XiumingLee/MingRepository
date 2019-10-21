package cn.xiuminglee.jt809.handle;

import cn.xiuminglee.jt809.db.JT809Dao;
import cn.xiuminglee.jt809.packet.JT809Packet0x1202;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 16:04
 * @Version 1.0
 * @Describe:
 */
public class JT809Packet0x1202Handle extends SimpleChannelInboundHandler<JT809Packet0x1202> {
    private static Logger log = LoggerFactory.getLogger(JT809Packet0x1202Handle.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JT809Packet0x1202 msg) {
        log.info("车辆定位信息：{}", msg.toString());
//        if (msg.getVec1() > 0 || msg.getVec2() > 0) {
            JT809Dao.insert0x1202(msg);
//        }
    }
}
