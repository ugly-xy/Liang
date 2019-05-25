package com.zb.service.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppleVerifyUtil {
	
	static final Logger log = LoggerFactory.getLogger(AppleVerifyUtil.class);

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";
	private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";

	/**
	 * 苹果服务器验证
	 * 
	 * @param receipt
	 *            账单
	 * @url 要验证的地址
	 * @return null 或返回结果 沙盒 https://sandbox.itunes.apple.com/verifyReceipt
	 * 
	 */

	public static String buyAppVerify(long uid,String receipt, boolean isTest) {
		String url = url_verify;
		if (isTest)
			url = url_sandbox;
		String buyCode = receipt;// getBASE64(receipt);
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "text/json");
			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
			// //多次出现链接超时情况
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream out = null;

			// 多次出现链接超时情况
			try {
				out = conn.getOutputStream();
			} catch (ConnectException e) {
				log.error("AOET："+uid+" receipt:"
						+ receipt);
				return "timeout";
			}
			BufferedOutputStream hurlBufOus = new BufferedOutputStream(out);

			String str = String.format(Locale.CHINA, "{\"receipt-data\":\""
					+ buyCode + "\"}");
			hurlBufOus.write(str.getBytes());
			hurlBufOus.flush();

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception ex) {
			log.error("AOET："+uid+" receipt:"
					+ receipt);
			return "err";
		}
	}
	

	/**
	 * md5加密方法
	 * 
	 * @author: duanruigang Jul 30, 2010 4:38:28 PM
	 * @param plainText
	 *            加密字符串
	 * @return String 返回32位md5加密字符串(16位加密取substring(8,24)) each engineer has a
	 *         duty to keep the code elegant
	 */
	public final static String md5(String plainText) {
		// 返回字符串
		String md5Str = null;
		try {
			// 操作字符串
			StringBuffer buf = new StringBuffer();
			/**
			 * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
			 * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
			 * 
			 * MessageDigest 对象开始被初始化。 该对象通过使用 update()方法处理数据。 任何时候都可以调用
			 * reset()方法重置摘要。 一旦所有需要更新的数据都已经被更新了，应该调用digest()方法之一完成哈希计算。
			 * 
			 * 对于给定数量的更新数据，digest 方法只能被调用一次。 在调用 digest 之后，MessageDigest
			 * 对象被重新设置成其初始状态。
			 */
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
			md.update(plainText.getBytes());
			// 计算出摘要,完成哈希计算。
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
				buf.append(Integer.toHexString(i));
			}
			// 32位的加密
			md5Str = buf.toString();
			// 16位的加密
			// md5Str = buf.toString().md5Strstring(8,24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	public static void main(String[] args) {
		//String str = "MIIT3AYJKoZIhvcNAQcCoIITzTCCE8kCAQExCzAJBgUrDgMCGgUAMIIDjQYJKoZIhvcNAQcBoIIDfgSCA3oxggN2MAoCARQCAQEEAgwAMAsCAQ4CAQEEAwIBUjALAgEZAgEBBAMCAQMwDQIBCgIBAQQFFgMxNyswDQIBCwIBAQQFAgMbz5wwDQIBDQIBAQQFAgMBOOYwDgIBAQIBAQQGAgQ019nlMA4CAQkCAQEEBgIEUDIzNDAOAgEQAgEBBAYCBDBrd4owDwIBAwIBAQQHDAUxLjIuMjAPAgETAgEBBAcMBTEuMi4yMBACAQ8CAQEECAIGZPYpsFytMBQCAQACAQEEDAwKUHJvZHVjdGlvbjAYAgEEAgECBBCsEnxVGVbLUYxr32hyLxm3MBsCAQICAQEEEwwRY29tLnJlZG5vdm8ud2VpYm8wHAIBBQIBAQQUh1Pp6IU6yah27txEczzrEB4RB9AwHgIBCAIBAQQWFhQyMDE1LTA3LTAyVDE0OjI1OjE2WjAeAgEMAgEBBBYWFDIwMTUtMDctMDJUMTQ6MjU6MTZaMB4CARICAQEEFhYUMjAxNS0wNy0wMlQxNDoxOToxOFowRAIBBwIBAQQ8TjYFmEdQby0IeaPDtb2YJCI3NLiODjtusXBA5axgGiRVRqESvlVDPbyFY2RBTKjzgjpz8Puol3ksg7WGMFkCAQYCAQEEUeMm37/ohXxMs3q8Kk9ajI9H+XwGcKmLUMp7O5CLMfidrLFBOPe6iKDuyWCy1ModpbgJ6k6WjLPEGsbvj4kXH9MnCYpAGZ3pIk5+jExsNATQpzCCAU8CARECAQEEggFFMYIBQTALAgIGrAIBAQQCFgAwCwICBq0CAQEEAgwAMAsCAgawAgEBBAIWADALAgIGsgIBAQQCDAAwCwICBrMCAQEEAgwAMAsCAga0AgEBBAIMADALAgIGtQIBAQQCDAAwCwICBrYCAQEEAgwAMAwCAgalAgEBBAMCAQEwDAICBqsCAQEEAwIBATAMAgIGrwIBAQQDAgEAMAwCAgaxAgEBBAMCAQAwDwICBq4CAQEEBgIEOMzlljAUAgIGpgIBAQQLDAl3ZWliby4wMDQwGgICBqcCAQEEEQwPNzEwMDAwMTA1MTQ4NTU4MBoCAgapAgEBBBEMDzcxMDAwMDEwNTE0ODU1ODAfAgIGqAIBAQQWFhQyMDE1LTA3LTAyVDE0OjI1OjE2WjAfAgIGqgIBAQQWFhQyMDE1LTA3LTAyVDE0OjI1OjE2WqCCDlUwggVrMIIEU6ADAgECAggYWUMhcnSc/DANBgkqhkiG9w0BAQUFADCBljELMAkGA1UEBhMCVVMxEzARBgNVBAoMCkFwcGxlIEluYy4xLDAqBgNVBAsMI0FwcGxlIFdvcmxkd2lkZSBEZXZlbG9wZXIgUmVsYXRpb25zMUQwQgYDVQQDDDtBcHBsZSBXb3JsZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9ucyBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTAeFw0xMDExMTEyMTU4MDFaFw0xNTExMTEyMTU4MDFaMHgxJjAkBgNVBAMMHU1hYyBBcHAgU3RvcmUgUmVjZWlwdCBTaWduaW5nMSwwKgYDVQQLDCNBcHBsZSBXb3JsZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9uczETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC2k8K3DyRe7dI0SOiFBeMzlGZb6Cc3v3tDSev5yReXM3MySUrIb2gpFLiUpvRlSztH19EsZku4mNm89RJRy+YvqfSznxzoKPxSwIGiy1ZigFqika5OQMN9KC7X0+1N2a2K+/JnSOzreb0CbQRZGP+MN5+KN/Fi/7uiA1CHCtWS4IYRXiNG9eElYyuiaoyyELeRI02aP4NA8mQJWveNrlZc1PW0bgMbBF0sG68AmRfXpftJkc7ioRExXhkBwNrOUINeyOtJO0kaKurgn7/SRkmc2Kuhg2FsD8H8s62ZdSr8I5vvIgjre1kUEZ9zNC3muTmmO/fmPuzKpvurrybfj4iBAgMBAAGjggHYMIIB1DAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYMBaAFIgnFwmpthhgi+zruvZHWcVSVKO3ME0GA1UdHwRGMEQwQqBAoD6GPGh0dHA6Ly9kZXZlbG9wZXIuYXBwbGUuY29tL2NlcnRpZmljYXRpb25hdXRob3JpdHkvd3dkcmNhLmNybDAOBgNVHQ8BAf8EBAMCB4AwHQYDVR0OBBYEFHV2JKJrYgyXNKH6Tl4IDCK/c+++MIIBEQYDVR0gBIIBCDCCAQQwggEABgoqhkiG92NkBQYBMIHxMIHDBggrBgEFBQcCAjCBtgyBs1JlbGlhbmNlIG9uIHRoaXMgY2VydGlmaWNhdGUgYnkgYW55IHBhcnR5IGFzc3VtZXMgYWNjZXB0YW5jZSBvZiB0aGUgdGhlbiBhcHBsaWNhYmxlIHN0YW5kYXJkIHRlcm1zIGFuZCBjb25kaXRpb25zIG9mIHVzZSwgY2VydGlmaWNhdGUgcG9saWN5IGFuZCBjZXJ0aWZpY2F0aW9uIHByYWN0aWNlIHN0YXRlbWVudHMuMCkGCCsGAQUFBwIBFh1odHRwOi8vd3d3LmFwcGxlLmNvbS9hcHBsZWNhLzAQBgoqhkiG92NkBgsBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEAoDvxh7xptLeDfBn0n8QCZN8CyY4xc8scPtwmB4v9nvPtvkPWjWEt5PDcFnMB1jSjaRl3FL+5WMdSyYYAf2xsgJepmYXoePOaEqd+ODhk8wTLX/L2QfsHJcsCIXHzRD/Q4nth90Ljq793bN0sUJyAhMWlb1hZekYxQWi7EzVFQqSM+hHVSxbyMjXeH7zSmV3I5gIyWZDojcs53yHaw3b7ejYaFhqYTIUb5itFLS9ZGi3GmtZmkqPSNlJQgCBNM8iymtZTYrFgUvD1930QUOQSv71xvrSAx23Eb1s5NdHnt96BICeOOFyChzpzYMTW8RygqWZEfs4MKJsjf6zs5qA73TCCBCMwggMLoAMCAQICARkwDQYJKoZIhvcNAQEFBQAwYjELMAkGA1UEBhMCVVMxEzARBgNVBAoTCkFwcGxlIEluYy4xJjAkBgNVBAsTHUFwcGxlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MRYwFAYDVQQDEw1BcHBsZSBSb290IENBMB4XDTA4MDIxNDE4NTYzNVoXDTE2MDIxNDE4NTYzNVowgZYxCzAJBgNVBAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSwwKgYDVQQLDCNBcHBsZSBXb3JsZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9uczFEMEIGA1UEAww7QXBwbGUgV29ybGR3aWRlIERldmVsb3BlciBSZWxhdGlvbnMgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDKOFSmy1aqyCQ5SOmM7uxfuH8mkbw0U3rOfGOAYXdkXqUHI7Y5/lAtFVZYcC1+xG7BSoU+L/DehBqhV8mvexj/avoVEkkVCBmsqtsqMu2WY2hSFT2Miuy/axiV4AOsAX2XBWfODoWVN2rtCbauZ81RZJ/GXNG8V25nNYB2NqSHgW44j9grFU57Jdhav06DwY3Sk9UacbVgnJ0zTlX5ElgMhrgWDcHld0WNUEi6Ky3klIXh6MSdxmilsKP8Z35wugJZS3dCkTm59c3hTO/AO0iMpuUhXf1qarunFjVg0uat80YpyejDi+l5wGphZxWy8P3laLxiX27Pmd3vG2P+kmWrAgMBAAGjga4wgaswDgYDVR0PAQH/BAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wHQYDVR0OBBYEFIgnFwmpthhgi+zruvZHWcVSVKO3MB8GA1UdIwQYMBaAFCvQaUeUdgn+9GuNLkCm90dNfwheMDYGA1UdHwQvMC0wK6ApoCeGJWh0dHA6Ly93d3cuYXBwbGUuY29tL2FwcGxlY2Evcm9vdC5jcmwwEAYKKoZIhvdjZAYCAQQCBQAwDQYJKoZIhvcNAQEFBQADggEBANoyAJbFVJTTO4I3Zn0uaNXDxrjLJoxIkM8TJGpGjmPU8NATBt3YxME3FfIzEzkmLc4uVUDjCwOv+hLC5w0huNWAz6woL84ts06vhhkExulQ3UwpRxAj/Gy7G5hrSInhW53eRts1hTXvPtDiWEs49O11Wh9ccB1WORLl4Q0R5IklBr3VtBWOXtBZl5DpS4Hi3xivRHQeGaA6R8yRHTrrI1r+pS2X93u71odGQoXrUj0msmOotLHKj/TM4rPIR+C/mlmD+tqYUyqC9XxlLpXZM1317WXMMTfFWgToa+HniANKdZ6bKMtKQIhlQ3XdyzolI8WeV/guztKpkl5zLi8ldRUwggS7MIIDo6ADAgECAgECMA0GCSqGSIb3DQEBBQUAMGIxCzAJBgNVBAYTAlVTMRMwEQYDVQQKEwpBcHBsZSBJbmMuMSYwJAYDVQQLEx1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEWMBQGA1UEAxMNQXBwbGUgUm9vdCBDQTAeFw0wNjA0MjUyMTQwMzZaFw0zNTAyMDkyMTQwMzZaMGIxCzAJBgNVBAYTAlVTMRMwEQYDVQQKEwpBcHBsZSBJbmMuMSYwJAYDVQQLEx1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEWMBQGA1UEAxMNQXBwbGUgUm9vdCBDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAOSRqQkfkdseR1DrBe1eeYQt6zaiV0xV7IsZid75S2z1B6siMALoGD74UAnTf0GomPnRymacJGsR0KO75Bsqwx+VnnoMpEeLW9QWNzPLxA9NzhRp0ckZcvVdDtV/X5vyJQO6VY9NXQ3xZDUjFUsVWR2zlPf2nJ7PULrBWFBnjwi0IPfLrCwgb3C2PwEwjLdDzw+dPfMrSSgayP7OtbkO2V4c1ss9tTqt9A8OAJILsSEWLnTVPA3bYharo3GSR1NVwa8vQbP4++NwzeajTEV+H0xrUJZBicR0YgsQg0GHM4qBsTBY7FoEMoxos48d3mVz/2deZbxJ2HafMxRloXeUyS0CAwEAAaOCAXowggF2MA4GA1UdDwEB/wQEAwIBBjAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQWBBQr0GlHlHYJ/vRrjS5ApvdHTX8IXjAfBgNVHSMEGDAWgBQr0GlHlHYJ/vRrjS5ApvdHTX8IXjCCAREGA1UdIASCAQgwggEEMIIBAAYJKoZIhvdjZAUBMIHyMCoGCCsGAQUFBwIBFh5odHRwczovL3d3dy5hcHBsZS5jb20vYXBwbGVjYS8wgcMGCCsGAQUFBwICMIG2GoGzUmVsaWFuY2Ugb24gdGhpcyBjZXJ0aWZpY2F0ZSBieSBhbnkgcGFydHkgYXNzdW1lcyBhY2NlcHRhbmNlIG9mIHRoZSB0aGVuIGFwcGxpY2FibGUgc3RhbmRhcmQgdGVybXMgYW5kIGNvbmRpdGlvbnMgb2YgdXNlLCBjZXJ0aWZpY2F0ZSBwb2xpY3kgYW5kIGNlcnRpZmljYXRpb24gcHJhY3RpY2Ugc3RhdGVtZW50cy4wDQYJKoZIhvcNAQEFBQADggEBAFw2mUwteLftjJvc83eb8nbSdzBPwR+Fg4UbmT1HN/Kpm0COLNSxkBLYvvRzm+7SZA/LeU802KI++Xj/a8gH7H05g4tTINM4xLG/mk8Ka/8r/FmnBQl8F0BWER5007eLIztHo9VvJOLr0bdw3w9F4SfK8W147ee1Fxeo3H4iNcol1dkP1mvUoiQjEfehrI9zgWDGG1sJL5Ky+ERI8GA4nhX1PSZnIIozavcNgs/e66Mv+VNqW2TAYzN39zoHLFbr2g8hDtq6cxlPtdk2f8GHVdmnmbkyQvvY1XGefqFStxu9k0IkEirHDx22TZxeY8hLgBdQqorV2uT80AkHN7B1dSExggHLMIIBxwIBATCBozCBljELMAkGA1UEBhMCVVMxEzARBgNVBAoMCkFwcGxlIEluYy4xLDAqBgNVBAsMI0FwcGxlIFdvcmxkd2lkZSBEZXZlbG9wZXIgUmVsYXRpb25zMUQwQgYDVQQDDDtBcHBsZSBXb3JsZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9ucyBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eQIIGFlDIXJ0nPwwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASCAQCWXmZ0vjo2jnsn6ifb1Xa8WzSmSIaHY1q6jWV47AKyHfxFQkc9YZDr2FR1ExE2nr7J31x/WHwTiLKhq0qNvzf3MhvkqHxRiSVj1rzQ1hG9Yu4wHnsUcajxpnkrflZ/RcX8kRmDEWcBXrkNDWdB9wIlYBBN1GodaJWWZP93b3og3Qt+c6R6s8vcpvfTmSYfrzrYY0FSWTqIzpTPl/fzSwWLOKq6LezlZn3Fx6up6KSh9K5Rl2wqTp/8ckPZi/rBsACUgfML6+yZLz6D/hH3/6JW9+Wz0JSG4CbvsCsvj4Hdig5p3j1ttsQUPm/UzKM56Co1/X+L0FlpkH8QXaU93sxg";
		String str = "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";
		System.out.println("res:" + buyAppVerify(1,str.trim(), false));

	}

}