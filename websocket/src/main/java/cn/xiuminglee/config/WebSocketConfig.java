package cn.xiuminglee.config;

import cn.xiuminglee.handler.AdminConnectionHandler;
import cn.xiuminglee.handler.UserConnectionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/8/19 20:51
 * @Version 1.0
 * @Describe:
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new UserConnectionHandler(),"/ws/userCount")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
        registry.addHandler(new AdminConnectionHandler(),"/ws/admin")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    /**
     * 每个底层WebSocket引擎都公开控制运行时特征的配置属性，例如消息缓冲区大小，空闲超时等。
     * 对于Tomcat，WildFly和GlassFish，您可以通过ServletServerContainerFactoryBean来配置WebSocket，如以下示例所示：
     * @return
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(300000L);
        return container;
    }

}
