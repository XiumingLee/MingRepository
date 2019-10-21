package cn.xiuminglee.jt809.common.util;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 8:49
 * @Version 1.0
 * @Describe: 加密解密：只数据体加密
 */
public class JT809Encrypt {
    private static final Long UINT32_MAX_VALUE = 4294967295L;

    private static byte[] encrypt(long M1, long IA1, long IC1, long key, byte[] bytes) {
        // 第一个传入的key默认认为小于等于UINT32_MAX_VALUE
        int index = 0;
        if (0 == key) {
            key = 1;
        }
        int size = bytes.length;
        if (M1 == 0) {
            // 排除除以0的异常
            return new byte[0];
        }
        while (index < size) {
            key = IA1 * (key % M1) + IC1;

            // 若key大于，取低位32位,java是有符号的所以unit32必须要用long类型类承接，而key为long类型时
            // key = IA1 * (key % M1) + IC1很可能大于unit32的最大值，java实现里面是正常的，但是对比c#
            // 加密后的数组字符串发现不一致，后来发现原始实现中用的uint32，当大于他的最大值是高位会被丢弃
            // 也就是&UINT32_MAX_VALUE后的值才是正确的值
            if (key > UINT32_MAX_VALUE) {
                key &= UINT32_MAX_VALUE;
            }
            byte b = (byte) ((key >> 20) & 0xff);
            bytes[index++] ^= b;
        }
        return bytes;
    }
}
