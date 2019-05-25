package com.zb.common.http;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class HttpsClientUtil {
	static final Logger log = LoggerFactory.getLogger(HttpsClientUtil.class);
	public static final Charset UTF8 = Charset.forName("UTF-8");
	public static final Charset GB18030 = Charset.forName("GB18030");
	static final int TIME_OUT = Integer.getInteger("http.timeout", 60000).intValue();
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.4 (KHTML, like Gecko) Safari/537.4";

	// static CloseableHttpClient HTTP_CLIENT = createSSLClientDefault();

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).setCookieSpec("ignoreCookies").build();
			return HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslsf).build();

		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String get(String url, Map<String, String> HEADERS) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		CloseableHttpResponse resp = httpClient.execute(get);
		return handle(resp);
	}

	public static InputStream download(String url, Map<String, String> HEADERS) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		CloseableHttpResponse resp = httpClient.execute(get);
		int status = resp.getStatusLine().getStatusCode();
		if (status == 200) {
			return resp.getEntity().getContent();
		}
		return null;
	}

	public static HttpEntity downloadEntity(String url, Map<String, String> HEADERS) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		CloseableHttpResponse resp = httpClient.execute(get);
		int status = resp.getStatusLine().getStatusCode();
		if (status == 200) {
			return resp.getEntity();
		}
		return null;
	}
	public static String post(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpPost post = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.addHeader(key, headers.get(key));
			}
		}
		if ((params != null) && (!params.isEmpty())) {
			List<NameValuePair> ps = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> kv : params.entrySet()) {
				ps.add(new BasicNameValuePair((String) kv.getKey(), (String) kv.getValue()));
			}
			post.setEntity(new UrlEncodedFormEntity(ps));
		}
		CloseableHttpResponse resp = httpClient.execute(post);
		return handle(resp);
	}

	public static String postData(String url, String json, Map<String, String> headers) throws IOException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpPost post = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.addHeader(key, headers.get(key));
			}
		}
		if (StringUtils.isNotBlank(json)) {
			StringEntity myEntity = new StringEntity(json, "utf8");
			post.setEntity(myEntity);
		}
		CloseableHttpResponse resp = httpClient.execute(post);
		return handle(resp);
	}

	// public static String postXml(String url, String xml) throws IOException {
	// CloseableHttpClient httpClient = HttpsClientUtil
	// .createSSLClientDefault();
	// HttpPost httppost = new HttpPost(url);
	// httppost.addHeader("Content-Type", "text/xml");
	// if (StringUtils.isNotBlank(xml)) {
	// StringEntity myEntity = new StringEntity(xml, "utf8");
	// httppost.setEntity(myEntity);
	// }
	// CloseableHttpResponse resp = httpClient.execute(httppost);
	// return handle(resp);
	// }

	public static String delData(String url, String json, Map<String, String> headers) throws IOException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpDelete post = new HttpDelete(url);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.addHeader(key, headers.get(key));
			}
		}
		CloseableHttpResponse resp = httpClient.execute(post);
		return handle(resp);
	}

	public static String putData(String url, String json, Map<String, String> headers) throws IOException {
		CloseableHttpClient httpClient = HttpsClientUtil.createSSLClientDefault();
		HttpPut post = new HttpPut(url);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.addHeader(key, headers.get(key));
			}
		}
		if (StringUtils.isNotBlank(json)) {
			StringEntity myEntity = new StringEntity(json, "utf8");
			post.setEntity(myEntity);
		}
		CloseableHttpResponse resp = httpClient.execute(post);
		return handle(resp);
	}

	public static String handle(HttpResponse resp) throws IOException {
		HttpEntity entity = resp.getEntity();
		if (entity == null) {
			return null;
		}
		byte[] content = EntityUtils.toByteArray(entity);
		Charset charset = null;
		ContentType contentType = ContentType.get(entity);
		if (contentType != null) {
			charset = contentType.getCharset();
		}
		if (charset == null) {
			charset = HttpsClientUtil.UTF8;
		}
		String html = new String(content, charset);
		charset = HttpsClientUtil.checkMetaCharset(html, charset);
		if (charset != null) {
			html = new String(content, charset);
		}
		return html;
	}

	private static Charset checkMetaCharset(String html, Charset use) {
		String magic = "charset=";
		int index = html.indexOf(magic);
		if ((index > 0) && (index < 1000)) {
			index += magic.length();
			int end = html.indexOf('"', index);
			if (end > index) {
				try {
					String charSetString = html.substring(index, end).toLowerCase();
					if (charSetString.length() > 10) {
						return null;
					}
					if (charSetString.startsWith("gb")) {
						return GB18030.equals(use) ? null : GB18030;
					}
					Charset curr = Charset.forName(charSetString);
					if (!curr.equals(use)) {
						return curr;
					}
				} catch (Exception e) {
					log.error("Get MetaCharset error", e);
				}
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		HttpEntity is = HttpsClientUtil.downloadEntity("http://q.qlogo.cn/qqapp/1105127046/4D189C31586C1C508BE2E454EA5FCBF4/640", null);
		if(is!=null){
			System.out.println(is.getContentType().getValue());
			System.out.println("OK");
		}
	}
}