package com.example.demo.common.util.zjyb;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SM4Utils加解密
 * @author tongxj
 * @version 1.0
 * @date 2019/7/4 11:10
 */
public class SM4Utils {
    private String secretKey = "";
    private String iv = "";
    private boolean hexString = false;

    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public boolean isHexString() {
        return hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    public SM4Utils()
    {
    }

    public String encryptData_ECB(String plainText)
    {
        try
        {
            com.example.demo.common.util.zjyb.SM4_Context ctx = new com.example.demo.common.util.zjyb.SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("utf-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText)
    {
        try
        {
            com.example.demo.common.util.zjyb.SM4_Context ctx = new com.example.demo.common.util.zjyb.SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText)
    {
        try
        {
            com.example.demo.common.util.zjyb.SM4_Context ctx = new com.example.demo.common.util.zjyb.SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(secretKey);
                ivBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("utf-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText)
    {
        try
        {
            com.example.demo.common.util.zjyb.SM4_Context ctx = new com.example.demo.common.util.zjyb.SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;
            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(secretKey);
                ivBytes = com.example.demo.common.util.zjyb.Util.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws IOException
    {
        String plainText = "ererfeiisgod";

        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = "JeF8U9wHFOMfs2Y8";
        sm4.hexString = false;

        System.out.println("ECB模式");
        String cipherText = sm4.encryptData_ECB(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_ECB(cipherText);
        System.out.println("明文: " + plainText);
        System.out.println("");

        System.out.println("CBC模式");
        sm4.iv = "UISwD9fW6cFh9SNS";
        cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("明文: " + plainText);
    }
}
