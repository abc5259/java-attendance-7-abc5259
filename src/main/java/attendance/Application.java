package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.Attendance;
import attendance.io.AttendanceInit;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Application {
    public static void main(String[] args) {
        AttendanceConfig attendanceConfig = new AttendanceConfig();

        AttendanceInit attendanceInit = attendanceConfig.attendanceInit();

        Attendance attendance = attendanceInit.init();
        AttendanceController attendanceController = attendanceConfig.attendanceController();
        attendanceController.process(attendance, LocalDateTime.of(2024, 12, 13, 14, 2));
    }

    private LocalDateTime getDateTime() {
        LocalDate localDate = DateTimes.now().toLocalDate();
        LocalTime localTime = DateTimes.now().toLocalTime();
        return LocalDateTime.of(localDate, localTime);
    }
}
