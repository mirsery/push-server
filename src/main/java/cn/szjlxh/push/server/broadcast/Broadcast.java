package cn.szjlxh.push.server.broadcast;

import cn.szjlxh.push.server.util.Configuration;
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

public class Broadcast {

    private Configuration configuration;

    private static final Logger log = LoggerFactory.getLogger(Broadcast.class);

    public Broadcast(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup);

        bootstrap.channel(NioServerSocketChannel.class)
                .childHandler(new BroadcastInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(new InetSocketAddress(configuration.getBroadcastServerPort())).addListener(new FutureListener<Void>() {

            public void operationComplete(Future<Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("BroadcastServer start on " + configuration.getBroadcastServerPort() + " now! ");
                } else {
                    log.info("BroadcastServer start failed ! ");
                }
            }
        }).channel().closeFuture().syncUninterruptibly();
    }

}