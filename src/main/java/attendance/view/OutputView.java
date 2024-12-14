package attendance.view;

import attendance.domain.AttendanceState;
import attendance.utils.DayUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OutputView {

    private static final String HELLO_MESSAGE_FORMAT = "오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private static final String ATTENDANCE_STATE_FORMAT = "%d월 %02d일 %s %02d:%02d (%s)%n";

    public void printHelloMessage(LocalDateTime dateTime) {

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        System.out.printf(HELLO_MESSAGE_FORMAT, dateTime.getMonth().getValue(), dateTime.getDayOfMonth(),
                DayUtils.toKorDayOfWeek(dayOfWeek));
    }

    public void printErrorMessage(Exception exception) {
        System.out.printf(ERROR_MESSAGE_FORMAT, exception.getMessage());
    }

    public void printAttendanceState(LocalDateTime dateTime, AttendanceState attendanceState) {
        printEmptyLine();
        System.out.printf(
                ATTENDANCE_STATE_FORMAT,
                dateTime.getMonth().getValue(),
                dateTime.getDayOfMonth(),
                DayUtils.toKorDayOfWeek(dateTime.getDayOfWeek()),
                dateTime.getHour(),
                dateTime.getMinute(),
                toMessageAttendanceState(attendanceState)
        );
    }

    private void printEmptyLine() {
        System.out.println();
    }

    private String toMessageAttendanceState(AttendanceState attendanceState) {
        if (attendanceState == AttendanceState.LATE) {
            return "지각";
        }
        return "출석";
    }
}
