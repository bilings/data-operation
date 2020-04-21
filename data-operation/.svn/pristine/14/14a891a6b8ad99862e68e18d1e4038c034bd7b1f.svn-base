package com.hifo.dataoperation.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密
 *
 * @author whc
 * @date 2019/3/18 14:17
 */
public class Md5Util {

    /**
     * 获客宝加密密钥
     */
    private final static String KEY = "20140217";

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        //加密后的字符串
        return DigestUtils.md5Hex(text + KEY);
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5  密文
     * @return true/false
     */
    public static boolean verify(String text, String md5) {
        //根据传入的密钥进行验证
        return md5(text).equalsIgnoreCase(md5);
    }
}
