package com.example.demo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName Test
 * @Description
 * @Author liulu_leo
 * @Date 2021/3/25
 * @Version 1.0
 */
public class Test<T> {

    private static volatile Long l = 1L;

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static void main(String args[]) throws Exception {

        Map map = new HashMap();
        map.put("commodityOrderId", "31");
        map.put("commodityModelId", "221");
        map.put("commodityModelName", "S4\uff086GB + 64 GB\uff09");
        map.put("commodityPrice", "100.0000");
        map.put("custName", "test");
        map.put("phone", "12343342223");
        map.put("email", "aicoofeng5@163.com");
        map.put("address", "Lagos Mainland fgsdgfv");
        map.put("merchantId", "1");
        map.put("merchantName", "tyler");
        map.put("channel", "X-Park");

        String data = http_build_query(map);
        System.out.println(data);
        String data1 = getSignature(data, "xpark");
        System.out.println(data1);

    }


    public static String http_build_query(Map<String ,String> array){
        String reString = new String();
        //遍历数组形成akey=avalue&bkey=bvalue&ckey=cvalue形式的的字符串 升序排列
        List<Map.Entry<String, String>> list = new ArrayList<>(array.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<String, String> entry: list){
            String key = entry.getKey();
            String value = entry.getValue();
            reString += key+"="+value+"&";
        }
        reString = reString.substring(0, reString.length()-1);
        System.out.println(reString);
        //将得到的字符串进行处理得到目标格式的字符串
        try {
            reString = java.net.URLEncoder.encode(reString,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(reString);
        reString = reString.replace("%3D", "=").replace("%26", "&");

        return reString;
    }

    public static String getSignature(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA256);

        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        String hexBytes = byte2hex(rawHmac);
        return hexBytes;

    }
    private static String byte2hex(final byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
        // 以十六进制(基数 16)无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));

            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;

            } else {
                hs = hs + stmp;

            }
        }
        return hs;

    }
}
