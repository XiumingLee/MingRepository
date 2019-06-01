package cn.mrain22.qiniu;

import com.qiniu.common.QiniuException;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/1 16:12
 * @Version 1.0
 * @Describe: 七牛上传服务接口
 */
public interface MqiniuService {
    /**
     * 上传文件
     * @param fileBytes 文件的字节数组
     * @param fileName 文件名带后缀
     * @return 上传后文件的url路径
     */
    String uploadFile(byte[] fileBytes, String fileName) throws QiniuException;

    /**
     * 删除文件
     * @param key 上传文件后返回的url路径
     * @return
     */
    boolean deleteByKey(String key) throws QiniuException;
}
