package cn.xiuminglee.jt809.common;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 20:41
 * @Version 1.0
 * @Describe: 一些常量
 */
public class Const {
    /** 报文序列号*/
    private static int msg_sn = -1;
    public static int getMsgSN(){
        msg_sn += 1;
        return msg_sn;
    }

    /** 业务数据类型标志*/
     public interface BusinessDataType{
        /** 主链路登录请求消息*/
        short UP_CONNECT_REQ = 0x1001;
        /** 主链路登录应答消息*/
        short UP_CONNECT_RSP = 0x1002;
        /** 主链路连接保持请求消息*/
        short UP_LINKTEST_REQ = 0x1005;
        /** 主链路连接保持应答消息*/
        short UP_LINKTEST_RSP = 0x1006;
        /** 主链路动态信息交换消息*/
        short UP_EXG_MSG = 0x1200;
    }

    /** 子业务数据类型标志*/
    public interface SubBusinessDataType{
        /** 实时上传车辆定位信息*/
        short UP_EXG_MSG_REAL_LOCATION = 0x1202;
    }
    /** 主链路登录应答*/
    public interface LoginResponseCode{
        /** 0x00:成功;*/
        byte SUCCESS = 0x00;
        /** 0x01:IP地址不正确；*/
        byte IP_ERROR = 0x01;
        /** 0x02:接入码不正确；*/
        byte ACCESS_CODE_ERROR = 0x02;
        /** 0x03:用户没用注册；*/
        byte USERNAME_ERROR = 0x03;
        /** 0x04:密码错误;*/
        byte PASSWORD_ERROR = 0x04;
        /** 0x05:资源紧张，稍后再连接(已经占用）；*/
        byte RESOURCE_CRISIS = 0x05;
        /** 0x06：其他。*/
        byte OTHER_ERROR = 0x06;
    }
    /** 报文数据体是否加密*/
    public interface EncryptFlag{
        /** 加密*/
        byte YES = 0x01;
        /** 不加密*/
        byte NO = 0x00;
    }

    /** 用户信息，以下信息请自定义*/
    public interface UserInfo{
        int USER_ID = 0000000001;
        String PASSWORD = "密码";
        int MSG_GNSSCENTERID = 0000000001;
    }



}
