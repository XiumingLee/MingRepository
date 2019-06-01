package cn.mrain22.qiniu;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/1 12:31
 * @Version 1.0
 * @Describe: 七牛配置文件
 */
@ConfigurationProperties(prefix = "mqiniu")
public class MqiniuProperties {

    /** accessKey*/
    private String accessKey;
    /** secretKey*/
    private String secretKey;
    /** 要上传的空间*/
    private  String bucketName;
    /** 文件地址前缀，cdn地址*/
    private String filePathPrefix;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFilePathPrefix() {
        return filePathPrefix;
    }

    public void setFilePathPrefix(String filePathPrefix) {
        this.filePathPrefix = filePathPrefix;
    }
}

