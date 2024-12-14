package attendance.view;

import attendance.utils.DayUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OutputView {

    private static final String HELLO_MESSAGE_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";

    public void printHelloMessage(LocalDateTime dateTime) {

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        System.out.printf(HELLO_MESSAGE_FORMAT, dateTime.getMonth().getValue(), dateTime.getDayOfMonth(),
                DayUtils.toKorDayOfWeek(dayOfWeek));
    }


    public void printErrorMessage(Exception exception) {
        System.out.printf(ERROR_MESSAGE_FORMAT, exception.getMessage());
    }
}
