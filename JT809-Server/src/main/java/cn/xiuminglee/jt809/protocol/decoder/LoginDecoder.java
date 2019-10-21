package cn.xiuminglee.jt809.protocol.decoder;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.common.util.PacketDecoderUtils;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.packet.JT809LoginPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 21:02
 * @Version 1.0
 * @Describe: 登录解码器
 */
public class LoginDecoder implements Decoder {
    private static Logger log = LoggerFactory.getLogger(LoginDecoder.class);

    @Override
    public JT809BasePacket decoder(byte[] bytes) throws Exception {
        JT809LoginPacket loginPacket = new JT809LoginPacket();
        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, loginPacket);
        loginPacketDecoder(byteBuf,loginPacket);
        return loginPacket;
    }

    /**
     *
     * @param byteBuf
     * @param loginPacket
     */
    private void loginPacketDecoder(ByteBuf byteBuf,JT809LoginPacket loginPacket) throws Exception{
        ByteBuf msgBodyBuf = null;
        if (loginPacket.getEncryptFlag() == Const.EncryptFlag.NO) {
            log.info("报文未加密！继续处理。");
            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
        } else {
            // TODO: 后续处理
            log.info("报文已加密！未处理。");
            msgBodyBuf = null;
            return;
        }

        loginPacket.setUserId(msgBodyBuf.readInt());

        byte[] passwordBytes = new byte[8];
        msgBodyBuf.readBytes(passwordBytes);
        loginPacket.setPassword(new String(passwordBytes, Charset.forName("GBK")));

        // TODO ip和端口号的解析待确定
        byte[] downLinkIpBytes = new byte[32];
        msgBodyBuf.readBytes(downLinkIpBytes);
        loginPacket.setDownLinkIp(new String(downLinkIpBytes));

        loginPacket.setDownLinkPort(msgBodyBuf.readShort());

    }
}
