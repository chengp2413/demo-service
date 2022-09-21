package com.example.demo.common.util.zjyb;

import java.util.Map;
import java.util.Set;

/**
 * @Author xucy
 * @Date 2019-03-08 10:38
 * @Description sm2加解密
 **/

public class SM2Util {

    /**
     * sm2算法加密
     *
     * @param publieKey
     * @param xml
     * @return
     * @throws Exception
     */
    public static String encrypt(String publieKey, String xml) throws Exception {
        return com.example.demo.common.util.zjyb.SM2Utils.encrypt(com.example.demo.common.util.zjyb.Util.hexToByte(publieKey), xml.getBytes("UTF-8"));
    }

    /**
     * sm2算法解密
     *
     * @param privateKey
     * @param xml
     * @return
     * @throws Exception
     */
    public static String decrypt(String privateKey, String xml) throws Exception {
        return new String(com.example.demo.common.util.zjyb.SM2Utils.decrypt(com.example.demo.common.util.zjyb.Util.hexToByte(privateKey), com.example.demo.common.util.zjyb.Util.hexToByte(xml)), "UTF-8");
    }

    /**
     * 生成秘钥对
     */
    public static void key() {
       /**         **/

        Map generateKeyPair = com.example.demo.common.util.zjyb.SM2Utils.generateKeyPair();
        Set<Map.Entry<String, String>> entrySet = generateKeyPair.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.err.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        key();
    }
}
