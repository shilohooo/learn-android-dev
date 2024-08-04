package org.shiloh.android.constant;

import java.util.regex.Pattern;

/**
 * 常量
 *
 * @author shiloh
 * @date 2024/8/1 22:11
 */
public final class Constants {

    private Constants() {
    }

    /**
     * 加法符号
     */
    public static final String PLUS_SIGN = "+";

    /**
     * 减法符号
     */
    public static final String MINUS_SIGN = "-";

    /**
     * 乘法符号
     */
    public static final String MULTIPLICATION_SIGN = "*";

    /**
     * 除法符号
     */
    public static final String DIVISION_SIGN = "÷";

    /**
     * 取模符合
     */
    public static final String MODULO_SIGN = "%";

    /**
     * 等号
     */
    public static final String EQUAL_SIGN = "=";

    /**
     * 小数点
     */
    public static final String DOT = ".";

    /**
     * 正则：判断是否输入的是有效的数字，包含小数点
     */
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+([.]\\d+)?$");

    /**
     * 正则：判断是否输入的是有效的运算符
     */
    public static final Pattern OPERATOR_PATTERN = Pattern.compile("^[+\\-*÷%]+$");

    /**
     * 空字符串
     */
    public static final String EMPTY_STR = "";
}
