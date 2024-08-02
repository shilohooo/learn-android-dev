package org.shiloh.android.util;

/**
 * 字符串操作工具类
 *
 * @author shiloh
 * @date 2024/8/2 22:58
 */
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * 判断是否为空字符串
     *
     * @param str 字符串
     * @return 如果是则返回true，否则返回false
     * @author shiloh
     * @date 2024/8/2 22:58
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断是否不为空字符串
     *
     * @param str 字符串
     * @return 如果是则返回true，否则返回false
     * @author shiloh
     * @date 2024/8/2 22:59
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
