package cn.kolmap.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用于加解密的工具类
 */
public class QEncodeUtil {
    /**
     * 加密
     *
     * @param sSrc         明 文
     * @param sKey         数据秘钥，DataSecret
     * @param dataSecretIV 初始向量，DataSecretIV
     * @return 加密后的密文
     */
    public static String encrypt(String sSrc, String sKey, String dataSecretIV) {
        try {
            if (sKey == null) {
                return null;
            }
//判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//"算法/ 模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//使用CBC模式，需要一个向量iv，可增加加密算 法的强度
            IvParameterSpec iv = new IvParameterSpec(dataSecretIV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
//此处使用BASE64做转码功能， 同时能起到2次加密的作用。
            String result = new BASE64Encoder().encode(encrypted);
            result = result.replaceAll("\r", "");
            result = result.replaceAll("\n", "");
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param sSrc         密 文
     * @param sKey         数据秘钥，DataSecret
     * @param dataSecretIV 初始向量，DataSecretIV
     * @return 明 文
     */
    public static String decrypt(String sSrc, String sKey, String dataSecretIV) {
        try {
// 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
// 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key⻓度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(dataSecretIV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "UTF-8");
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        /**
         #组织唯一Id
         orgId:FUHE2023
         #密钥
         orgSecret:62c03d227f4af930
         #消息密钥
         dataSecret:763b74f42ca165d5
         #初始化向量
         dataSecretIv:e9c66b9eeedacb40
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orgId", "HUAYUN1036");
        jsonObject.put("orgSecret", "62c03d227f4af930");
        System.out.println(JSONObject.toJSONString(jsonObject));
        String e9c66b9eeedacb40 = encrypt(JSONObject.toJSONString(jsonObject), "763b74f42ca165d5", "e9c66b9eeedacb40");

        System.out.println(e9c66b9eeedacb40);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data", e9c66b9eeedacb40);

        String body = HttpRequest.post("http://121.37.152.25:8085/load/control/queryToken").body(JSONObject.toJSONString(jsonObject1)).execute().body();
        System.out.println(body);
        String responseData = (String) JSONObject.parseObject(body).get("data");
        String e9c66b9eeedacb401 = decrypt(responseData, "763b74f42ca165d5", "e9c66b9eeedacb40");
        System.out.println(e9c66b9eeedacb401);


        JSONObject jsonObject3 = new JSONObject();

//        jsonObject3.put("lastQueryTime","2023-06-10 00:00:00");
        jsonObject3.put("pageNo", 1);
        jsonObject3.put("pageSize", 1);


        String jsonString = JSONObject.toJSONString(jsonObject3);
        System.out.println(jsonString);
        String d = encrypt(jsonString, "763b74f42ca165d5", "e9c66b9eeedacb40");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("data", d);
        jsonObject2.put("orgId", "HUAYUN1036");
        System.out.println(JSONObject.toJSONString(jsonObject2));
        String accessToken1 = JSONObject.parseObject(e9c66b9eeedacb401).getString("accessToken");
        System.out.println(accessToken1);
        String accessToken = HttpRequest.post("http://121.37.152.25:8095/load/control/queryStationsInfo")
                .body(JSONObject.toJSONString(jsonObject2))
                .header("Authorization", "Bearer ".concat(accessToken1))
                .execute()
                .body();
        System.out.println(accessToken);
        String acb401 = decrypt(JSONObject.parseObject(accessToken).getString("data"), "763b74f42ca165d5", "e9c66b9eeedacb40");
        System.out.println(acb401);


    }
}