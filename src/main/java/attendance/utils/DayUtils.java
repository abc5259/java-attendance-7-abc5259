package attendance.utils;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;

public class DayUtils {

    public static String toKorDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == MONDAY) {
            return "월요일";
        }
        if (dayOfWeek == TUESDAY) {
            return "화요일";
        }
        if (dayOfWeek == WEDNESDAY) {
            return "수요일";
        }
        if (dayOfWeek == THURSDAY) {
            return "목요일";
        }
        if (dayOfWeek == FRIDAY) {
            return "금요일";
        }
        if (dayOfWeek == SATURDAY) {
            return "토요일";
        }
        return "일요일";
    }
}
