package com.example.demo.common.util.sm4;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * SM4工具
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/21 16:58
 */
@Slf4j
public class SM4Util {
    public SM4Util() {
    }


    /**
     * ECB 加密
     *
     * @param key         密钥
     * @param str         原数据
     * @param charsetName 编码方式
     * @param hexStrFlag  密钥是否16进制数字
     * @return 加密字符串(base64编码后)
     */
    public static String encryptData_ECB(String key, String str, String charsetName, boolean hexStrFlag) {
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.setKey(key);
        sm4Utils.setHexString(hexStrFlag);
        String resultStr = sm4Utils.encryptData_ECB(str, charsetName);
        return resultStr;
    }

    /**
     * CBC 加密
     *
     * @param key         密钥
     * @param iv          子密钥（轮密钥）
     * @param str         原数据
     * @param charsetName 编码方式
     * @param hexStrFlag  密钥是否16进制数字
     * @return 加密字符串(base64编码后)
     */
    public static String encryptData_CBC(String key, String iv, String str, String charsetName, boolean hexStrFlag) {
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.setKey(key);
        sm4Utils.setIv(iv);
        sm4Utils.setHexString(hexStrFlag);
        String resultStr = sm4Utils.encryptData_CBC(str, charsetName);
        return resultStr;
    }

    /**
     * ECB 解密
     *
     * @param key         密钥
     * @param str         待解密数据
     * @param charsetName 编码方式
     * @param hexStrFlag  密钥是否16进制数字
     * @return 原数据
     */
    public static String decryptData_ECB(String key, String str, String charsetName, boolean hexStrFlag) {
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.setKey(key);
        sm4Utils.setHexString(hexStrFlag);
        String resultStr = sm4Utils.decryptData_ECB(str, charsetName);
        return resultStr;
    }

    /**
     * CBC 解密
     *
     * @param key         密钥
     * @param iv          子密钥（轮密钥）
     * @param str         待解密数据
     * @param charsetName 编码方式
     * @param hexStrFlag  密钥是否16进制数字
     * @return 原数据
     */
    public static String decryptData_CBC(String key, String iv, String str, String charsetName, boolean hexStrFlag) {
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.setKey(key);
        sm4Utils.setIv(iv);
        sm4Utils.setHexString(hexStrFlag);
        String resultStr = sm4Utils.decryptData_CBC(str, charsetName);
        return resultStr;
    }

    /**
     * 生成随机密钥
     *
     * @param len 密钥长度
     * @return SM4密钥
     */
    public static String randomHexString(int len) {
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < len; i++) {
                result.append(Integer.toHexString(new SecureRandom().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            log.error("随机SM4密钥生成失败", e);
            return null;
        }
    }
}
