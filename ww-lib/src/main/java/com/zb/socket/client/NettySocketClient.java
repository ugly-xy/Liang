package com.zb.socket.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.utils.JSONUtil;
import com.zb.socket.client.headler.DefaultClientHandler;
import com.zb.socket.codec.JsonDecoder;
import com.zb.socket.codec.JsonEncoder;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Socket 客户端
 *
 * @author walker.bao
 */
public class NettySocketClient {

	static final Logger log = LoggerFactory.getLogger(NettySocketClient.class);

	private String host;
	private int port;
	private Map<String, ChannelFuture> channels = new ConcurrentHashMap<String, ChannelFuture>();
	private final Lock lock = new ReentrantLock();

	// private ChannelFuture cf;
	// EventLoopGroup workerGroup = new NioEventLoopGroup();

	public void sendMsg(Msg msg) throws Exception {
		sendMsg(msg, host, port);
	}

	public void sendMsg(final Msg msg, String host, int port)
			throws InterruptedException {
		String key = getKey(host, port);
		if (!channels.containsKey(key)) {
			synchronized (NettySocketClient.class) {
				if (!channels.containsKey(key)) {
					connect(host, port);
				}
			}
		}
		if (!channels.get(key).channel().isActive()) {
			stop(host, port);
			connect(host, port);
		}
		// log.error(JSONUtil.beanToJson(msg));
		lock.lock();
		try {
			channels.get(key).channel().writeAndFlush(msg);
		} finally {
			lock.unlock();
		}
	}

	private static final int MAX_FRAME_LENGTH = 1024 * 1024;

	private static String getKey(String host, int port) {
		return host + ":" + port;
	}

	public void stop(String host, int port) throws InterruptedException {
		String key = getKey(host, port);
		ChannelFuture cf = channels.get(key);
		channels.remove(key);
		if (cf != null) {
			cf.channel().closeFuture().sync();
			cf = null;
		}
	}

	public NettySocketClient() {
	}

	/**
	 * 
	 */
	public NettySocketClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect(String host, int port) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap(); // (1)

		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.group(boss); // (2)
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("decoder",
						new JsonDecoder(MAX_FRAME_LENGTH, 0, 4, 0, 0));
				ch.pipeline().addLast("encoder", new JsonEncoder());
				ch.pipeline().addLast(new DefaultClientHandler());
			}
		});
		// 启动客户端
		//b.remoteAddress(host, port);
		//ChannelFuture cf = b.connect().addListener(new ConnectionListener(this));//.connect(host, port).sync();
		ChannelFuture cf = b.connect(host, port).sync();
		if (cf.isSuccess()) {
			channels.put(getKey(host, port), cf);
			System.out.println("connect server  成功---------");
		}

	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws Exception {
		NettySocketClient nsc = new NettySocketClient("192.168.1.112", 9528);
		// nsc.connect();
		MapBody mb = new MapBody()
				.append("token", "59a8e758ffeaefabf814095b64ec9b324e13602a")
				.append("clientId", "aaaa").append("devName", "iphone 6");
		Msg msg = new Msg(1L, MsgType.LOGIN, mb.getData());
		nsc.sendMsg(msg);
		MapBody mb2 = new MapBody().append("type", "txt")
				.append("msg", "iphone 6s ").append("to", 100000002);
		Msg newM = new Msg(System.currentTimeMillis(), MsgType.CHAT,
				mb2.getData());
		nsc.sendMsg(newM);
		Thread.sleep(500 * 1000);
		MapBody mb3 = new MapBody().append("type", "1").append("id", 100000002);
		Msg m3 = new Msg(System.currentTimeMillis(), MsgType.SYN, mb3.getData());
		nsc.sendMsg(m3);

	}
}
