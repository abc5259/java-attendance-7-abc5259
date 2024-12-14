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

    private final IteratorInputHandler iteratorInputHandler;
    private final OutputView outputView;

    public AttendanceController(IteratorInputHandler iteratorInputHandler, OutputView outputView) {
        this.iteratorInputHandler = iteratorInputHandler;
        this.outputView = outputView;
    }

    public void process(Attendance attendance, LocalDateTime dateTime) {
        outputView.printHelloMessage(dateTime);
        Campus campus = new Campus();
        Menu menu = iteratorInputHandler.inputMenu(dateTime);
        if (menu == Menu.ATTENDANCE_INSERT) {
            Crew crew = iteratorInputHandler.inputCrew(attendance, dateTime.toLocalDate());
            LocalDateTime goingSchoolDateTIme = iteratorInputHandler.inputGoingSchoolDateTime(dateTime.toLocalDate(),
                    campus);
            AttendanceState attendanceState = attendance.attendanceCrew(crew, goingSchoolDateTIme);
            outputView.printAttendanceState(goingSchoolDateTIme, attendanceState);
        }
        if (menu == Menu.ATTENDANCE_UPDATE) {
            Crew crew = iteratorInputHandler.inputAttendanceUpdateCrew(attendance);
            LocalDateTime updateDateTime = iteratorInputHandler.inputAttendanceUpdateDateTime(dateTime.toLocalDate(),
                    campus);
            AttendanceUpdateResponse attendanceUpdateResponse = attendance.updateAttendance(crew, updateDateTime);
            outputView.printAttendanceUpdateResponse(attendanceUpdateResponse);
        }

        if (menu == Menu.ATTENDANCE_READ) {
            Crew crew = iteratorInputHandler.inputAttendanceCheckCrew(attendance);
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
