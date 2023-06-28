package cn.kolmap.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算签名的工具类
 */
public class HMacMD5Util {
    /**
     * 计算签名
     *
     * @param key  签名秘钥，SigSecret
     * @param data 要进行签名的字符串
     * @return 根据data计算的签名字符串
     */
    public static String getHmacMd5Str(String key, String data) {
        String result = "";
        try {


            byte[] keyByte = key.getBytes("UTF-8");
            byte[] dataByte = data.getBytes("UTF-8");
            byte[] hmacMd5Byte = getHmacMd5Bytes(keyByte, dataByte);
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < hmacMd5Byte.length; i++) {
                if (Integer.toHexString(0xFF & hmacMd5Byte[i]).length() == 1) md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & hmacMd5Byte[i]));
                else
                    md5StrBuff.append(Integer
                            .toHexString(0xFF & hmacMd5Byte[i]));
            }
            result = md5StrBuff.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static byte[] getHmacMd5Bytes(byte[] key, byte[] data)
            throws NoSuchAlgorithmException {
        /*
         * HmacMd5 calculation formula: H (K XOR opad, H (K XOR ipad, text))* HmacMd5 计算公式：H (K XOR opad, H (K XOR ipad, text))
         * H代表hash算法，本类中使用MD5算法，K代表密钥，text代表要加密的数据* ipad为 0x36，opad为0x5C。*/
        int length = 64;
        byte[] ipad = new byte[length];
        byte[] opad = new byte[length];
        for (int i = 0; i < 64; i++) {
            ipad[i] = 0x36;
            opad[i] = 0x5C;
        }


        byte[] actualKey = key;
        byte[] keyArr = new byte[length];
        if (key.length > length) {
            actualKey = md5(key);
        }
        for (int i = 0; i < actualKey.length; i++) {
            keyArr[i] = actualKey[i];
        }

        if (actualKey.length < length) {
            for (int i = actualKey.length; i < keyArr.length; i++)
                keyArr[i] = 0x00;
        }
        byte[] kIpadXorResult = new byte[length];
        for (int i = 0; i < length; i++) {
            kIpadXorResult[i] = (byte) (keyArr[i] ^ ipad[i]);
        }
        byte[] firstAppendResult = new byte[kIpadXorResult.length + data.length];
        for (int i = 0; i < kIpadXorResult.length; i++) {
            firstAppendResult[i] = kIpadXorResult[i];
        }
        for (int i = 0; i < data.length; i++) {
            firstAppendResult[i + keyArr.length] = data[i];
        }
        byte[] firstHashResult = md5(firstAppendResult);


        byte[] kOpadXorResult = new byte[length];
        for (int i = 0; i < length; i++) {
            kOpadXorResult[i] = (byte) (keyArr[i] ^ opad[i]);
        }
        byte[] secondAppendResult = new byte[kOpadXorResult.length
                + firstHashResult.length];
        for (int i = 0; i < kOpadXorResult.length; i++) {
            secondAppendResult[i] = kOpadXorResult[i];
        }
        for (int i = 0; i < firstHashResult.length; i++) {
            secondAppendResult[i + keyArr.length] = firstHashResult[i];
        }
        byte[] hmacMd5Bytes = md5(secondAppendResult);
        return hmacMd5Bytes;
    }

    private static byte[] md5(byte[] str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str);
        return md.digest();
    }


    public static void main(String[] args) {
        String hmacMd5Str = getHmacMd5Str("62c03d227f4af930", "123");
        System.out.println(hmacMd5Str);
    }
}


