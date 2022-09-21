package com.example.demo.common.util.zjyb;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * sm2
 *
 * @Author xucy
 * @Date 10:30 2019-04-10
 * @param
 * @return
 */
public class SM2Result
{
	public SM2Result() {
	}

	/** 签名/验签*/
	public BigInteger r;
	public BigInteger s;
	public BigInteger r1;

	/** 密钥交换*/
	public byte[] sa;
	public byte[] sb;
	public byte[] s1;
	public byte[] s2;

	public ECPoint keyra;
	public ECPoint keyrb;
}
