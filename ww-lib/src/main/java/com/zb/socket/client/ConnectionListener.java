package com.zb.socket.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.util.Timeout;

public class ConnectionListener implements ChannelFutureListener {  
	  
	  
    private NettySocketClient client;  
  
  
    public ConnectionListener(NettySocketClient client) {  
        this.client = client;  
    }  
  
  
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {  
        if (!future.isSuccess()) {  
            System.out.println("Reconnection");  
            final EventLoop eventLoop = future.channel().eventLoop();  
            eventLoop.schedule(new Runnable() {  
  
  
                @Override  
                public void run() {  
//					client.createBootstrap(new Bootstrap(), eventLoop);  
                }  
            }, 1L, TimeUnit.SECONDS);  
        }  
    }


//	@Override
//	public void operationComplete(ChannelFuture future) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}  
  
  
}  
