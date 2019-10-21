package cn.xiuminglee.jt809.packet;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.common.util.CommonUtils;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:37
 * @Version 1.0
 * @Describe: 登录应答包
 */
public class JT809LoginResponsePacket extends JT809BasePacket {

    private static final int FIXED_LENGTH = 5;

    public JT809LoginResponsePacket() {
        setMsgLength(getFixedByteLength() + FIXED_LENGTH);
        setMsgSn(Const.getMsgSN());
        setMsgId(Const.BusinessDataType.UP_CONNECT_RSP);
        setMsgGNSSCenterId(Const.UserInfo.MSG_GNSSCENTERID);
        setVersionFlag(new byte[]{1,0,0});
        setEncryptFlag(Const.EncryptFlag.NO);
        setEncryptKey(0);
    }

    /** 标志 1位*/
    private byte resul;
    /** 校验码 4字节*/
    private int verifyCode;

    public byte getResul() {
        return resul;
    }

    public void setResul(byte resul) {
        this.resul = resul;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public byte[] getMsgBodyByteArr() {
        byte[] verifyCodeBytes = CommonUtils.int2bytes(this.verifyCode);
        return CommonUtils.append(new byte[]{this.resul},verifyCodeBytes);
    }

    @Override
    public String toString() {
        return "JT809LoginResponsePacket{" +
                "resul=" + resul +
                ", verifyCode=" + verifyCode +
                super.toString() +
                '}';
    }
}
