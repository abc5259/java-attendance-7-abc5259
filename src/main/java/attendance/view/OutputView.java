package attendance.view;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class OutputView {

    private static final String HELLO_MESSAGE_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";

    public void printHelloMessage(LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.printf(HELLO_MESSAGE_FORMAT, date.getMonth().getValue(), date.getDayOfMonth(),
                toKorDayOfWeek(dayOfWeek));
    }

    private String toKorDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == MONDAY) {
            return "월요일";
        }
        if (dayOfWeek == TUESDAY) {
            return "회요일";
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

    public void printErrorMessage(Exception exception) {
        System.out.printf(ERROR_MESSAGE_FORMAT, exception.getMessage());
    }
}
