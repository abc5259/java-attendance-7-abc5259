package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Menu;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {

    private final IteratorInputHandler iteratorInputHandler;
    private final OutputView outputView;

    public AttendanceController(IteratorInputHandler iteratorInputHandler, OutputView outputView) {
        this.iteratorInputHandler = iteratorInputHandler;
        this.outputView = outputView;
    }

    public void process(Attendance attendance) {
        LocalDateTime dateTime = getDateTime();
        outputView.printHelloMessage(dateTime);
        Menu menu = iteratorInputHandler.inputMenu(dateTime);
        if (menu == Menu.ATTENDANCE_INSERT) {
            Crew crew = iteratorInputHandler.inputCrew(attendance, dateTime.toLocalDate());
            LocalDateTime goingSchoolDateTIme = iteratorInputHandler.inputGoingSchoolDateTime(dateTime.toLocalDate());
        }
    }

    private LocalDateTime getDateTime() {
        LocalDate localDate = DateTimes.now().toLocalDate();
        LocalTime localTime = DateTimes.now().toLocalTime();
        return LocalDateTime.of(localDate, localTime);
    }
}
