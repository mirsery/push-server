package cn.szjlxh.websocket.server.audience;

import cn.szjlxh.websocket.server.util.Configuration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class AudienceServer {

    private static Configuration configuration;

    public AudienceServer(Configuration configuration) {
        this.configuration = configuration;
    }

    private static final Logger log = LoggerFactory.getLogger("AudienceServer");

    public static void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup);

        bootstrap.channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(new InetSocketAddress(configuration.getAudienceServerPort())).addListener(new FutureListener<Void>() {
            public void operationComplete(Future<Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("AudienceServer start on "+configuration.getAudienceServerPort()+" now! ");
                } else {
                    log.info("AudienceServer start failed ! ");
                }
            }
        }).channel().closeFuture().syncUninterruptibly();
    }
}