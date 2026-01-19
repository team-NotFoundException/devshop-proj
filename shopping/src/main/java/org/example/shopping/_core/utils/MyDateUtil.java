package org.example.shopping._core.utils;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateUtil {

    private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

    // 시간 포맷터
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ORDER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHss");


    public static String timestampFormat(Timestamp createdAt) {

        return null;
    }

    public static String toDateString(LocalDateTime createdAtKst) {
        if (createdAtKst == null) return "";
        return createdAtKst.format(DATE_FORMATTER);
    }

    public static String toOrderString(LocalDateTime createdAtKst) {
        if (createdAtKst == null) return "";
        return createdAtKst.format(ORDER_DATE_FORMATTER);
    }
}
