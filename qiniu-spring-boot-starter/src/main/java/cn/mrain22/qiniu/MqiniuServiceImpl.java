package cn.mrain22.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/1 16:29
 * @Version 1.0
 * @Describe: 七牛上传删除实现类
 */
public class MqiniuServiceImpl implements MqiniuService {

    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private Auth auth;
    @Autowired
    private MqiniuProperties mqiniuProperties;

    public String uploadFile(byte[] fileBytes, String fileName) throws QiniuException {
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID() + "." + fileExtName;
        Response response = uploadManager.put(fileBytes, newFileName, getUploadToken());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String key = mqiniuProperties.getFilePathPrefix() + putRet.key;
        return key;
    }

    public boolean deleteByKey(String key) throws QiniuException {
        key = key.replace(mqiniuProperties.getFilePathPrefix(),"");
        bucketManager.delete(mqiniuProperties.getBucketName(), key);
        return true;
    }

    /**
     * 获取上传凭证，普通上传
     */
    private String getUploadToken() {
        return this.auth.uploadToken(mqiniuProperties.getBucketName());

    }
}
