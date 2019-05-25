package com.we.core.push;

public class BdAndNotice extends PushMsg {

//	static final Logger log = LoggerFactory.getLogger(BdAndNotice.class);
//
//	public BdAndNotice() {
//		super.devType = DevType.ANDROID;
//		super.msgType = MsgType.NOTICE;
//	}
//
//	/**
//	 * 通知标题，可以为空；如果为空则设为appid对应的应用名;
//	 * 
//	 * @param title
//	 * @return
//	 */
//	public BdAndNotice title(String title) {
//		root.put("title", title);
//		return this;
//	}
//
//	/**
//	 * 通知文本内容，不能为空;
//	 * 
//	 * @param description
//	 * @return
//	 */
//	public BdAndNotice description(String description) {
//		root.put("description", description);
//		return this;
//	}
//
//	/**
//	 * android客户端自定义通知样式，如果没有设置默认为0;
//	 * 
//	 * @param builderId
//	 * @return
//	 */
//	public BdAndNotice notification_builder_id(int builderId) {
//		root.put("notification_builder_id", builderId);
//		return this;
//	}
//
//	/**
//	 * 只有notification_builder_id为0时有效，可以设置通知的基本样式包括(响铃：0x04;振动：0x02;可清除：0x01;),
//	 * 这是一个flag整形，每一位代表一种样式;
//	 * 
//	 * @param basicStyle
//	 * @return
//	 */
//	public BdAndNotice notification_basic_style(int basicStyle) {
//		root.put("notification_basic_style", basicStyle);
//		return this;
//	}
//
//	/**
//	 * 点击通知后的行为(1：打开Url; 2：自定义行为；3：默认打开应用;);
//	 * 
//	 * @param open_type
//	 * @return
//	 */
//	public BdAndNotice open_type(int open_type) {
//		root.put("open_type", open_type);
//		return this;
//	}
//
//	/**
//	 * 需要打开的Url地址，open_type为1时才有效;
//	 * 
//	 * @param url
//	 * @return
//	 */
//	public BdAndNotice url(String url) {
//		root.put("url", url);
//		return this;
//	}
//
//	/**
//	 * pen_type为2时才有效，Android端SDK会把pkg_content字符串转换成Android
//	 * Intent,通过该Intent打开对应app组件，所以pkg_content字符串格式必须遵循Intent
//	 * uri格式，最简单的方法可以通过Intent方法toURI()获取
//	 * 
//	 * @param pkg_content
//	 * @return
//	 */
//	public BdAndNotice pkg_content(String pkg_content) {
//		root.put("pkg_content", pkg_content);
//		return this;
//	}
//
//	/**
//	 * 自定义内容，键值对，Json对象形式(可选)；在android客户端，这些键值对将以Intent中的extra进行传递。
//	 * 
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	public BdAndNotice custom_content(String key, String value) {
//		Map<String, String> c = (Map<String, String>) root
//				.get("custom_content");
//		if (c != null) {
//			c.put(key, value);
//			root.put("custom_content", c);
//		} else {
//			c = new HashMap<String, String>();
//			c.put(key, value);
//			root.put("custom_content", c);
//		}
//		return this;
//	}
//
//	public String toString() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			return objectMapper.writeValueAsString(root);
//		} catch (JsonProcessingException e) {
//			log.error("JsonErr:", e);
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(new BdAndNotice().title("你好").description("dddd")
//				.custom_content("aaa", "bbb").custom_content("bbb", "ccc")
//				.custom_content("abab", "cccc").pkg_content("aaa")
//				.notification_basic_style(7).notification_builder_id(1)
//				.toString());
//	}
}
