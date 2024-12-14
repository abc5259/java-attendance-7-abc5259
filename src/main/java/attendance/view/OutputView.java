package attendance.view;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceState;
import attendance.domain.AttendanceUpdateResponse;
import attendance.domain.Subject;
import attendance.domain.WeedingSubjectCrews;
import attendance.utils.DayUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OutputView {

    private static final String HELLO_MESSAGE_FORMAT = "오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private static final String ATTENDANCE_STATE_FORMAT = "%d월 %02d일 %s %02d:%02d (%s)%n";
    private static final String ATTENDANCE_ABSENCE_STATE_FORMAT = "%d월 %02d일 %s --:-- (결석)%n";
    private static final String ATTENDANCE_UPDATE_STATE_FORMAT = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n";

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
        if (attendanceState == AttendanceState.ABSENCE) {
            return "결석";
        }
        return "출석";
    }

    public void printAttendanceUpdateResponse(AttendanceUpdateResponse attendanceUpdateResponse) {
        printEmptyLine();
        AttendanceResult prevAttendanceResult = attendanceUpdateResponse.prevAttendanceResult();
        AttendanceResult updateAttendanceResult = attendanceUpdateResponse.updateAttendanceResult();
        System.out.printf(
                ATTENDANCE_UPDATE_STATE_FORMAT,
                prevAttendanceResult.dateTime().getMonth().getValue(),
                prevAttendanceResult.dateTime().getDayOfMonth(),
                DayUtils.toKorDayOfWeek(prevAttendanceResult.dateTime().getDayOfWeek()),
                prevAttendanceResult.dateTime().getHour(),
                prevAttendanceResult.dateTime().getMinute(),
                toMessageAttendanceState(prevAttendanceResult.attendanceState()),
                updateAttendanceResult.dateTime().getHour(),
                updateAttendanceResult.dateTime().getMinute(),
                toMessageAttendanceState(updateAttendanceResult.attendanceState())
        );
    }

    public void printAttendanceHistory(AttendanceHistory attendanceHistory, Subject subject) {
        List<AttendanceResult> attendanceResults = attendanceHistory.getAttendanceResults();
        System.out.println("이번 달 빙티의 출석 기록입니다.");
        printEmptyLine();
        attendanceResults.forEach(attendanceResult -> {
            LocalDateTime dateTime = attendanceResult.dateTime();
            AttendanceState attendanceState = attendanceResult.attendanceState();

            if (attendanceState == AttendanceState.ABSENCE && dateTime.toLocalTime().equals(LocalTime.MIN)) {
                System.out.printf(
                        ATTENDANCE_ABSENCE_STATE_FORMAT,
                        dateTime.getMonth().getValue(),
                        dateTime.getDayOfMonth(),
                        DayUtils.toKorDayOfWeek(dateTime.getDayOfWeek()));
                return;
            }
            System.out.printf(
                    ATTENDANCE_STATE_FORMAT,
                    dateTime.getMonth().getValue(),
                    dateTime.getDayOfMonth(),
                    DayUtils.toKorDayOfWeek(dateTime.getDayOfWeek()),
                    dateTime.getHour(),
                    dateTime.getMinute(),
                    toMessageAttendanceState(attendanceState)
            );
        });

        printEmptyLine();
        System.out.printf("출석: %d회%n", attendanceHistory.totalAttendance());
        System.out.printf("지각: %d회%n", attendanceHistory.totalLate());
        System.out.printf("결석: %d회%n", attendanceHistory.totalAbsence());
        printEmptyLine();

        if (subject == Subject.NONE) {
            return;
        }

        System.out.printf("%s 대상자입니다.%n", subject.getName());
    }

    public void printWeedingSubjectCrews(WeedingSubjectCrews weedingSubjectCrews) {
        printEmptyLine();
        System.out.println("제적 위험자 조회 결과");
        weedingSubjectCrews.getWeedingSubjectCrews().forEach(
                weedingSubjectCrew -> {
                    System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                            weedingSubjectCrew.getCrewName(),
                            weedingSubjectCrew.getAbsenceCount(),
                            weedingSubjectCrew.getLateCount(),
                            weedingSubjectCrew.getSubject().getName());
                }
        );
    }
}
