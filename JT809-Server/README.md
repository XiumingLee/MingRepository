# JT809-Server
## 简介

最近公司有个项目需要接收公交公司的实时推送的公交车位置数据。于是就用Netty简单实现了JT809协议的部分功能。

- ##### 服务端(上级平台)主链路

- ##### 主链路登录请求报文解析

- ##### 主链路登录应答消息

- ##### 车辆实时车辆定位信息消息报文解析

## 主要代码和使用

- 项目结构

![](http://qiniu.mrain22.cn/201911101443_397.png)

### 解码适配器

```java
package cn.xiuminglee.jt809.protocol;

import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.CrcUtil;
import cn.xiuminglee.jt809.common.util.PacketDecoderUtils;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.protocol.decoder.DecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static cn.xiuminglee.jt809.common.util.CommonUtils.PACKET_CACHE;

/**
 * @Author: Xiuming Lee
 * @Describe: 解码适配器
 */
public class JT809DecoderAdapter extends ByteToMessageDecoder {
    private static Logger log = LoggerFactory.getLogger(JT809DecoderAdapter.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //判断是否有可读的字节
        if (in.readableBytes() <= 0) {
            return;
        }
        // 1、进行转义
        byte[] bytes = PacketDecoderUtils.decoderEscape(in);
        // 2、校验crc
        if (!CrcUtil.checkCRC(bytes)){
            return;
        }
        // 3、判断是那种类型的数据，交给具体的解码器类完成。
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        byteBuf.skipBytes(9);
        // 获取业务标志
        short msgId = byteBuf.readShort();

        // 交给具体的解码器
        JT809BasePacket packet = null;
        try {
            packet = DecoderFactory.getDecoder(msgId).decoder(bytes);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                // log.info("没有可用的解析器，忽略这条信息！此信息不在业务范围内。");
                // 没有可用的解析器，忽略这条信息！此信息不在业务范围内。
            } else {
                log.error("报文解析出错！错误信息：{}；报文信息：{}；",e.getMessage(),PACKET_CACHE.get(Thread.currentThread().getName()));
            }
            return;
        }
        out.add(packet);
    }
}

```

> 这个解码适配器会根据报文的业务标志从`DecoderFactory`获取具体的解码器去解析报文。
>
> 所以如果需要扩展，只需要编写相关业务的解码器并添加到`DecoderFactory`即可。

```java
package cn.xiuminglee.jt809.protocol.decoder;


import cn.xiuminglee.jt809.common.Const;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xiuming Lee
 * @Describe: 解码工厂类
 */
public class DecoderFactory {
    private static Map<Short,Decoder> DECODER_FACTORY = new HashMap<>();
    static {
        DECODER_FACTORY.put(Const.BusinessDataType.UP_CONNECT_REQ,new LoginDecoder());
        DECODER_FACTORY.put(Const.BusinessDataType.UP_LINKTEST_REQ,new HeartbeatDecoder());
        DECODER_FACTORY.put(Const.BusinessDataType.UP_EXG_MSG,new JT809Packet0x1202Decoder());
    }

    /**
     * @param businessDataType 业务数据类型标志
     * @return 具体的解码器
     */
    public static Decoder getDecoder(short businessDataType){
        return DECODER_FACTORY.get(businessDataType);
    }
}

```

## [GitHub地址](https://github.com/XiumingLee/MingRepository/tree/master/JT809-Server)

