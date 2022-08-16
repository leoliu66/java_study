package com.example.demo.rsa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @ClassName RSA256Utils
 * @Description rsa工具类
 * @Author liulu_leo
 * @Date 2021/6/21
 * @Version 1.0
 */
@Slf4j
public class RSA256Utils {

    private static final String CHARSET="utf-8";
    private static final String ALGORITHMS="RSA";

    public static void main(String[] args) {
        /*String privateKey = "";
        JSONObject body = JSONObject.parseObject("{\"tranDateTime\":\"2021-06-20 06:55:03\",\"transactionAmount\":82233.115,\"channel\":\"EasyBuy\",\"currency\":\"NGN\",\"bvn\":\"81722617183\",\"tranRef\":\"EBZ311822702811942912\",\"settledAmount\":82233.115}");
        String sign = "O5pqGO6tg1PLH+SLqnbHCIebqVcj0AqbncU87jJqyyhme2GwVkT0XV0De1o955O5XJG9D/XEsQJV9xEj3DktgnwCoILDSt2GX+2QVREPNtH3PiOOz7UmSvZGaeE8PnHuaVYlrHlLDqk5s+b0ljEfL7cAuovj/Ou/tIeGkIiF/1EzQDuEG9WjcUKwrA2vlVBRvl9UoW4QVIivI7Pkay/W7Fc8ISTRKr3AvQrh+hHW7tD8ScJIesVl7o+RqSmDNt6nMZPnpK41pvt16cnzI2gWKhLLshZC6d5RUKkKKIAwDTbJ7ol8YPdzP/Oq+Xe+i+KJjZpyAHQyLtGnQmO6qUGSpA==";
        Map<String, String> header = new HashMap<>();
        header.put("signature",sign);
        Map<String, Object> param = new HashMap<>();
        param.put("tranRef",body.getString("tranRef"));
        param.put("accountNumber",body.getString("accountNumber"));
        param.put("bvn",body.getString("bvn"));
        param.put("channel",body.getString("channel"));
        param.put("transactionAmount",body.getBigDecimal("transactionAmount"));
        param.put("settledAmount",body.getBigDecimal("settledAmount"));
        param.put("feeAmount",body.getBigDecimal("feeAmount"));
        param.put("currency",body.getString("currency"));
        param.put("tranDateTime",body.getString("tranDateTime"));
        String about = getSortParams(param);
        log.info(about);

        String signHead = header.get("signature");
        String decode = RSA256Utils.decrypt(signHead,privateKey);
        log.info(decode);
        if(about.equals(decode)){
            log.info("true");
        } else {
            log.info("false");
        }*/
    }

    //私钥解密
    public static String decrypt(String encryptText,String privateKeyStr){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            PrivateKey privateKey=getPrivateKey(privateKeyStr);
            if(privateKey!=null){
                cipher.init(Cipher.DECRYPT_MODE,privateKey);
                byte[] bytes=cipher.doFinal(Base64.getDecoder().decode(encryptText));
                return new String(bytes);
            }
        } catch (Exception e) {
            log.warn("私钥解密失败：{}", e);
        }
        return null;
    }

    public static String getSortParams(Map<String, Object> params) {
        Map<String, Object> map = new TreeMap<>(Comparator.naturalOrder());
        for (String key : params.keySet()) {
            if (StringUtils.isEmpty(key)
                    || "sign".equals(key)
                    || "returnUrl".equals(key)
                    || "desKey".equals(key)) {
                continue;
            }
            map.put(key, params.get(key));
        }
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        String str = "";
        while (iter.hasNext()) {
            String key = iter.next();
            Object value = map.get(key);
            str += key + "=" + value + "&";
        }
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static PublicKey getPublicKey(String publicKey){
        try {
            X509EncodedKeySpec spec=new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory keyFactory=KeyFactory.getInstance(ALGORITHMS);
            return keyFactory.generatePublic(spec);

        } catch (Exception e) {
            log.warn("getPublicKey失败：{}", e);
        }

        return null;
    }

    public static PrivateKey getPrivateKey(String privateKey){
        try {
            PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyFactory= KeyFactory.getInstance(ALGORITHMS);
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            log.warn("getPrivateKey失败：{}", e);
        }
        return null;
    }

    //公钥加密
    public static String encrypt(String plainText,String publicKeyStr){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            PublicKey publickKey=getPublicKey(publicKeyStr);
            if(publickKey!=null){
                cipher.init(Cipher.ENCRYPT_MODE,publickKey);
                byte[] bytes=cipher.doFinal(plainText.getBytes(CHARSET));
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (Exception e) {
            log.warn("公钥加密失败：{}", e);
        }
        return null;
    }

}
