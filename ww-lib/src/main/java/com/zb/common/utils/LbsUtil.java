package com.zb.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.common.http.HttpClientUtil;

@SuppressWarnings("all")
public class LbsUtil {

	static final Logger log = LoggerFactory.getLogger(LbsUtil.class);
	static String AK = "rprl1RRYHGAvrNqUfSvjXxAk6S3mrDoX";
	static String SK = "wWkVBov3tPdtWEqO0dNuoI8pD5cvN03G";
	public static String[] citys = new String[] { "北京市", "天津市", "上海市", "重庆市", "香港特别行政区", "澳门特别行政区" };

	public static String getFmtProvince(AddressComponent ac) {
		if (ac.getProvince() != null) {
			return ac.getProvince().replaceAll("[省|市|回族自治区|壮族自治区|维吾尔自治区|自治区|特别行政区]", "");
		}
		return null;
	}

	public static String getFmtCity(AddressComponent ac) {
		if (ac.getCity() != null) {
			if (isDirect(ac.getCity())) {
				return ac.getDistrict().replace("区", "");
			} else {
				return ac.getCity().replaceAll("[市|地区|区|新区]", "");
			}
		}
		return null;
	}

	/** GPS加密 */
	public static Map geoconv(Double longitude, Double latitude) {
		try {
			String url = "http://api.map.baidu.com/geoconv/v1/?";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("ak", AK);
			paramsMap.put("coords", longitude + "," + latitude);
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geoconv/v1/?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + "ak=" + AK + "&coords=" + longitude + "," + latitude + "&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			if (MapUtil.getInt(m, "status") == 0) {
				return (LinkedHashMap) ((ArrayList) m.get("result")).get(0);
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	};

	/**
	 * 上传坐标
	 *
	 * @throws IOException
	 */
	public static Integer creatPoi(Double longitude, Double latitude, Long uid, Long loginTime, Long sex)
			throws IOException {
		// key uid
		// http://api.map.baidu.com/geodata/v3/poi/create // POST请求
		// 请求参数
		// 参数名 参数含义 类型 备注
		// title poi名称 string(256) 可选
		// address 地址 string(256) 可选
		// tags tags string(256) 可选
		// latitude 用户上传的纬度 double 必选
		// longitude 用户上传的经度 double 必选
		// coord_type 用户上传的坐标的类型 uint32 必选
		// 1：GPS经纬度坐标
		// 2：国测局加密经纬度坐标
		// 3：百度加密经纬度坐标
		// 4：百度加密墨卡托坐标
		// geotable_id 记录关联的geotable的标识 string(50) 必选
		// ak 用户的访问权限key string(50) 必选
		// sn 用户的权限签名 string(50) 可选
		// {column key} 用户在column定义的key/value对 开发者自定义的类型（string、int、double）
		// 唯一索引字段必选，且需要保证唯一，否则会创建失败
		if (uid == null) {
			return null;
		}
		String url = "http://api.map.baidu.com/geodata/v3/poi/create";
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("geotable_id", "166087");
		params.put("ak", AK);
		params.put("latitude", Double.toString(latitude));
		params.put("longitude", Double.toString(longitude));
		params.put("coord_type", "1");
		params.put("userId", Long.toString(uid));
		params.put("sex", Long.toString(sex));
		params.put("loginTime", Long.toString(loginTime));
		// post请求是按字母序填充，对上面的paramsMap按key的字母序排列
		Map<String, String> treeMap = new TreeMap<>(params);
		String paramsStr = toQueryString(treeMap);
		String wholeStr = new String("/geodata/v3/poi/create?" + paramsStr + SK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		// 调用下面的MD5方法得到sn签名值
		String sn = MD5(tempStr);
		params.put("sn", sn);
		String ret = URLDecoder.decode(HttpClientUtil.post(url, params, null));
		if (ret != null) {
			Map<String, Object> retMap = JSONUtil.jsonToMap(ret);
			return (Integer) retMap.get("status");
		}
		return null;
	}

	public static Map queryPoi(Long uid) {
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		try {
			if (null == uid) {
				return null;
			}
			String url = "http://api.map.baidu.com/geodata/v3/poi/list";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();
			paramsMap.put("ak", AK);
			paramsMap.put("geotable_id", "166087");
			paramsMap.put("userId", Long.toString(uid).concat(",").concat(Long.toString(uid)));
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geodata/v3/poi/list?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + "?ak=" + AK + "&geotable_id=166087&userId="
					+ Long.toString(uid).concat(",").concat(Long.toString(uid)) + "&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			if (MapUtil.getInt(m, "status") == 0 && null != m.get("pois")) {
				return (LinkedHashMap) ((ArrayList) (m.get("pois"))).get(0);
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	public static Map queryAllPoi() {
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		try {
			String url = "http://api.map.baidu.com/geodata/v3/poi/list";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();
			paramsMap.put("ak", AK);
			paramsMap.put("geotable_id", "166087");
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geodata/v3/poi/list?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + "?ak=" + AK + "&geotable_id=166087&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			if (MapUtil.getInt(m, "status") == 0 && null != m.get("pois")) {
				return m;
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	public static Integer updatePoi(Double longitude, Double latitude, Long uid, Long loginTime, Long sex)
			throws IOException {
		if (uid == null) {
			return null;
		}
		String url = "http://api.map.baidu.com/geodata/v3/poi/update";
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("geotable_id", "166087");
		params.put("ak", AK);
		params.put("coord_type", "1");
		params.put("latitude", Double.toString(latitude));
		params.put("longitude", Double.toString(longitude));
		params.put("userId", Long.toString(uid));
		params.put("sex", Long.toString(sex));
		params.put("loginTime", Long.toString(loginTime));
		// post请求是按字母序填充，对上面的paramsMap按key的字母序排列
		Map<String, String> treeMap = new TreeMap<>(params);
		String paramsStr = toQueryString(treeMap);
		String wholeStr = new String("/geodata/v3/poi/update?" + paramsStr + SK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		// 调用下面的MD5方法得到sn签名值
		String sn = MD5(tempStr);
		params.put("sn", sn);
		String ret = URLDecoder.decode(HttpClientUtil.post(url, params, null));
		if (ret != null) {
			Map<String, Object> retMap = JSONUtil.jsonToMap(ret);
			return (Integer) retMap.get("status");
		}
		return null;
	}

	public static AddressComponent getLocationLbs(String location) {
		// 调换经纬度
		String[] locations = location.split(",");
		location = locations[1] + "," + locations[0];
		String url = "http://api.map.baidu.com/geocoder/v2/?";
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("ak", AK);
		// paramsMap.put("sn", SK);
		paramsMap.put("location", location);
		paramsMap.put("output", "json");
		paramsMap.put("pois", "0");
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		try {
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geocoder/v2/?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + paramsStr + "&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			// String res = HttpClientUtil.post(url, paramsMap, null);
			// System.out.println(res);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			// for (String key : m.keySet()) {
			// System.out.println("k:" + key + ";v:" + m.get(key));
			// }
			if (MapUtil.getInt(m, "status") == 0) {
				Map<String, Object> rs = (Map<String, Object>) m.get("result");
				Map<String, Object> acm = (Map<String, Object>) rs.get("addressComponent");
				AddressComponent ac = new AddressComponent();
				ac.setCountry(MapUtil.getStr(acm, "country"));
				ac.setProvince(MapUtil.getStr(acm, "province"));
				ac.setCity(MapUtil.getStr(acm, "city"));
				ac.setDistrict(MapUtil.getStr(acm, "district"));
				ac.setStreet(MapUtil.getStr(acm, "street"));
				ac.setStreetNumber(MapUtil.getStr(acm, "street_number"));
				return ac;
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	// public static AddressComponent getSinaIpLbs(HttpServletRequest request) {
	// return IpUtil.getIpLbs(IpUtil.getIpAddr(request));
	// }

	public static AddressComponent getIpLbs(HttpServletRequest request) {
		String ip = IpUtil.getIpAddr(request);
		if (ip.contains("192.168") || ip.equals("127.0.0.1")) {
			return null;
		}
		return getIpLbs(ip);
	}

	public static AddressComponent getIpLbs(String ip) {
		String url = "http://api.map.baidu.com/location/ip?";
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("ip", ip);
		paramsMap.put("ak", AK);
		// paramsMap.put("output", "json");
		paramsMap.put("coor", "bd09ll");
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak

		try {
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/location/ip?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + paramsStr + "&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			// String res = HttpClientUtil.post(url, paramsMap, null);
			// System.out.println(res);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			// for (String key : m.keySet()) {
			// System.out.println("k:" + key + ";v:" + m.get(key));
			// }
			if (MapUtil.getInt(m, "status") == 0) {
				Map<String, Object> rs = (Map<String, Object>) m.get("content");
				Map<String, Object> acm = (Map<String, Object>) rs.get("address_detail");
				Map<String, Object> pt = (Map<String, Object>) rs.get("point");
				AddressComponent ac = new AddressComponent();
				if (isDirect(MapUtil.getStr(acm, "city"))) {
					if (StringUtils.isBlank(MapUtil.getStr(acm, "district"))) {
						return getLocationLbs(MapUtil.getStr(pt, "x") + "," + MapUtil.getStr(pt, "y"));
					}
				}
				ac.setCountry(MapUtil.getStr(acm, "country"));
				ac.setProvince(MapUtil.getStr(acm, "province"));
				ac.setCity(MapUtil.getStr(acm, "city"));
				ac.setDistrict(MapUtil.getStr(acm, "district"));
				ac.setStreet(MapUtil.getStr(acm, "street"));
				ac.setStreetNumber(MapUtil.getStr(acm, "street_number"));
				ac.setX(Double.parseDouble(MapUtil.getStr(pt, "x")));
				ac.setY(Double.parseDouble(MapUtil.getStr(pt, "y")));
				return ac;
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	public static void creatTable() throws Exception {
		String url = "http://api.map.baidu.com/geodata/v3/geotable/create";
		LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("geotype", "1");
		paramsMap.put("ak", AK);
		paramsMap.put("name", "geotable80");
		paramsMap.put("is_published", "1");

		// post请求是按字母序填充，对上面的paramsMap按key的字母序排列
		Map<String, String> treeMap = new TreeMap<String, String>(paramsMap);
		String paramsStr = toQueryString(treeMap);

		String wholeStr = new String("/geodata/v3/geotable/create?" + paramsStr + SK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		// 调用下面的MD5方法得到sn签名值
		String sn = MD5(tempStr);
		paramsMap.put("sn", sn);
		String result = HttpClientUtil.post(url, paramsMap, null);
		// 打印响应内容
		//System.out.println(result);
	}

	public static String queryTable() throws IOException {
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		try {
			String url = "http://api.map.baidu.com/geodata/v3/geotable/list";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("ak", AK);
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geodata/v3/geotable/list?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + "?ak=" + AK + "&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
//			if (MapUtil.getInt(m, "status") == 0) {
//				System.out.println("ok");
//			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	// 对Map内所有value作utf8编码，拼接返回结果
	public static String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException, URIException {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			// queryString.append(URLEncoder.encode((String) pair.getValue(),
			// "UTF-8") + "&");
			queryString.append(URIUtil.encodeQuery((String) pair.getValue(), "UTF-8") + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	// MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static void creatCol() throws IOException {
		// 2.6.1 请求url
		// http://api.map.baidu.com/geodata/v3/column/create  POST请求
		// 2.6.2 请求参数
		// 参数名 参数含义 类型 备注
		// name Column的属性中文名称 String(45) 必选
		// key column存储的属性key String(45) 必选，同一个geotable内的名字不能相同
		// type 存储的值的类型 UInt32 必选，枚举值
		// 1： Int64, 2:double, 3,string，4，在线图片url
		// max_length 最大长度 UInt32 必选
		// 最大值2048，最小值为1，针对string有效，并且string时必填。此值代表utf8的汉字个数，不是字节个数
		// default_value 默认值 String(45) 设置默认值
		// is_sortfilter_field 是否检索引擎的数值排序筛选字段 UInt32
		// 必选，1代表是，0代表否。设置后效果详见http://developer.baidu.com/map/lbs-geosearch.htm#.search.nearby
		// 最多只能设置15个
		// 只有int或者double类型可以设置
		// is_search_field 是否检索引擎的文本检索字段 UInt32 必选，1,代表支持，0为不支持。只有string可以设置
		// 检索字段只能用于字符串类型的列且最大长度不能超过512个字节 
		// Is_index_field 是否存储引擎的索引字段 UInt32 必选，用于存储接口查询
		// 1,代表支持，0为不支持。
		// 注：is_index_field=1
		// 时才能在根据该列属性值检索时检索到数据。设置后可用功能：http://developer.baidu.com/map/lbs-geodata.htm#.column.manage3.2
		// is_unique_field 是否云存储唯一索引字段 UInt32 可选
		// 用于更新，删除，查询：1代表支持 ，0为不支持
		// geotable_id 所属于的geotable_id String(50)  必选
		// ak 用户的访问权限key String(50) 必选
		// sn 用户的权限签名 String(50) 可选
		// geotable_id 166087
		String url = "http://api.map.baidu.com/geodata/v3/column/create";
		LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("geotable_id", "166087");
		paramsMap.put("ak", AK);
		paramsMap.put("name", "测试");
		paramsMap.put("key", "test");
		paramsMap.put("type", "1");
		paramsMap.put("is_search_field", "0");
		paramsMap.put("is_sortfilter_field", "1");
		paramsMap.put("Is_index_field", "1");
		paramsMap.put("is_unique_field", "0");
		// post请求是按字母序填充，对上面的paramsMap按key的字母序排列
		Map<String, String> treeMap = new TreeMap<String, String>(paramsMap);
		String paramsStr = toQueryString(treeMap);
		String wholeStr = new String("/geodata/v3/column/create?" + paramsStr + SK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		// 调用下面的MD5方法得到sn签名值
		String sn = MD5(tempStr);
		paramsMap.put("name", URLEncoder.encode("测试", "UTF-8"));
		paramsMap.put("sn", sn);
		String result = HttpClientUtil.post(url, paramsMap, null);
		// 打印响应内容
		//System.out.println(result);
	}

	public static String queryCol() {
		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		try {
			String url = "http://api.map.baidu.com/geodata/v3/column/list";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("ak", AK);
			paramsMap.put("geotable_id", "166087");
			String paramsStr = toQueryString(paramsMap);
			// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
			String wholeStr = new String("/geodata/v3/column/list?" + paramsStr + SK);
			// 对上面wholeStr再作utf8编码
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
			String sn = MD5(tempStr);
			url = url + "?ak=" + AK + "&geotable_id=166087&sn=" + sn;
			String res = HttpClientUtil.get(url, null);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
//			if (MapUtil.getInt(m, "status") == 0) {
//				System.out.println("ok");
//			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	public static List findNearBy(Double longitude, Double latitude, Long distance, Long sex, Long startTime,
			Long endTime, int page, int size) {
		try {
			String url = "http://api.map.baidu.com/geosearch/v3/nearby"; // GET请求";
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("ak", AK);
			if (sex == 0L) {
				paramsMap.put("filter", "loginTime:" + startTime + "," + endTime + "");
			} else {
				paramsMap.put("filter", "sex:" + sex + "," + sex + "|loginTime:" + startTime + "," + endTime + "");
			}
			paramsMap.put("geotable_id", "166087");
			paramsMap.put("location", Double.toString(longitude).concat(",").concat(Double.toString(latitude)));
			paramsMap.put("page_index", Integer.toString(page));
			paramsMap.put("page_size", Integer.toString(size));
			paramsMap.put("radius", Long.toString(distance));
			paramsMap.put("sortby", "distance:1");
			String paramsStr = toQueryString(paramsMap);
			String wholeStr = new String("/geosearch/v3/nearby?" + paramsStr + SK);
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			String sn = MD5(tempStr);
			String param = "";
			if (sex == 0L) {
				param = "ak=" + AK + "&filter=loginTime:" + startTime + "," + endTime + "&geotable_id=166087&location="
						+ Double.toString(longitude).concat(",").concat(Double.toString(latitude)) + "&page_index="
						+ page + "&page_size=" + size + "&radius=" + distance + "&sortby=distance:1&sn=" + sn;
			} else {
				param = "ak=" + AK + "&filter=sex:" + sex + "," + sex + "" + URLEncoder.encode("|", "UTF-8")
						+ "loginTime:" + startTime + "," + endTime + "&geotable_id=166087&location="
						+ Double.toString(longitude).concat(",").concat(Double.toString(latitude)) + "&page_index="
						+ page + "&page_size=" + size + "&radius=" + distance + "&sortby=distance:1&sn=" + sn;
			}
			url = url + "?" + param;
			String res = HttpClientUtil.get(url, null, HttpClientUtil.UTF8);
			Map<String, Object> m = JSONUtil.jsonToMap(res);
			if (MapUtil.getInt(m, "status") == 0 && MapUtil.getInt(m, "size") > 1) {
				return (List) ((ArrayList<LinkedHashMap>) m.get("contents")).stream()
						.filter(e -> MapUtil.getInt(e, "distance") > 0).map(e -> new HashMap() {
							{
								put("uid", MapUtil.getLong(e, "userId"));
								put("distance", MapUtil.getInt(e, "distance"));
							}
						}).collect(Collectors.toList());
			} else {
				return new ArrayList();
			}
		} catch (IOException e) {
			log.error("LBS error", e);
		}
		return null;
	}

	/** 上传最新位置 */
	public static Integer updateLocation(Double longitude, Double latitude, Long uid, Long loginTime, Long sex) {
		Map map = queryPoi(uid);
		if (map == null) {
			try {
				return LbsUtil.creatPoi(longitude, latitude, uid, loginTime, sex);
			} catch (IOException e) {
				log.error("创建坐标失败", e);
			}
		} else {
			try {
				return LbsUtil.updatePoi(longitude, latitude, uid, loginTime, sex);
			} catch (IOException e) {
				log.error("更新坐标失败", e);
			}
		}
		return null;
	}

	public static Integer updateLoginTime(Long uid, Long loginTime) throws IOException {
		if (uid == null) {
			return null;
		}
		String url = "http://api.map.baidu.com/geodata/v3/poi/update";
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("geotable_id", "166087");
		params.put("ak", AK);
		params.put("userId", Long.toString(uid));
		params.put("loginTime", Long.toString(loginTime));
		// post请求是按字母序填充，对上面的paramsMap按key的字母序排列
		Map<String, String> treeMap = new TreeMap<>(params);
		String paramsStr = toQueryString(treeMap);
		String wholeStr = new String("/geodata/v3/poi/update?" + paramsStr + SK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		// 调用下面的MD5方法得到sn签名值
		String sn = MD5(tempStr);
		params.put("sn", sn);
		String ret = URLDecoder.decode(HttpClientUtil.post(url, params, null));
		if (ret != null) {
			Map<String, Object> retMap = JSONUtil.jsonToMap(ret);
			return (Integer) retMap.get("status");
		}
		return null;
	}

	/** 判断是否是直辖市 */
	public static Boolean isDirect(String city) {
		return Arrays.asList(citys).contains(city);
	}

	public static void main(String[] args) {
		AddressComponent ac = getLocationLbs("116.40387397,39.91488908");
		if (ac != null) {
			System.out.println(ac.getCity());
			System.out.println(ac.getDistrict());
		}

		AddressComponent ac2 = getIpLbs("124.64.85.5");
		if (ac2 != null) {
			System.out.println(ac2.getCity());
			System.out.println(ac2.getDistrict());
		}

		// double latitude10 = 40.031483;//40.039986272634;// ;//
		// // 40.003217;//40.031753;
		// // 40.039986272634;//
		// double longitude10 = 116.411319;//116.42499898286;// //
		// // 116.409342;//116.411394;
		// // 116.42499898286;//
		// long key = 1000002L;
		// long distance = 5000L;
		// try {
		// Map map = geoconv(longitude10, latitude10);
		// // System.err.print(MapUtil.getDouble(map, "x"));
		// // System.err.print(MapUtil.getDouble(map, "y"));
		// // Map map = queryPoi(key);
		// // Map map2 = queryAllPoi();
		// List list = findNearBy(longitude10, latitude10, distance, 1L,
		// System.currentTimeMillis() - 100000000 * 1000,
		// System.currentTimeMillis(), 0, 12);
		// // Integer ret = updatePoi(longitude10,latitude10,key,
		// // System.currentTimeMillis(), 2L);
		// // queryTable();
		// // queryCol();
		// // creatCol();
		// // int ret = updateLoginTime(100109321L,
		// // System.currentTimeMillis());
		// System.out.println("111");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
