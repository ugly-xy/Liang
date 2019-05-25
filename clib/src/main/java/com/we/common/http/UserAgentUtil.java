package com.we.common.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentUtil {

	static final Logger log = LoggerFactory.getLogger(UserAgentUtil.class);

	public static final String OSTYPE_ANDROID = "And";
	public static final String OSTYPE_IOS = "iOS";
	public static final String OSTYPE_WP = "WP";
	public static final String OSTYPE_BLACKBERRY = "BB";
	public static final String OSTYPE_WIN = "W";
	public static final String OSTYPE_MAC = "Mac";
	public static final String OSTYPE_LINUX = "linux";

	public static final String DEVICE_TYPE_PAD = "Pad";
	public static final String DEVICE_TYPE_PHONE = "Phone";
	public static final String DEVICE_TYPE_PC = "PC";
	public static final String DEVICE_TYPE_POD = "Pod";

	public static UserAgent getUserAgent(HttpServletRequest req) {
		return getUserAgent(req.getHeader("User-Agent"));
	}

	public static UserAgent getUserAgent(String ua) {
		if (StringUtils.isBlank(ua)) {
			return null;
		}
		String userAgent = ua.toUpperCase();
		String rex = "";
		String devType = null;
		String os = null;
		String osVer = null;
		String netType = null;
		int nt = userAgent.indexOf("NETTYPE/");
		if (nt > -1) {
			netType = ua.substring(nt + 8);
			int end = netType.indexOf(" ");
			if (end > -1) {
				netType = netType.substring(0, end);
			}
		}

		log.debug("UserAgent:" + ua);
		if (userAgent.contains("ANDROID")) {
			if (isMatch(ua, "\\bMobi", Pattern.CASE_INSENSITIVE)) {
				devType = DEVICE_TYPE_PHONE;
			} else {
				devType = DEVICE_TYPE_PAD;
			}
			rex = ".*ANDROID[\\s]*(\\d*[\\._\\d]*)";
			Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(userAgent);
			boolean rs = m.find();
			if (rs) {
				String version = m.group(1).replace("_", ".");
				osVer = version;
			}
			os = OSTYPE_ANDROID;

			return judgeBrowser(ua, os, osVer, devType, netType);

		} else if (userAgent.contains("MAC OS X")) {
			os = OSTYPE_IOS;
			if (userAgent.contains("IPHONE")) {
				devType = DEVICE_TYPE_PHONE;
				String temp = userAgent.substring(userAgent.indexOf("OS ") + 3);
				if (temp != null && temp.contains(" ")) {
					osVer = temp.substring(0, temp.indexOf(" "));
				} else {
					osVer = temp;
				}
			} else if (userAgent.contains("IPAD")) {
				devType = DEVICE_TYPE_PAD;
			} else if (userAgent.contains("IPOD")) {
				devType = DEVICE_TYPE_POD;
			} else if (userAgent.contains("MACINTOSH")) {
				os = OSTYPE_MAC;
				devType = DEVICE_TYPE_PC;
				osVer = userAgent.substring(userAgent.indexOf("MAC OS X") + 9, userAgent.indexOf(")"));
			}
			return judgeBrowser(ua, os, osVer, devType, netType);
		} else if (userAgent.contains("WINDOWS PHONE")) {
			rex = ".*WINDOWS PHONE[\\s]*[OS\\s]*([\\d][\\.\\d]*)";
			Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(userAgent);
			boolean rs = m.find();
			if (rs) {
				osVer = m.group(1);
			}
			return judgeBrowser(ua, OSTYPE_WP, osVer, DEVICE_TYPE_PHONE, netType);
		} else if (userAgent.contains("WINDOWS")) {// 主流应用靠前
			osVer = "10";
			if (userAgent.contains("WINDOWS NT 10.0")) {
				osVer = "10";
			} else if (userAgent.contains("WINDOWS NT 6.2")) {// WINDOWS 8
				osVer = "8";
			} else if (userAgent.contains("WINDOWS NT 6.1")) {// WINDOWS 7
				osVer = "7";
			} else if (userAgent.contains("WINDOWS NT 6.0")) {// WINDOWS Vista
				osVer = "Vista";
			} else if (userAgent.contains("WINDOWS NT 5.2")) {
				osVer = "XP";
			} else if (userAgent.contains("WINDOWS NT 5.1")) {// WINDOWS XP
				osVer = "XP";
			} else if (userAgent.contains("WINDOWS NT 5.01")) {
				osVer = "2000";
			} else if (userAgent.contains("WINDOWS NT 5.0")) {// WINDOWS 2000
				osVer = "2000";
			} else if (userAgent.contains("WINDOWS NT 4.0")) {
				osVer = "NT 4.0";
			} else if (userAgent.contains("WINDOWS 98; Win 9x 4.90")) {
				osVer = "ME";
			} else if (userAgent.contains("WINDOWS 98")) {// WINDOWS 98
				osVer = "98";
			} else if (userAgent.contains("WINDOWS 95")) {// WINDOWS 95
				osVer = "95";
			} else if (userAgent.contains("WINDOWS CE")) {// WINDOWS CE
				osVer = "CE";
			}

			return judgeBrowser(ua, OSTYPE_WIN, osVer, DEVICE_TYPE_PC, netType);
		}
		return null;
	}

	/**
	 * 用途：根据客户端 User Agent Strings 判断其浏览器 if 判断的先后次序：
	 * 根据浏览器的用户使用量降序排列，这样对于大多数用户来说可以少判断几次即可拿到结果： >>Browser:Chrome > FF > IE > ...
	 * 
	 * @param userAgent
	 *            :user agent
	 * @param os
	 *            :平台
	 * @param platformSeries
	 *            :系列
	 * @param osVer
	 *            :版本
	 * @return
	 */
	private static UserAgent judgeBrowser(String userAgent, String os, String osVer, String devType, String netType) {
		if (userAgent.contains("Chrome")) {

			String temp = userAgent.substring(userAgent.indexOf("Chrome/") + 7);
			String chromeVersion = null;
			if (temp.indexOf(" ") < 0) {// temp形如"24.0.1295.0"
				chromeVersion = temp;
			} else {// temp形如"24.0.1295.0 Safari/537.15"
				chromeVersion = temp.substring(0, temp.indexOf(" "));
			}
			return new UserAgent("Chrome", chromeVersion, os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("Safari")) {
			String temp = userAgent.substring(userAgent.indexOf("Safari/") + 7);
			String chromeVersion = null;
			if (temp.indexOf(" ") < 0) {// temp形如"24.0.1295.0"
				chromeVersion = temp;
			} else {// temp形如"24.0.1295.0 Safari/537.15"
				chromeVersion = temp.substring(0, temp.indexOf(" "));
			}
			return new UserAgent("Safari", chromeVersion, os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("Firefox")) {
			String temp = userAgent.substring(userAgent.indexOf("Firefox/") + 8);
			String ffVersion = null;
			if (temp.indexOf(" ") < 0) {// temp形如"16.0.1"
				ffVersion = temp;
			} else {// temp形如"16.0.1 Gecko/20121011"
				ffVersion = temp.substring(0, temp.indexOf(" "));
			}
			return new UserAgent("Firefox", ffVersion, os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("Edge")) {// edge
			String temp = userAgent.substring(userAgent.indexOf("Edge/") + 5);
			String bver = null;
			if (temp.indexOf(" ") < 0) {// temp形如"16.0.1"
				bver = temp;
			} else {// temp形如"16.0.1 Gecko/20121011"
				bver = temp.substring(0, temp.indexOf(" "));
			}
			return new UserAgent("edge", bver, os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("MSIE 10.0")) {// ie 10
			return new UserAgent("ie", "10", os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("MSIE 9.0")) {// ie 9
			return new UserAgent("ie", "9", os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("MSIE 8.0")) {// ie 8
			return new UserAgent("ie", "8", os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("MSIE 7.0")) {// ie 7
			return new UserAgent("ie", "7", os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("MSIE 6.0")) {// ie 6
			return new UserAgent("ie", "6", os, osVer, userAgent, devType, netType);
		} else if (userAgent.contains("Trident")) {
			String v = null;
			if (userAgent.indexOf("rv:") > 0) {
				v = userAgent.substring(userAgent.indexOf("rv:") + 3, userAgent.indexOf("rv:") + 7);
			}
			return new UserAgent("ie", v, os, osVer, userAgent, devType, netType);

		} else {// 暂时支持以上三个主流.其它浏览器,待续...
			return new UserAgent(null, null, os, osVer, userAgent, devType, netType);
		}
	}


	public static boolean isMatch(String source, String regx, int flags) {
		Pattern p = Pattern.compile(regx, flags);
		Matcher m = p.matcher(source);
		boolean result = m.find();
		return result;
	}
	
	public static boolean watchIsMobile(HttpServletRequest request) { 
		boolean isMobile =false;
		String[] mobileAgents = {"iphone","android","ipad","phone","mobile","wap","netfront","java","operamobi",
				"operamini","ucweb","windowsce","symbian","series","webos","sony","blackberry","dopod",
				"nokia","samsung","palmsource","xda","pieplus","meizu","midp","cldc","motorola","foma",
				"docomo","up.browser","up.link","blazer","helio","hosin","huawei","novarra","coolpad","webos",
				"techfaith","palmsource","alcatel","amoi","ktouch","nexian","ericsson","philips","sagem",
				"wellcom","bunjalloo","maui","smartphone","iemobile","spice","bird","zte-","longcos",
				"pantech","gionee","portalmmm","jigbrowser","hiptop","benq","haier","^lct","320x320",
				"240x320","176x220","w3c","acs-","alav","alca","amoi","audi","avan","benq","bird","blac",
				"blaz","brew","cell","cldc","cmd-","dang","doco","eric","hipt","inno","ipaq","java","jigs",
				"kddi","keji","leno","lg-c","lg-d","lg-g","lge-","maui","maxo","midp","mits","mmef","mobi",
				"mot-","moto","mwbp","nec-","newt","noki","oper","palm","pana","pant","phil","play","port",
				"prox","qwap","sage","sams","sany","sch-","sec-","send","seri","sgh-","shar","sie-","siem",
				"smal","smar","sony","sph-","symb","t-mo","teli","tim-","tosh","tsm-","upg1","upsi","vk-v",
				"voda","wap-","wapa","wapi","wapp","wapr","webc","winw","xda","xda-","Googlebot-Mobile"};
		String agent = request.getHeader("User-Agent");
		if(agent!=null) {
			for(String one:mobileAgents) {
				if (agent.toLowerCase().indexOf(one)>0&&agent.toLowerCase().indexOf("windows nt")<=0&&agent.toLowerCase().indexOf("macintosh")<=0) {
					isMobile=true;
					break;
				}
			}
		}
		 return isMobile;
	}

	public static void main(String[] args) {
		System.out.println(
				UserAgentUtil.getUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"));

		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36"));
		System.out.println(
				UserAgentUtil.getUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; Lenovo S810t Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"));
		System.out.println(
				UserAgentUtil.getUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11) AppleWebKit/601.1.56 (KHTML, like Gecko) Version/9.0 Safari/601.1.56"));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 "));
		System.out.println(UserAgentUtil.getUserAgent(
				"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53"));
	}
}
