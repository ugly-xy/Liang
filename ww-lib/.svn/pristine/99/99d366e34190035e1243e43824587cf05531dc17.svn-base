package com.qq.open;


// urlencode
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

// hmacsha1 
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

// base64
//import biz.source_code.base64Coder.Base64Coder;




//import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 *
 * @version 3.0.1
 * @since jdk1.5
 *
 * @copyright  2012, Tencent Corporation. All rights reserved.
 * @History:
 *               3.0.1 | 2012-08-28 17:34:12 | support cpay callback sig verifictaion.
 *               3.0.0 | nemozhang | 2012-03-21 12:01:05 | initialization
 *
 */
 
public class SnsSigCheck 
{
	
	private static Logger logger = LoggerFactory.getLogger(SnsSigCheck.class);
    /** 
     * 
     *
     * @param input 
     * @return 
     * @throws OpensnsException 
     */
    public static String encodeUrl(String input) throws OpensnsException
    {
        try
        {
            return URLEncoder.encode(input, CONTENT_CHARSET).replace("+", "%20").replace("*", "%2A");
        }
        catch(UnsupportedEncodingException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
    }

    /* 鐢熸垚绛惧悕
     *
     * @param method HTTP璇锋眰鏂规硶 "get" / "post"
     * @param url_path CGI鍚嶅瓧, eg: /v3/user/get_info
     * @param params URL璇锋眰鍙傛暟
     * @param secret 瀵嗛挜
     * @return 绛惧悕鍊�
     * @throws OpensnsException 涓嶆敮鎸佹寚瀹氱紪鐮佷互鍙婁笉鏀寔鎸囧畾鐨勫姞瀵嗘柟娉曟椂鎶涘嚭寮傚父銆�
     */
    public static String makeSig(String method, String url_path, HashMap<String, String> params, String secret) throws OpensnsException
    {
        String sig = null;
        try
        {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);

            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());

            mac.init(secretKey);

            String mk = makeSource(method, url_path, params);
            System.out.println(mk);
            
            byte[] hash = mac.doFinal(mk.getBytes(CONTENT_CHARSET));
    
            // base64
            sig = encodeUrl(new String(Base64Coder.encode(hash)));
            
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        catch(UnsupportedEncodingException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        catch(InvalidKeyException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        return sig;
    }
    public static String makeSig1(String method, String url_path, HashMap<String, String> params, String secret) throws OpensnsException
    {
        String sig = null;
        try
        {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);

            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());

            mac.init(secretKey);

            String mk = makeSource1(method, url_path, params);
            System.out.println(mk);
            
            byte[] hash = mac.doFinal(mk.getBytes(CONTENT_CHARSET));
    
            // base64
            sig = encodeUrl(new String(Base64Coder.encode(hash)));
            
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        catch(UnsupportedEncodingException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        catch(InvalidKeyException e)
        {
            throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
        }
        return sig;
    }

    /* 鐢熸垚绛惧悕鎵�闇�婧愪覆
     *
     * @param method HTTP璇锋眰鏂规硶 "get" / "post"
     * @param url_path CGI鍚嶅瓧, eg: /v3/user/get_info
     * @param params URL璇锋眰鍙傛暟
     * @return 绛惧悕鎵�闇�婧愪覆
     */
    public static String makeSource(String method, String url_path, HashMap<String, String> params) throws OpensnsException
    {
        Object[] keys = params.keySet().toArray();

        Arrays.sort(keys);  

        StringBuilder buffer = new StringBuilder(128);

        buffer.append(method.toUpperCase()).append("&").append(encodeUrl(url_path)).append("&");

        StringBuilder buffer2= new StringBuilder();

        for(int i=0; i<keys.length; i++)
        {  
            buffer2.append(keys[i]).append("=").append(params.get(keys[i]));

            if (i!=keys.length-1)
            {
                buffer2.append("&");
            }
        }   

        buffer.append(encodeUrl(buffer2.toString()));

        return buffer.toString();
    }
    public static String makeSource1(String method, String url_path, HashMap<String, String> params) throws OpensnsException
    {
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);  

        StringBuilder buffer = new StringBuilder(128);

        buffer.append(method.toUpperCase()).append("&").append(encodeUrl(url_path)).append("&");

        StringBuilder buffer2= new StringBuilder();

        for(int i=0; i<keys.length; i++)
        {  
            buffer2.append(keys[i]).append("=").append(encodeValue(params.get(keys[i])));

            if (i!=keys.length-1)
            {
                buffer2.append("&");
            }
        }   

        buffer.append(encodeUrl(buffer2.toString()));

        return buffer.toString();
    }
    public static boolean verifySig(String method, String url_path, HashMap<String, String> params, String secret, String sig) throws OpensnsException
    {
        // 纭繚涓嶅惈sig
        params.remove("sig");

        // 鎸夌収鍙戣揣鍥炶皟鎺ュ彛鐨勭紪鐮佽鍒欏value缂栫爜
        //codePayValue(params);

        // 璁＄畻绛惧悕
        String sig_new = makeSig1(method, url_path, params, secret);
        logger.info("鏈湴绛惧悕锛�"+sig_new);
        logger.info("鑵捐绛惧悕锛�"+encodeValue(sig));
        // 瀵规瘮鍜岃吘璁繑鍥炵殑绛惧悕
        return sig_new.equals(encodeValue(sig));
    }

    /**
     * 搴旂敤鍙戣揣URL鎺ュ彛瀵硅吘璁洖璋冧紶鏉ョ殑鍙傛暟value鍊煎厛杩涜涓�娆＄紪鐮佹柟娉曪紝鐢ㄤ簬楠岀 
     * (缂栫爜瑙勫垯涓猴細闄や簡 0~9 a~z A~Z !*() 涔嬪鍏朵粬瀛楃鎸夊叾ASCII鐮佺殑鍗佸叚杩涘埗鍔�%杩涜琛ㄧず锛屼緥濡傗��-鈥濈紪鐮佷负鈥�%2D鈥�)
     * 鍙傝�� <鍥炶皟鍙戣揣URL鐨勫崗璁鏄巁V3>
     * 
     * @param params
     *            鑵捐鍥炶皟浼犲弬Map (key,value);
     */
    public static void codePayValue(Map<String, String> params) 
    {
        Set<String> keySet = params.keySet();
        Iterator<String> itr = keySet.iterator();

        while (itr.hasNext()) 
        {
            String key = (String) itr.next();
            String value = (String) params.get(key);
            value = encodeValue(value);
            params.put(key, value);
        }
    }

    /**
     * 搴旂敤鍙戣揣URL鎺ュ彛鐨勭紪鐮佽鍒�
     * @param s
     * @return
     */
    public static String encodeValue(String s) 
    {
        String rexp = "[0-9a-zA-Z!*\\(\\)]";
        StringBuffer sb = new StringBuffer(s);
        StringBuffer sbRtn = new StringBuffer();
        Pattern p = Pattern.compile(rexp);
        char temp;
        String tempStr;

        for (int i = 0; i < sb.length(); i++) 
        {
            temp = sb.charAt(i);
            tempStr = String.valueOf(temp);
            Matcher m = p.matcher(tempStr);

            boolean result = m.find();
            if (!result) {
                tempStr = hexString(tempStr);
            }
            sbRtn.append(tempStr);
        }

        return sbRtn.toString();
    }

    /**
     * 搴旂敤鍙戣揣URL銆�鍗佸叚杩涘埗缂栫爜銆�
     * @param s
     * @return
     */
    private static String hexString(String s) 
    {
        byte[]b = s.getBytes();
        String retStr = "";
        for (int i = 0; i < b.length; i++) 
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            retStr = "%"+hex.toUpperCase();
        }
        return retStr;
    }
    
