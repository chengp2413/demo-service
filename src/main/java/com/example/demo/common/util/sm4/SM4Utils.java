package com.example.demo.common.util.sm4;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SM4加解密实现
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/21 16:07
 */
@Data
@Slf4j
public class SM4Utils {
    public SM4Utils() {
    }

    public static final Pattern P_MATCH = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * SM4密钥
     */
    private String key = "";
    /**
     * SM4子密钥（轮密钥）
     */
    private String iv = "";
    /**
     * 是否16进制字符串
     */
    private boolean hexString = false;

    /**
     * ECB加密
     *
     * @param input       原数据
     * @param charsetName 编码方式
     * @return 加密后字符串(默认base64编码后输出)
     */
    public String encryptData_ECB(String input, String charsetName) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.mode = 1;
            ctx.isPadding = true;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = this.hexStringToBytes(key);
            } else {
                keyBytes = key.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, input.getBytes(charsetName));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = P_MATCH.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            log.error("encryptData_ECB error!", e);
            return null;
        }
    }

    /**
     * ECB解密
     *
     * @param input       待解密数据
     * @param charsetName 编码方式
     * @return 原数据
     */
    public String decryptData_ECB(String input, String charsetName) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.mode = 0;
            ctx.isPadding = true;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = this.hexStringToBytes(key);
            } else {
                keyBytes = key.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(input));
            return new String(decrypted, charsetName);
        } catch (Exception e) {
            log.error("decryptData_ECB error!", e);
            return null;
        }
    }

    /**
     * CBC加密
     *
     * @param input       原数据
     * @param charsetName 编码方式
     * @return 加密后字符串(默认base64编码后输出)
     */
    public String encryptData_CBC(String input, String charsetName) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.mode = 1;
            ctx.isPadding = true;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = this.hexStringToBytes(key);
                ivBytes = this.hexStringToBytes(iv);
            } else {
                keyBytes = key.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, input.getBytes(charsetName));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = P_MATCH.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            log.error("encryptData_CBC error!", e);
            return null;
        }
    }

    /**
     * CBC解密
     *
     * @param input       待解密数据
     * @param charsetName 编码方式
     * @return 原数据
     */
    public String decryptData_CBC(String input, String charsetName) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.mode = 0;
            ctx.isPadding = true;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = this.hexStringToBytes(key);
                ivBytes = this.hexStringToBytes(iv);
            } else {
                keyBytes = key.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(input));
            return new String(decrypted, charsetName);
        } catch (Exception e) {
            log.error("decryptData_CBC error!", e);
            return null;
        }
    }

    /**
     * 16进制数组转字节数组
     *
     * @param hexString 16进制数字
     * @return 字节数组
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (this.charToByte(hexChars[pos]) << 4 | this.charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * char 转 byte
     *
     * @param c char字符
     * @return 字节
     */
    public byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 字节数组转换16进制数字
     *
     * @param bytes     字节数组
     * @param upperCase 是否转换大写
     * @return 16进制数字
     */
    public String getHexString(byte[] bytes, boolean upperCase) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            ret += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return upperCase ? ret.toUpperCase() : ret;
    }

    /**
     * 整形转换网络传输的字节数组
     *
     * @param num 整形
     * @return 字节数组
     */
    public byte[] intToBytes(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & (num >> 0));
        bytes[1] = (byte) (0xff & (num >> 8));
        bytes[2] = (byte) (0xff & (num >> 16));
        bytes[3] = (byte) (0xff & (num >> 24));
        return bytes;
    }

    /**
     * 字节数组（四个字节）转换整形数据
     *
     * @param bytes 字节数组
     * @return 整形
     */
    public int bytesToInt(byte[] bytes) {
        int num = 0;
        int temp;
        temp = (0x000000ff & (bytes[0])) << 0;
        num = num | temp;
        temp = (0x000000ff & (bytes[1])) << 8;
        num = num | temp;
        temp = (0x000000ff & (bytes[2])) << 16;
        num = num | temp;
        temp = (0x000000ff & (bytes[3])) << 24;
        num = num | temp;
        return num;
    }

}
