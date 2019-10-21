package cn.xiuminglee.jt809.common.util;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.xiuminglee.jt809.common.util.CommonUtils.PACKET_CACHE;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 20:14
 * @Version 1.0
 * @Describe: 校验工具类
 */
public class CrcUtil {
    private static Logger log = LoggerFactory.getLogger(CrcUtil.class);

    public static boolean checkCRC(byte[] bytes){
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        // 获取数据长度和crc标示
        byteBuf.skipBytes(1);
        int msgLength = CommonUtils.bytes2int(new byte[]{bytes[1],bytes[2],bytes[3],bytes[4]});
        int crcLength = msgLength - 4;
        byte[] crcBody = new byte[crcLength];
        byteBuf.readBytes(crcBody);

        short oldCRCcode = byteBuf.readShort();
        short currentCRCcode = getCRC16(crcBody);
        if (oldCRCcode == currentCRCcode) {
            return true;
        }
        log.error("crc校验失败;报文信息：{}",PACKET_CACHE.get(Thread.currentThread().getName()));
        return false;
    }

    public static short getCRC16(byte[] bytes) {
        short crc = (short) 0xffff;
        short polynomial = 0x1021;
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        byte[] short2Bytes = CommonUtils.short2Bytes(crc);
        return crc;
    }
}