    // 缂栫爜鏂瑰紡
    private static final String CONTENT_CHARSET = "UTF-8";
   
    // HMAC绠楁硶
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    
    
    public static void main(String[] args) {
    	String url = "/midashiandroidpay/weibo_pay_callbacak";
		String str = "amt=28800&appid=1104941854&appmeta=20151116%2D70001%2D72951*qdqb*qq&billno=%2DAPPDJSX35990%2D20151116%2D1625143847&channel_id=10000144%2Dandroid%2D2002%2D%2Dqq&clientver=android&ebazinga=1&openid=CA74DF086F6BFBD188CE31DF0A7FBF3F&payamt_coins=0&payitem=id106*2880*1&providetype=5&pubacct_payamt_coins=&token=FD584201553D07AD35565BBF17B53ECE25718&ts=1447662314&version=v3&zoneid=1";
		//String str = "payitem=id106*2880*1&openid=CA74DF086F6BFBD188CE31DF0A7FBF3F&amt=28800&providetype=5&version=v3&token=FD584201553D07AD35565BBF17B53ECE25718&sig=zmxhoCaj8ocQfqPD2C51Kol+bCU=&appid=1104941854&payamt_coins=0&zoneid=1&clientver=android&billno=-APPDJSX35990-20151116-1625143847&channel_id=10000144-android-2002--qq&ebazinga=1&appmeta=20151116-70001-72951*qdqb*qq&ts=1447662314";
		//String str = "payitem=id106*2880*1&openid=CA74DF086F6BFBD188CE31DF0A7FBF3F&amt=28800&providetype=5&pubacct_payamt_coins=&version=v3&token=FD584201553D07AD35565BBF17B53ECE25718&sig=zmxhoCaj8ocQfqPD2C51Kol+bCU=&appid=1104941854&payamt_coins=0&zoneid=1&clientver=android&billno=-APPDJSX35990-20151116-1625143847&channel_id=10000144-android-2002--qq&appmeta=20151116-70001-72951*qdqb*qq&ts=1447662314";
		String sig = "zmxhoCaj8ocQfqPD2C51Kol+bCU=";
		String type = "GET";
		String ser = "00hhGc27OUO7fWv7&";
		HashMap<String,String> map = new HashMap<String, String>();
		String[] p = str.split("&");
		for(String s : p){
			System.out.println(s);
			String[] c = s.split("=");
			if(c.length == 2){
				map.put(c[0], c[1]);
			}else{
				map.put(c[0], "");
			}
			
		}
		System.out.println(map.toString());
		try {
			if( verifySig(type,url,map,ser,sig)){
				System.out.println(123);
			}else{
				System.out.println("no");
			}
		} catch (OpensnsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//    	String s = "openid=CA74DF086F6BFBD188CE31DF0A7FBF3F&openkey=73A2C3D44BAB11164771FA1A152017F8&pf=desktop_m_qq-10000144-android-2002-&appid=1101192253&format=json&pay_token=73A2C3D44BAB11164771FA1A152017F8&ts=1445287688212&paytiem=productId*30*1&goodsmeta=閲戝竵*娓告垙閲戝竵&goodsurl=123123&zoneid=1&app_metadata=20151109-70001-72755&amt=50";
//    	System.out.println(encodeValue(s));
    }
}
