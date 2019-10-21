package cn.xiuminglee.jt809.common.util;

import cn.xiuminglee.jt809.packet.JT809BasePacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.xiuminglee.jt809.common.util.CommonUtils.PACKET_CACHE;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 16:55
 * @Version 1.0
 * @Describe: 报文解码工具包
 */
public class PacketDecoderUtils {
    private static Logger log = LoggerFactory.getLogger(PacketDecoderUtils.class);

    public static byte[] decoderEscape(ByteBuf buf){
        byte[] originalPacket = CommonUtils.getByteArray(buf);
        byte[] correctPacket = decoderEscape(originalPacket);
        StringBuilder packetInfo = new StringBuilder();
        packetInfo.append("原始报文：").append(bytes2HexStr(originalPacket)).append("；转义后的报文：").append(bytes2HexStr(correctPacket));
        PACKET_CACHE.put(Thread.currentThread().getName(),packetInfo.toString());
        return correctPacket;
    }

    public static byte[] decoderEscape(byte[] originalPacket){
        String dataStr = bytes2FullHexStr(originalPacket);
        dataStr = dataStr.replaceAll("0x5a0x01", "0x5b");
        dataStr = dataStr.replaceAll("0x5a0x02", "0x5a");
        dataStr = dataStr.replaceAll("0x5e0x01", "0x5d");
        dataStr = dataStr.replaceAll("0x5e0x02", "0x5e");
        byte[] bytes = fullHexStr2Bytes(dataStr);
        return bytes;
    }

    /**
     * 数组转换成十六进制字符串，不带0x
     * @param array
     * @return HexString
     */
    public static String bytes2HexStr(byte[] array) {
        StringBuffer sb = new StringBuffer(array.length);
        String sTemp;
        for (int i = 0; i < array.length; i++) {
            sTemp = Integer.toHexString(0xFF & array[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 数组转换成带0x的十六进制字符串
     * @param array
     * @return HexString
     */
    public static String bytes2FullHexStr(byte[] array) {
        StringBuffer sb = new StringBuffer(array.length);
        sb.append("0x");
        String sTemp;
        for (int i = 0; i < array.length; i++) {
            sTemp = Integer.toHexString(0xFF & array[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp);
            if(i < array.length-1){
                sb.append("0x");
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 把16进制字符串不带0x的转换成字节数组
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * 把带0x的16进制字符串转换成字节数组
     * @param hex
     * @return byte[]
     */
    public static byte[] fullHexStr2Bytes(String hex){
        hex = hex.toLowerCase().replaceAll("0x","").trim().toUpperCase();
        return hexStr2Bytes(hex);
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 基础解析，解析数据体之前的内容，公共的。
     * @param bytes
     * @param packet
     */
    public static ByteBuf baseDecoder(byte[] bytes, JT809BasePacket packet) throws Exception {
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        byteBuf.skipBytes(1);
        packet.setMsgLength(byteBuf.readInt());
        packet.setMsgSn(byteBuf.readInt());
        packet.setMsgId(byteBuf.readShort());
        packet.setMsgGNSSCenterId(byteBuf.readInt());
        byte[] versionFlag = new byte[3];
        byteBuf.readBytes(versionFlag);
        packet.setVersionFlag(versionFlag);
        packet.setEncryptFlag(byteBuf.readByte());
        packet.setEncryptKey(byteBuf.readInt());
        return byteBuf;
    }

    /**
     * 获取数据体的 ByteBuf
     * @param byteBuf 是从数据体开始的ByteBuf
     * @return
     */
    public static ByteBuf getMsgBodyBuf(ByteBuf byteBuf) {
        byte[] msgBodyByteArr = getMsgBodyByteArr(byteBuf);
        return CommonUtils.getByteBuf(msgBodyByteArr);
    }

    /**
     * 获取数据体的 ByteArr
     * @param byteBuf 是从数据体开始的ByteBuf
     * @return
     */
    public static byte[] getMsgBodyByteArr(ByteBuf byteBuf) {
        // 1、获取数据数组
        int msgBodyLength = byteBuf.readableBytes()-3;
        byte[] msgBodyByteArr = new byte[msgBodyLength];
        byteBuf.readBytes(msgBodyByteArr);

        return msgBodyByteArr;
    }


}
