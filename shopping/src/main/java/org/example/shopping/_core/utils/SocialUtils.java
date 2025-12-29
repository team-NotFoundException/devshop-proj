package org.example.shopping._core.utils;

import org.example.shopping.users.enums.Gender;

import java.time.LocalDate;

public class SocialUtils {
    public static Gender convertGender(String gender) {
        if (gender == null) return Gender.N;

        return switch (gender) {
            case "male" -> Gender.M;
            case "female" -> Gender.F;
            default -> Gender.N;
        };
    }

    public static LocalDate convertBirthday(String year, String birthday) {
        if (year == null || birthday == null) return null;

        int yyyy = Integer.parseInt(year);
        int mm = Integer.parseInt(birthday.substring(0,2));
        int dd = Integer.parseInt(birthday.substring(2,4));

        return LocalDate.of(yyyy, mm, dd);
    }
}
