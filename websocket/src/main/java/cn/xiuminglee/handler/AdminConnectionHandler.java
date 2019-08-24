package cn.xiuminglee.handler;

import cn.xiuminglee.entity.MessageEntity;
import cn.xiuminglee.util.WebSocketCountUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/8/20 7:36
 * @Version 1.0
 * @Describe: 管理员连接处理的Handler，管理员查看实时用户情况
 */
@Slf4j
public class AdminConnectionHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.warn("管理员连接成功->{}",session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage textMessage = (TextMessage)message;
        log.warn("收到消息->{}",textMessage);
        System.out.println(session);
        MessageEntity messageEntity = JSONObject.parseObject(textMessage.getPayload(), MessageEntity.class);
        WebSocketCountUtil.adminSessionAdd(session,messageEntity.getContent());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("传输消息出错"+"afterConnectionClosed");
        System.out.println(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.warn("管理员断开连接->{}",session);
        WebSocketCountUtil.adminSessionReduce(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
