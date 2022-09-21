package com.example.demo.common.util.zjyb;

/**
 * sm4
 *
 * @Author xucy
 * @Date 10:31 2019-04-10
 * @param
 * @return
 */
public class SM4_Context
{
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4_Context()
    {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
