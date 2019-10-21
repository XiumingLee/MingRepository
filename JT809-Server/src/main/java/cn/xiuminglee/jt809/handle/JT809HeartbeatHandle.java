package cn.xiuminglee.jt809.handle;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.packet.JT809HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 14:42
 * @Version 1.0
 * @Describe:
 */
public class JT809HeartbeatHandle extends SimpleChannelInboundHandler<JT809HeartbeatResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JT809HeartbeatResponse msg) throws Exception {
        msg.setMsgLength(JT809BasePacket.getFixedByteLength());
        msg.setMsgSn(Const.getMsgSN());
        msg.setMsgId(Const.BusinessDataType.UP_LINKTEST_RSP);
        msg.setMsgGNSSCenterId(Const.UserInfo.MSG_GNSSCENTERID);
        msg.setVersionFlag(new byte[]{1,0,0});
        msg.setEncryptFlag(Const.EncryptFlag.NO);
        msg.setEncryptKey(0);
        ctx.channel().writeAndFlush(msg);
    }
}
