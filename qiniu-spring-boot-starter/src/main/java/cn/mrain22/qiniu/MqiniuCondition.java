package cn.mrain22.qiniu;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/1 16:03
 * @Version 1.0
 * @Describe: 校验类
 */
public class MqiniuCondition implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String ak = context.getEnvironment().getProperty("mqiniu.access-key");
        String sk = context.getEnvironment().getProperty("mqiniu.secret-key");
        String bucketName = context.getEnvironment().getProperty("mqiniu.bucket-name");

        /** 校验用户是否将配置信息填写完整*/
        if (StringUtils.isEmpty(ak)) {
            throw new RuntimeException("Lack of qiniuyun configuration:access-key");
        } else if (StringUtils.isEmpty(sk)) {
            throw new RuntimeException("Lack of mqiniuyun configuration:qiniu.secret-key");
        } else if (StringUtils.isEmpty(bucketName)) {
            throw new RuntimeException("Lack of qiniuyun configuration:qiniu.bucket-name");
        } else {
            return true;
        }
    }
}
