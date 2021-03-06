package com.we.socket.server.websocket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.we.socket.store.DBInit;
import com.we.socket.store.RedisInit;
import com.we.socket.store.RedisRecvListener;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
public class WebSocketServer {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/application.xml");

		DBInit.init(context);
		RedisInit.init(context);
		// KafkaInit.init(context);

		/** 启动socket */
		// FIXME 1、socket 启动
		context.getBean("webSocketServer", WebSocketServer.class).run();
		
		//new WebSocketServer().run();
	}
	
	RedisRecvListener redisRecvListener;
	int port = 7397;

	public RedisRecvListener getRedisRecvListener() {
		return redisRecvListener;
	}

	public void setRedisRecvListener(RedisRecvListener redisRecvListener) {
		this.redisRecvListener = redisRecvListener;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void run(){
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel e) throws Exception {
					e.pipeline().addLast("http-codec",new HttpServerCodec());
					e.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
					e.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
					e.pipeline().addLast("handler",new WebSocketServerHandler());
				}
			} );
			
			System.out.println("服务端开启等待客户端连接 ...:Port:"+port);
			
			Channel ch = b.bind(port).sync().channel();
			
			ch.closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	
} 
