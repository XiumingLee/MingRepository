package cn.xiuminglee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/8/22 14:34
 * @Version 1.0
 * @Describe: 自定义会话实体类，
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    private String userId;
    private WebSocketSession session;
}
