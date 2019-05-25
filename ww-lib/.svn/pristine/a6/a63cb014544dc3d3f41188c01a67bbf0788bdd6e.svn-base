package com.zb.common.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zb.common.http.HttpClientUtil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class IpUtil {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (null != ip && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (null != ip && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (null != ip && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

//	public static AddressComponent getIpLbs(String ip) {
//		try {
//			String res = HttpClientUtil.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=" + ip,
//					null);
//			if (StringUtils.isNotBlank(res)) {
//				String json = res.substring(res.indexOf("= ") + 2, res.length() - 1);
//				// System.out.println(json);
//				Map<String, Object> m = JSONUtil.jsonToMap(json);
//				if (MapUtil.getInt(m, "ret") == 1) {
//					AddressComponent ac = new AddressComponent();
//					if (MapUtil.getStr(m, "country") != null)
//						ac.setCountry(new String(MapUtil.getStr(m, "country")));
//					if (MapUtil.getStr(m, "city") != null)
//						ac.setCity(new String(MapUtil.getStr(m, "city")));
//					if (MapUtil.getStr(m, "province") != null)
//						ac.setProvince(new String(MapUtil.getStr(m, "province")));
//					// if (MapUtil.getStr(m, "district") != null)
//					// ac.setDistrict(new String(MapUtil.getStr(m,
//					// "district")));
//					// if (MapUtil.getStr(m, "street") != null)
//					// ac.setStreet(new String(MapUtil.getStr(m, "street")));
//					// if (MapUtil.getStr(m, "streetNumber") != null)
//					// ac.setStreetNumber(new String(MapUtil.getStr(m,
//					// "streetNumber")));
//					return ac;
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static List<InetAddress> getLocalInet4Address() throws SocketException {
		Enumeration allNetInterfaces;
		List<InetAddress> ips = new ArrayList<InetAddress>();
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();

		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			// System.out.println(netInterface.getName());
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					ips.add(ip);
					// System.out.println("本机的IP = " + ip.getHostAddress());
				}
			}
		}
		return ips;
	}
	
	public static void main(String[] args) {
		try {
			List<InetAddress> ips = getLocalInet4Address();
			for(InetAddress ip : ips){
				System.out.println(ip.getHostAddress()+" "+ip.isLinkLocalAddress()+" "+ip.isAnyLocalAddress());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
