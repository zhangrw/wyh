package com.banshion.portal.util;

/**
 * Created by zhang.rw on 16-4-12.
* <pre>
* 功能说明：密码工具类
* </pre>
*
*/
public class PasswordUtil {

    /***
     * 获取随即的八位字符的byte数组
     *
     * @return
     */
    public static byte[] getSaltBytes() {
        return Digests.generateSalt(8);
    }

    /***
     * 获取随即的指定位数位字符的byte数组
     *
     * @return
     */
    public static byte[] getSaltBytes(int num) {
        return Digests.generateSalt(num);
    }

    /***
     * 获取指定字符的字节
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        return str.getBytes();
    }

    /**
     * Hex加密算法
     *
     * @return
     */
    public static String encodeHex(byte[] bytes) {
        return Encodes.encodeHex(bytes);
    }

    /**
     * Hex解密算法
     */
    public static byte[] decodeHex(String input) {
        return Encodes.decodeHex(input);
    }

    /***
     * 获取“关键词”加密字符
     *
     * @param salts
     * @return
     */
    public static String getEncodeSalts(byte[] salts) {
        return encodeHex(salts);
    }

    /***
     * 本系统中密码生产器
     *
     * @param passWord
     *            <String>未加密
     * @param salts
     *            <byte[]>未加密
     * @return
     */
    public static String getEncodePassWord(String passWord, byte[] salts) {
        byte[] hashPassword = Digests.sha1(getBytes(passWord), salts, 1024);
        return encodeHex(hashPassword);
    }
}

