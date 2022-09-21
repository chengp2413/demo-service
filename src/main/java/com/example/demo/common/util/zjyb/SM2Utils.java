package com.example.demo.common.util.zjyb;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xucy
 * @Date 2019-04-09 16:03
 * @Description
 **/

public class SM2Utils {
    /**
     *  公钥
     *
     * @Author xucy
     * @Date 16:09 2019-04-09
     * @param
     * @return
     */

    public static String PUBLIC_KEY = "PUBLIC_KEY";

    /**
     * 私钥
     *
     * @Author xucy
     * @Date 16:09 2019-04-09
     * @param
     * @return
     */
    public static String PRIVATE_KEY = "PRIVATE_KEY";

    /**
     * 生成随机秘钥对
     *
     * @Author xucy
     * @Date 16:10 2019-04-09
     * @param
     * @return
     */
    public static Map<String,String> generateKeyPair() {
        SM2 sm2 = SM2.instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();

        System.out.println("公钥: " + Util.byteToHex(publicKey.getEncoded()));
        System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));

        Map map = new HashMap(2);
        map.put(PUBLIC_KEY, Util.byteToHex(publicKey.getEncoded()));
        map.put(PRIVATE_KEY, Util.byteToHex(privateKey.toByteArray()));
        return map;
    }

    /**
     * 生成随机秘钥对
     *
     * @Author xucy
     * @Date 16:10 2019-04-09
     * @param
     * @return
     */
    public static String encrypt(String publicKey, String source) throws IOException {
        return encrypt(Util.hexToByte(publicKey), source.getBytes());
    }

    /**
     * 简单数据解密
     *
     * @Author xucy
     * @Date 16:10 2019-04-09
     * @param
     * @return
     */
    public static String decrypt1(String privateKey, String source) throws IOException {
        return new String(decrypt(Util.hexToByte(privateKey), Util.hexToByte(privateKey)),"UTF-8");
    }
    /**
     * 数据加密
     *
     * @Author xucy
     * @Date 16:10 2019-04-09
     * @param
     * @return
     */
    public static String encrypt(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return null;
        }

        if (data == null || data.length == 0) {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.initEnc(sm2, userKey);
        cipher.encrypt(source);
        byte[] c3 = new byte[32];
        cipher.dofinal(c3);

        return Util.byteToHex(c1.getEncoded()) + Util.byteToHex(source) + Util.byteToHex(c3);

    }

    /**
     * 数据解密
     *
     * @Author xucy
     * @Date 16:10 2019-04-09
     * @param
     * @return
     */
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0) {
            return null;
        }
        // 加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = Util.byteToHex(encryptedData);
        /***
         * 分解加密字串 （C1 = C1标志位2位 + C1实体部分128位 = 130） （C3 = C3实体部分64位 = 64） （C2 =
         * encryptedData.length * 2 - C1长度 - C2长度）
         */
        byte[] c1Bytes = Util.hexToByte(data.substring(0, 130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = Util.hexToByte(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

        SM2 sm2 = SM2.instance();
        BigInteger userD = new BigInteger(1, privateKey);

        // 通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.initDec(userD, c1);
        cipher.decrypt(c2);
        cipher.dofinal(c3);

        // 返回解密结果Returns the y-coordinate.
        //     *
        //     * Caution: depending on the curve's coordinate system, this may not be the same value as in an
        //     * affine coordinate system; use normalize() to get a point where the coordinates have their
        //     * affine values, or use getAffineYCoord() if you expect the point to already have been
        //     * normalized.
        //     *
        //     * @return the y-coordinate of this point
        return c2;
    }

    public static void main(String[] args) throws  Exception{
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><MsgText><GrpHdr><Version>1.0.0</Version><Ref>3092019092030900000000000000012</Ref><BusCd>0114001</BusCd><TradSrc>S</TradSrc><Sender><InstId>309</InstId></Sender><Recver><InstId>309</InstId></Recver><Resend>N</Resend><Date>20190920</Date><Time>202113</Time><Dgst>系统连通性测试</Dgst><Rst><Code>0001</Code><Info>待处理</Info></Rst></GrpHdr><BusiText><SiSeq>309</SiSeq></BusiText></MsgText>";
        System.out.println(Util.hexToByte(PUBLIC_KEY).length);
        System.out.println(Arrays.toString(xml.getBytes()));
        xml = SM2Utils.encrypt(Util.hexToByte(PUBLIC_KEY), xml.getBytes());
        System.out.println(Arrays.toString(xml.getBytes()));
        xml = new String(SM2Utils.decrypt(Util.hexToByte(PRIVATE_KEY), Util.hexToByte(xml)),"UTF-8");

        System.out.println(xml);
        System.out.println(Arrays.toString(xml.getBytes()));
        generateKeyPair();
    }
}
