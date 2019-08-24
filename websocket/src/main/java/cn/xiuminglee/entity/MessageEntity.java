package cn.xiuminglee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/8/24 11:45
 * @Version 1.0
 * @Describe: 消息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    /** 1：连接；2：消息*/
    private String type;
    private String content;
}
