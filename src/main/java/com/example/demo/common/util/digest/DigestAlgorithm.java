package com.example.demo.common.util.digest;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 摘要算法枚举
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/21 13:06
 */
@Getter
@AllArgsConstructor
public enum DigestAlgorithm {
    SM3("SM3"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private final String algorithmName;
}
