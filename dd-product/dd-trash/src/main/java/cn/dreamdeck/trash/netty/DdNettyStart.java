package cn.dreamdeck.trash.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

//物联网 开启检测 并写入数据库
@Component
public class DdNettyStart {


	private static final Logger logger = LoggerFactory.getLogger(DdNettyStart.class);
	@Resource
	private DdServerHandler serverHandler;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workGroup = new NioEventLoopGroup();


	/**
	 * 自研设备启动netty服务
	 * @throws InterruptedException
	 */
	@PostConstruct
	public void start() throws InterruptedException {
		//crc
		ServerBootstrap b=new ServerBootstrap();
		//dd
		ServerBootstrap t =new ServerBootstrap();
		t.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG,1024)//连接数
				.option(ChannelOption.TCP_NODELAY,true)//不延迟消息立刻发送
				.childHandler(new ChannelInitializer<SocketChannel>()  {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(serverHandler);
					}
				});
		b.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG,1024)//连接数
				.option(ChannelOption.TCP_NODELAY,true)//不延迟消息立刻发送
				.childHandler(new ChannelInitializer<SocketChannel>()  {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
//						ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//						socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
						socketChannel.pipeline().addLast(new IdleStateHandler(10,0,0, TimeUnit.SECONDS));
						socketChannel.pipeline().addLast(serverHandler);
					}
				});
		ChannelFuture future = b.bind(20007).sync();//开启需要监听 的端口 多开端口
		if (future.isSuccess()) {
			logger.info("垃圾桶netty启用成功 端口 20007 启用成功");
		}
	}

	/**
	 * 销毁
	 */
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully().syncUninterruptibly();
		workGroup.shutdownGracefully().syncUninterruptibly();
		System.out.println("关闭 Netty 成功");
	}
}
