package cn.xiuminglee.jt809.protocol;

import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.PacketDecoderUtils;
import cn.xiuminglee.jt809.common.util.PacketEncoderUtils;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 10:13
 * @Version 1.0
 * @Describe: 编码适配器
 */
public class JT809EncodeAdapter extends MessageToByteEncoder<JT809BasePacket> {
    private static Logger log = LoggerFactory.getLogger(JT809EncodeAdapter.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, JT809BasePacket packet, ByteBuf out) throws Exception {
//        EncoderFactory.getEncoder(packet.getClass().getSimpleName()).encode(ctx,packet,out);
        byte[] allBody = packet.getAllBody();
        // 转义
        byte[] dataBytes = PacketEncoderUtils.encoderEscape(allBody);
        byte[] bytes1 = CommonUtils.append(new byte[]{JT809BasePacket.HEAD_FLAG}, dataBytes);
        byte[] bytes = CommonUtils.append(bytes1, new byte[]{JT809BasePacket.END_FLAG});
        String hexStr = PacketDecoderUtils.bytes2HexStr(bytes);
        log.info("发出的报文为：{}",hexStr);
        out.writeBytes(bytes);
    }
}
