package org.lecoder.easyflow.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DbColumnNameGenerator {
    // 数据库字段名允许的字符正则（字母、数字、下划线）
    private static final Pattern VALID_COLUMN_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    // 时间戳格式（精确到毫秒，避免特殊字符）
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS", Locale.US
    );
    // 哈希算法（使用SHA-1生成短哈希，减少长度）
    private static final String HASH_ALGORITHM = "SHA-1";
    // 十六进制字符表（用于手动转换）
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    /**
     * 生成唯一数据库字段名：前缀_时间戳_哈希值（兼容JDK11）
     *
     * @param prefix 业务前缀（如 "user"）
     * @param source 原始信息（如用户ID、业务关键字，用于生成哈希）
     * @return 符合规则的唯一字段名
     */
    public static String generateColumnName(String prefix, String source) {
        // 1. 校验前缀合法性
        if (!isValidColumnPart(prefix)) {
            throw new IllegalArgumentException("前缀包含非法字符，仅允许字母、数字、下划线");
        }

        // 2. 生成时间戳（精确到毫秒，确保时序唯一）
        String timestamp = TIMESTAMP_FORMAT.format(new Date());

        // 3. 生成哈希值（对原始信息哈希，取前8位避免过长）
        String hash = generateShortHash(source, 8);

        // 4. 拼接字段名（格式：前缀_时间戳_哈希）
        String columnName = String.format("%s_%s_%s", prefix, timestamp, hash);

        // 5. 校验最终字段名长度（不超过64字符）
        if (columnName.length() > 64) {
            // 截断前缀以满足长度限制（保留至少3位前缀）
            int prefixMaxLength = 64 - (timestamp.length() + hash.length() + 2);
            prefixMaxLength = Math.max(prefixMaxLength, 3);
            columnName = String.format(
                    "%s_%s_%s",
                    prefix.substring(0, prefixMaxLength),
                    timestamp,
                    hash
            );
        }

        return columnName;
    }

    /**
     * 生成短哈希值（截取前n位），兼容JDK11（手动实现字节转十六进制）
     */
    private static String generateShortHash(String source, int length) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            // 手动将字节数组转换为十六进制字符串
            String fullHash = bytesToHex(hashBytes);
            // 截取前length位
            return fullHash.substring(0, Math.min(length, fullHash.length()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希算法不存在", e);
        }
    }

    /**
     * 手动实现字节数组转十六进制字符串（兼容JDK11）
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            // 取字节的高4位和低4位，映射到十六进制字符
            int value = bytes[i] & 0xFF; // 转为无符号整数
            hexChars[i * 2] = HEX_CHARS[value >>> 4]; // 高4位
            hexChars[i * 2 + 1] = HEX_CHARS[value & 0x0F]; // 低4位
        }
        return new String(hexChars);
    }

    /**
     * 校验字段名部分（前缀、哈希等）是否合法
     */
    private static boolean isValidColumnPart(String part) {
        return part != null && !part.isEmpty() && VALID_COLUMN_PATTERN.matcher(part).matches();
    }

}
