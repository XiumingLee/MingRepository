package cn.xiuminglee.jt809.protocol.decoder;

import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.packet.JT809HeartbeatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 14:53
 * @Version 1.0
 * @Describe: 心跳解码器器
 */
public class HeartbeatDecoder implements Decoder{
    private static Logger log = LoggerFactory.getLogger(HeartbeatDecoder.class);
    @Override
    public JT809BasePacket decoder(byte[] bytes) {
        log.info("心跳解码器！");
        return new JT809HeartbeatResponse();
    }
}
