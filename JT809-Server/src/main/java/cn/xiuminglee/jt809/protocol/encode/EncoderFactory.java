package cn.xiuminglee.jt809.protocol.encode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 10:58
 * @Version 1.0
 * @Describe:
 */
public class EncoderFactory {
    private static Map<String,Encoder> ENCODER_FACTORY = new HashMap();
    static {
        ENCODER_FACTORY.put("JT809LoginResponsePacket",new LoginResponseEncoder());
    }

    public static Encoder getEncoder(String packetClassName){
        return ENCODER_FACTORY.get(packetClassName);
    }
}
