package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceState;
import attendance.domain.AttendanceUpdateResponse;
import attendance.domain.Campus;
import attendance.domain.Crew;
import attendance.domain.Menu;
import attendance.domain.SubjectCalculator;
import attendance.domain.WeedingSubjectCrews;
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
        Campus campus = new Campus();
        Menu menu;
        do {
            outputView.printHelloMessage(dateTime);
            menu = inputHandler.inputMenu(dateTime);
            run(attendance, dateTime, menu, campus);
        } while (menu != Menu.QUIT);
    }

    private void run(Attendance attendance, LocalDateTime dateTime, Menu menu, Campus campus) {
        if (menu == Menu.ATTENDANCE_INSERT) {
            runAttendanceInsert(attendance, dateTime, campus);
        }
        if (menu == Menu.ATTENDANCE_UPDATE) {
            runAttendanceUpdate(attendance, dateTime, campus);
        }

        if (menu == Menu.ATTENDANCE_READ) {
            runAttendanceRead(attendance, dateTime);
        }

        if (menu == Menu.RISK_CREW_READ) {
            runRiskCrewRead(attendance, dateTime);
        }
    }

    private void runAttendanceInsert(Attendance attendance, LocalDateTime dateTime, Campus campus) {
        Crew crew = inputHandler.inputCrew(attendance, dateTime.toLocalDate());
        LocalDateTime goingSchoolDateTIme = inputHandler.inputGoingSchoolDateTime(dateTime.toLocalDate(),
                campus);
        AttendanceState attendanceState = attendance.attendanceCrew(crew, goingSchoolDateTIme);
        outputView.printAttendanceState(goingSchoolDateTIme, attendanceState);
    }

    private void runAttendanceUpdate(Attendance attendance, LocalDateTime dateTime, Campus campus) {
        Crew crew = inputHandler.inputAttendanceUpdateCrew(attendance);
        LocalDateTime updateDateTime = inputHandler.inputAttendanceUpdateDateTime(dateTime.toLocalDate(),
                campus);
        AttendanceUpdateResponse attendanceUpdateResponse = attendance.updateAttendance(crew, updateDateTime);
        outputView.printAttendanceUpdateResponse(attendanceUpdateResponse);
    }

    private void runAttendanceRead(Attendance attendance, LocalDateTime dateTime) {
        LocalDateTime yesterday = dateTime.minusDays(1);
        Crew crew = inputHandler.inputAttendanceCheckCrew(attendance);
        AttendanceHistory attendanceHistory = attendance.getAttendanceHistory(
                crew,
                yesterday.getYear(),
                yesterday.getMonthValue(),
                yesterday.toLocalDate());
        SubjectCalculator subjectCalculator = new SubjectCalculator(attendanceHistory);
        outputView.printAttendanceHistory(attendanceHistory, subjectCalculator.calculateSubject());
    }

    private void runRiskCrewRead(Attendance attendance, LocalDateTime dateTime) {
        LocalDateTime yesterday = dateTime.minusDays(1);
        WeedingSubjectCrews weedingSubjectCrews = attendance.getWeedingSubjectCrews(
                yesterday.getYear(),
                yesterday.getMonthValue(),
                yesterday.toLocalDate());
        outputView.printWeedingSubjectCrews(weedingSubjectCrews);
    }
}
