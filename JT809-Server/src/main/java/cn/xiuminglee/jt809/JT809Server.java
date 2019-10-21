package cn.xiuminglee.jt809;

import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.PropertiesUtil;
import cn.xiuminglee.jt809.handle.JT809ServerInitialzer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 20:26
 * @Version 1.0
 * @Describe: 启动类
 */
public class JT809Server {
    private static Logger log = LoggerFactory.getLogger(JT809Server.class);

    private static int PORT;

    public static void main(String[] args) {
        PORT = Integer.parseInt(PropertiesUtil.getProperty("port","9090"));
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new JT809ServerInitialzer());
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("JT809Server在端口：{}启动成功!",port);
                CommonUtils.delDataTimer(); // 定时任务，定时删除垃圾数据
            } else {
                log.error("JT809Server在端口：{}启动失败!",port);
            }
        });
    }
}
