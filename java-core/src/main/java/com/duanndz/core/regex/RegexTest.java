package com.duanndz.core.regex;

/**
 * duan.nguyen
 * Datetime 3/13/20 09:26
 */
public class RegexTest {
    private static final String PHONE_NUMBER_REGEX =
            "([0|+84]([8])([37859])\\d{7}|[0]{1}([3,7,8,5,9]{1})\\d{8}|[0]{1}[3,7,8,5,9]{1}\\d{9})";

    public static void main(String[] args) {
        System.out.println("+84984922709".matches(PHONE_NUMBER_REGEX));
    }

}
