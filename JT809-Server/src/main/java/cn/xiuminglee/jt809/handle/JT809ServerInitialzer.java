package cn.xiuminglee.jt809.handle;

import cn.xiuminglee.jt809.protocol.JT809DecoderAdapter;
import cn.xiuminglee.jt809.protocol.JT809EncodeAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 20:29
 * @Version 1.0
 * @Describe:
 */
public class JT809ServerInitialzer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x5d});
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, false, delimiter));
        ch.pipeline().addLast(new JT809IdleStateHandler());
        ch.pipeline().addLast(new JT809AdapterHandle());
        ch.pipeline().addLast(new JT809DecoderAdapter());
        ch.pipeline().addLast(new JT809HeartbeatHandle());
        ch.pipeline().addLast(new JT809LoginHandle());
        ch.pipeline().addLast(new JT809Packet0x1202Handle());
        ch.pipeline().addLast(new JT809EncodeAdapter());
    }
}
