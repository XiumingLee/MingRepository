package cn.xiuminglee.jt809.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:35
 * @Version 1.0
 * @Describe: 登录请求包，此中的结尾数据体的消息。
 */
public class JT809LoginPacket extends JT809BasePacket {
    private static Logger log = LoggerFactory.getLogger(JT809LoginPacket.class);
    /** id 4字节*/
    private int userId;
    /** 密码 8字节*/
    private String password;
    /** 下级平台IP 32字节*/
    private String downLinkIp;
    /** 下级平台端口 2字节*/
    private short downLinkPort;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDownLinkIp() {
        return downLinkIp;
    }

    public void setDownLinkIp(String downLinkIp) {
        this.downLinkIp = downLinkIp;
    }

    public short getDownLinkPort() {
        return downLinkPort;
    }

    public void setDownLinkPort(short downLinkPort) {
        this.downLinkPort = downLinkPort;
    }

    @Override
    public byte[] getMsgBodyByteArr() {
        return new byte[0];
    }

    @Override
    public String toString() {
        return "JT809LoginPacket{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", downLinkIp='" + downLinkIp + '\'' +
                ", downLinkPort='" + downLinkPort + '\'' +
                super.toString() +
                '}';
    }
}
