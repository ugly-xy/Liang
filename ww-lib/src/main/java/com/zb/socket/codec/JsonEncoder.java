/**
 * 
 */
package com.zb.socket.codec;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.crypto.MDUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.socket.model.Msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 说明： json 编码器
 *
 * 包类型2byte｜版本2byte｜包签名16byte｜包长度4byte（包体长度）｜包体
 *
 * @author
 */

public class JsonEncoder extends MessageToByteEncoder<Msg> {

	public static final Logger log = LoggerFactory.getLogger(JsonEncoder.class);

	static final Charset charset = Charset.forName("utf-8");

	//private final Lock lock = new ReentrantLock();

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out)
			throws Exception {
		//lock.lock();
		//try {
			String dataStr = JSONUtil.beanToJson(msg.getO());
			//log.error(JSONUtil.beanToJson(msg));
			byte[] data = dataStr.getBytes(charset);
			int len;
			String sign = null;
			if (msg.isHasS()) {
				len = data.length + 63;
				sign = MDUtil.MD5.digest2HEX(dataStr
						+ ctx.channel().localAddress().toString().substring(1)
						+ msg.getT() + msg.get_id(), true);
			} else {
				len = data.length + 31;
			}
			out.writeInt(len);// 包内容长度4byte
			out.writeLong(msg.get_id());
			out.writeByte(msg.getVia());
			out.writeShort(msg.getV());
			out.writeInt(msg.getT());
			out.writeLong(msg.getDt() == 0 ? System.currentTimeMillis() : msg
					.getDt());
			out.writeShort(0);// 写入2byte预留字节
			out.writeInt(msg.getFr());
			out.writeByte(msg.getC());
			out.writeByte(msg.isHasS() ? 1 : 0);
			if (msg.isHasS()) {
				out.writeBytes(sign.getBytes());
			}
			out.writeBytes(data);// 包内容
//		} finally {
//			lock.unlock();
//		}
	}

}
