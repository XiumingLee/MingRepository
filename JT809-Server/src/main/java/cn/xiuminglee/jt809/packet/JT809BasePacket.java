package cn.xiuminglee.jt809.packet;

import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.CrcUtil;

import java.util.Arrays;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:01
 * @Version 1.0
 * @Describe: 基础报文实体类
 */
public abstract class JT809BasePacket {
    /**
     *  不算消息体固定为26个字节长度
     *  Head flag + Message Header + CRC Code + End Flag
     *  1 + 22 + 2 + 1 = 26
     */
    public static final int FIXED_BYTE_LENGTH = 26;
    
    /** 头标识*/
    public static final byte HEAD_FLAG = 0x5b;
    /** 数据长度(包括头标识、数据头、数据体和尾标识) 4字节*/
    private int msgLength;
    /** 报文序列号 4字节*/
    private int msgSn;
    /** 业务数据类型 2字节*/
    private short msgId;
    /** 下级平台接入码，上级平台给下级平台分配唯一标识码。4字节*/
    private int msgGNSSCenterId;
    /**
     * 协议版本号标识，上下级平台之间采用的标准协议版
     *  编号；长度为 3 个字节来表示，0x01 0x02 0x0F 标识
     *  的版本号是 v1.2.15，以此类推。
     *  Hex编码 ,这个是3个字节，需要注意
     */
    private byte[] versionFlag;
    /** 报文加密标识位 b: 0 表示报文不加密，1 表示报文加密。0x00 0x01,这里默认不加密 1字节*/
    private byte encryptFlag = 0x00;
    /** 数据加密的密匙，长度为 4 个字节*/
    private int encryptKey;

    /////////////////
    ////数据体在这///
    public abstract byte[] getMsgBodyByteArr();
    ///////////////

    /** CRC 校验码 2字节*/
    private int crcCode;
    /** 尾标识*/
    public static final byte END_FLAG = 0x5d;
    /** 获取需要校验的部分*/
    private byte[] getNeedCRCBody(){
        byte[] msgLengthBytes = CommonUtils.int2bytes(this.msgLength);
        byte[] msgSnBytes = CommonUtils.int2bytes(this.msgSn);
        byte[] bytes1 = CommonUtils.append(msgLengthBytes, msgSnBytes);
        byte[] msgIdBytes = CommonUtils.short2Bytes(this.msgId);
        byte[] bytes2 = CommonUtils.append(bytes1, msgIdBytes);
        byte[] msgGNSSCenterIdBytes = CommonUtils.int2bytes(this.msgGNSSCenterId);
        byte[] bytes3 = CommonUtils.append(bytes2, msgGNSSCenterIdBytes);
        byte[] bytes4 = CommonUtils.append(bytes3, this.versionFlag);
        byte[] bytes5 = CommonUtils.append(bytes4, new byte[]{this.encryptFlag});
        byte[] encryptKeyBytes = CommonUtils.int2bytes(this.encryptKey);
        byte[] bytes6 = CommonUtils.append(bytes5, encryptKeyBytes);
        byte[] bytes = CommonUtils.append(bytes6, getMsgBodyByteArr());
        return bytes;
    }

    /**
     * 没有头标志和尾标志
     * @return
     */
    public byte[] getAllBody(){
        byte[] needCRCBody = getNeedCRCBody();
        short crc16 = CrcUtil.getCRC16(needCRCBody);
        byte[] crcCode = CommonUtils.short2Bytes(crc16);
        byte[] bytes = CommonUtils.append(needCRCBody, crcCode);
        return bytes;
    }


    public static int getFixedByteLength() {
        return FIXED_BYTE_LENGTH;
    }

    public static byte getHeadFlag() {
        return HEAD_FLAG;
    }

    public static byte getEndFlag() {
        return END_FLAG;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }

    public short getMsgId() {
        return msgId;
    }

    public void setMsgId(short msgId) {
        this.msgId = msgId;
    }

    public int getMsgGNSSCenterId() {
        return msgGNSSCenterId;
    }

    public void setMsgGNSSCenterId(int msgGNSSCenterId) {
        this.msgGNSSCenterId = msgGNSSCenterId;
    }

    public byte[] getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public byte getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(byte encryptFlag) {
        this.encryptFlag = encryptFlag;
    }

    public int getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(int encryptKey) {
        this.encryptKey = encryptKey;
    }


    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    @Override
    public String toString() {
        return "JT809BasePacket{" +
                "msgLength=" + msgLength +
                ", msgSn=" + msgSn +
                ", msgId=" + msgId +
                ", msgGNSSCenterId=" + msgGNSSCenterId +
                ", versionFlag=" + Arrays.toString(versionFlag) +
                ", encryptFlag=" + encryptFlag +
                ", encryptKey=" + encryptKey +
                ", crcCode=" + crcCode +
                '}';
    }
}
