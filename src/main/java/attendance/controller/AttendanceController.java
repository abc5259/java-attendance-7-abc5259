package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceState;
import attendance.domain.AttendanceUpdateResponse;
import attendance.domain.Campus;
import attendance.domain.Crew;
import attendance.domain.Menu;
import attendance.domain.SubjectCalculator;
import attendance.view.OutputView;
import java.time.LocalDateTime;

public class AttendanceController {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public AttendanceController(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public void process(Attendance attendance, LocalDateTime dateTime) {
        outputView.printHelloMessage(dateTime);
        Campus campus = new Campus();
        Menu menu = inputHandler.inputMenu(dateTime);
        if (menu == Menu.ATTENDANCE_INSERT) {
            Crew crew = inputHandler.inputCrew(attendance, dateTime.toLocalDate());
            LocalDateTime goingSchoolDateTIme = inputHandler.inputGoingSchoolDateTime(dateTime.toLocalDate(),
                    campus);
            AttendanceState attendanceState = attendance.attendanceCrew(crew, goingSchoolDateTIme);
            outputView.printAttendanceState(goingSchoolDateTIme, attendanceState);
        }
        if (menu == Menu.ATTENDANCE_UPDATE) {
            Crew crew = inputHandler.inputAttendanceUpdateCrew(attendance);
            LocalDateTime updateDateTime = inputHandler.inputAttendanceUpdateDateTime(dateTime.toLocalDate(),
                    campus);
            AttendanceUpdateResponse attendanceUpdateResponse = attendance.updateAttendance(crew, updateDateTime);
            outputView.printAttendanceUpdateResponse(attendanceUpdateResponse);
        }

        if (menu == Menu.ATTENDANCE_READ) {
            Crew crew = inputHandler.inputAttendanceCheckCrew(attendance);
            AttendanceHistory attendanceHistory = attendance.getAttendanceHistory(
                    crew,
                    dateTime.getYear(),
                    dateTime.getMonthValue(),
                    dateTime.toLocalDate());
            SubjectCalculator subjectCalculator = new SubjectCalculator(attendanceHistory);
            outputView.printAttendanceHistory(attendanceHistory, subjectCalculator.calculateSubject());
        }
    }
}
