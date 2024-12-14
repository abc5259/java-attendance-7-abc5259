package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.Attendance;
import attendance.io.AttendanceInit;

public class Application {
    public static void main(String[] args) {
        AttendanceConfig attendanceConfig = new AttendanceConfig();

        AttendanceInit attendanceInit = attendanceConfig.attendanceInit();

        Attendance attendance = attendanceInit.init();
        AttendanceController attendanceController = attendanceConfig.attendanceController();
        attendanceController.process(attendance);
    }
}
