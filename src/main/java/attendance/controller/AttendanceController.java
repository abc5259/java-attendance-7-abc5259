package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceState;
import attendance.domain.Crew;
import attendance.domain.Menu;
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
        Menu menu = iteratorInputHandler.inputMenu(dateTime);
        if (menu == Menu.ATTENDANCE_INSERT) {
            Crew crew = iteratorInputHandler.inputCrew(attendance, dateTime.toLocalDate());
            LocalDateTime goingSchoolDateTIme = iteratorInputHandler.inputGoingSchoolDateTime(dateTime.toLocalDate());
            AttendanceState attendanceState = attendance.attendanceCrew(crew, goingSchoolDateTIme);
            outputView.printAttendanceState(goingSchoolDateTIme, attendanceState);
        }
        if (menu == Menu.ATTENDANCE_UPDATE) {
            Crew crew = iteratorInputHandler.inputAttendanceUpdateCrew(attendance, dateTime.toLocalDate());
        }
    }
}
