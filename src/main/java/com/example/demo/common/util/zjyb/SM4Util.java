package com.example.demo.common.util.zjyb;
import java.security.SecureRandom;

/**
 * SM4加密工具
 * @author tongxj
 * @version 1.0
 * @date 2019/7/4 11:10
 */
public class SM4Util {


    /**
     * ecb 加密
     * @param   ：
     * @return  ：
     * @Author: chenjh
     */
    public static String encryptData_ECB(String key,String planText){
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(key);
        sm4.setHexString(false);
        String cipherText = sm4.encryptData_ECB(planText);
        return cipherText;
    }
   /**
    * cbc 加密
    * @param   ：
    * @return  ：
    * @Author: chenjh
    */
    public static String encryptData_CBC(String key,String iv,String planText){
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(key);
        sm4.setIv(iv);
        sm4.setHexString(false);
        String cipherText = sm4.encryptData_CBC(planText);
        return cipherText;
    }
    /**
     * ecb 解密
     * @param   ：
     * @return  ：
     * @Author: chenjh
     */

    public static  String decryptData_ECB(String key,String planText){
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(key);
        sm4.setHexString(false);
        String cipherText = sm4.decryptData_ECB(planText);
        return cipherText;
    }

    /**
     * cbc 解密
     * @param   ：
     * @return  ：
     * @Author: chenjh
     */
    public static String decryptData_CBC(String key,String iv,String planText){
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey(key);
        sm4.setIv(iv);
        sm4.setHexString(false);
        String cipherText = sm4.decryptData_CBC(planText);
        return cipherText;
    }

    /**
     * @Author huangwei
     * @Description //TODO 随机生成16进制码
     * @Date 17:32 2019/7/30
     * @Param len（生成多少位的）
     * @return int
     **/
    public static String randomHexString(int len)  {
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<len;i++) {
                result.append(Integer.toHexString(new SecureRandom().nextInt(16)));
            }
            return result.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

}
