package org.shiloh.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 *
 * @author shiloh
 * @date 2024/6/30 13:36
 */
public final class DateUtils {
    private DateUtils() {
    }

    /**
     * 获取当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间字符串
     * @author shiloh
     * @date 2024/6/30 12:33
     */
    public static String getNowStr() {
        final SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.CHINA
        );
        return formatter.format(new Date());
    }
}
