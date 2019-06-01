package cn.mrain22.qiniu;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/1 16:49
 * @Version 1.0
 * @Describe:
 */
// 装配配置属性

import com.qiniu.common.Region;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
// 自动装配这个properties类，读取yaml自定义内容
@EnableConfigurationProperties(MqiniuProperties.class)
// service类，@ConditionalOnClass某个 Class 位于类路径上，才会实例化一个Bean。也就是说，当classpath下发现该类的情况下进行实例化。
@ConditionalOnClass(MqiniuService.class)
// 校验类
@Conditional(MqiniuCondition.class)
// 当配置文件中 mqiniu 的值为 true 时，实例化此类。可以不填
@ConditionalOnProperty(prefix = "mqiniu", value = "true", matchIfMissing = true)
public class MqiniuServiceAutoConfiguration {
    @Autowired
    private MqiniuProperties mqiniuProperties;

    /**
     * 指定实例化接口的类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MqiniuService qiNiuYunService() {
        return new MqiniuServiceImpl();
    }

    /**
     * 构建一个七牛上传工具实例
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public UploadManager uploadManager() {
        return new UploadManager(new com.qiniu.storage.Configuration(Region.autoRegion()));
    }

    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(mqiniuProperties.getAccessKey(), mqiniuProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     * @return
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(),new com.qiniu.storage.Configuration(Region.autoRegion()));
    }
}
