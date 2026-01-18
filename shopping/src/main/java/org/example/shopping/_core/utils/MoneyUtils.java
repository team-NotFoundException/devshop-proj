package org.example.shopping._core.utils;

import java.text.DecimalFormat;

public class MoneyUtils {
    public static final DecimalFormat decimalFormat = new DecimalFormat();

    public static String format(Long amount) {
        if (amount == null) return "";
        return decimalFormat.format(amount);
    }
}
