package com.example.demo.common.util.digest;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.digests.SM3Digest;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 数据摘要
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/20 16:33
 */
@Slf4j
public class DataDigestUtil {
    public DataDigestUtil() {
    }


    /**
     * 计算数据摘要
     *
     * @param input           原始字符串
     * @param digestAlgorithm 摘要算法
     * @param charsetName     编码方式
     * @param upperCase       是否转大写
     * @return 摘要字符串
     */
    public static synchronized String digestStr(String input, DigestAlgorithm digestAlgorithm, String charsetName, boolean upperCase) {
        try {
            if (DigestAlgorithm.SM3.getAlgorithmName().equals(digestAlgorithm.getAlgorithmName())) {
                SM3Digest sm3Digest = new SM3Digest();
                byte[] inputByteArray = input.getBytes(charsetName);
                sm3Digest.update(inputByteArray, 0, inputByteArray.length);
                byte[] resultByteArray = new byte[sm3Digest.getDigestSize()];
                sm3Digest.doFinal(resultByteArray, 0);
                return getHexString(resultByteArray, upperCase);
            } else {
                MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm.getAlgorithmName());
                byte[] inputByteArray = input.getBytes(charsetName);
                messageDigest.update(inputByteArray);
                byte[] resultByteArray = messageDigest.digest();
                return getHexString(resultByteArray, upperCase);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException: {}", charsetName, e);
            return null;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException: {}", digestAlgorithm.getAlgorithmName(), e);
            return null;
        }
    }

    /**
     * 计算文件摘要
     *
     * @param file            原文件
     * @param digestAlgorithm 摘要算法
     * @param upperCase       是否转大写
     * @return 摘要字符串
     */
    public static synchronized String digestStr(File file, DigestAlgorithm digestAlgorithm, boolean upperCase) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            if (DigestAlgorithm.SM3.getAlgorithmName().equals(digestAlgorithm.getAlgorithmName())) {
                SM3Digest sm3Digest = new SM3Digest();
                byte[] inputByteArray = new byte[2048];
                int var1;
                while ((var1 = fileInputStream.read(inputByteArray)) != -1) {
                    sm3Digest.update(inputByteArray, 0, var1);
                }
                byte[] resultByteArray = new byte[sm3Digest.getDigestSize()];
                sm3Digest.doFinal(resultByteArray, 0);
                return getHexString(resultByteArray, upperCase);
            } else {
                MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm.getAlgorithmName());
                byte[] inputByteArray = new byte[2048];
                int var1;
                while ((var1 = fileInputStream.read(inputByteArray)) != -1) {
                    messageDigest.update(inputByteArray, 0, var1);
                }
                byte[] resultByteArray = messageDigest.digest();
                return getHexString(resultByteArray, upperCase);
            }
        } catch (FileNotFoundException e) {
            log.error("file【{}】 FileNotFoundException：{}", file.getAbsolutePath(), e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("file【{}】 IOException：{}", file.getAbsolutePath(), e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException: {}", digestAlgorithm.getAlgorithmName(), e);
            return null;
        }

    }

    /**
     * 字节数组转换16进制数字
     *
     * @param byteArray 字节数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 字节数组转换16进制数字
     *
     * @param bytes     字节数组
     * @param upperCase 是否转换大写
     * @return 16进制数字
     */
    public static String getHexString(byte[] bytes, boolean upperCase) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            ret += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return upperCase ? ret.toUpperCase() : ret;
    }

}
