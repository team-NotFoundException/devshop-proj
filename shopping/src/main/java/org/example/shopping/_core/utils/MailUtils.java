package org.example.shopping._core.utils;

import java.util.Random;

public class MailUtils {

    public static String generatedRandomCode() {
        Random random = new Random();

        // 899999 까지의 랜덤 숫자 생성
        int code = 100000 + random.nextInt(900000);

        return String.valueOf(code);
    }
}
