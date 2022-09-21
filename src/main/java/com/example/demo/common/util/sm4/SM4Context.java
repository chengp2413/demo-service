package com.example.demo.common.util.sm4;

/**
 * SM4参数
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/21 15:49
 */
public class SM4Context {
    /**
     * 模式 0-解密 1-加密
     */
    public int mode;
    /**
     * 子密钥（轮密钥）
     */
    public long[] sk;
    /**
     * 是否填充 false-否 true-是
     */
    public boolean isPadding;

    public SM4Context() {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
