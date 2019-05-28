package com.zb.socket.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.crypto.MDUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 * 说明：Jackson json 解码器
 *
 * @author
 */
public class JsonDecoder extends LengthFieldBasedFrameDecoder {

	public static final Logger log = LoggerFactory.getLogger(JsonDecoder.class);

	private static final int HEADER_SIZE = 4;
	private static final Msg hb = new Msg(9L, MsgType.HEARTBEAT, new MapBody().append("hb", "hb").getData());

	public JsonDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
	}

	@Override
	protected Msg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, in2);
		if (in == null) {
			//ReferenceCountUtil.release(in2);
			return null;
		}
		try {
			if (in.readableBytes() < HEADER_SIZE) {
				//ReferenceCountUtil.release(in2);
				return null;// response header is 10 bytes
			}
			int len = in.readInt();
			if (in.readableBytes() < len) {
				//ReferenceCountUtil.release(in2);
				return null; // until we have the entire payload return
			}
			// System.out.println("LEN---->" + len);
			Msg msg = null;
			if (len < 31) {
				// log.error("HB ----len:" + len);
				in.readBytes(len);
				msg = hb;
				//ReferenceCountUtil.release(in2);
			} else {
				msg = new Msg();
				msg.set_id(in.readLong());
				msg.setVia(in.readByte());
				msg.setV(in.readShort());
				msg.setT(in.readInt());
				msg.setDt(in.readLong());
				in.readShort();// 读取预留2字节
				msg.setFr(in.readInt());
				byte crypto = in.readByte();
				msg.setC(crypto);
				boolean isS = in.readByte() == (byte) 0 ? false : true;
				String sign = null;
				int bodyLen;
				if (isS) {
					bodyLen = len - 63;
					ByteBuf buf = in.readBytes(32);
					byte[] csign = new byte[buf.readableBytes()];
					buf.readBytes(csign);
					ReferenceCountUtil.release(buf);
					sign = new String(csign);
				} else {
					bodyLen = len - 31;
				}
				ByteBuf buf = in.readBytes(bodyLen);
				byte[] req = new byte[buf.readableBytes()];
				buf.readBytes(req);
				ReferenceCountUtil.release(buf);
				String json = new String(req, "UTF-8");
				MsgType mt = MsgType.valueOf(msg.getT());
//				if(MsgType.UNDERCOVER.getT()==msg.getT()||MsgType.DICE.getT()== msg.getT()||MsgType.DRAWSOMETHING.getT()==msg.getT()){
//					log.error(msg.get_id()+" "+msg.getT()+" "+json);
//				}
				if (!mt.isSign()) {// 服务器不需要签名
					msg.setOk(true);
				} else if (isS && mt.isSign()) {// 服务器需要签名，客户端没有签名
					String curSign = MDUtil.MD5.digest2HEX(
							json + ctx.channel().remoteAddress().toString().substring(1) + msg.getT() + msg.get_id(),
							true);
					if (curSign.equals(sign)) {
						msg.setOk(true);
					}
					// System.out.println(curSign + "\n" + sign);
				}
				if (crypto != 0) {
					// TODO
				} else {
					if (MsgType.SYS.getT() == msg.getT()) {
						msg.setO(JSONUtil.jsonToBean(json, Msg.class));
					} else {
						msg.setO(JSONUtil.jsonToMap(json));
					}
				}
			}
			//System.out.println(JSONUtil.beanToJson(msg));
			return msg;
		} finally {
			ReferenceCountUtil.release(in);
		}
	}
}
