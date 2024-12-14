package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Menu;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class AttendanceController {

    private final IteratorInputHandler iteratorInputHandler;
    private final OutputView outputView;

    public AttendanceController(IteratorInputHandler iteratorInputHandler, OutputView outputView) {
        this.iteratorInputHandler = iteratorInputHandler;
        this.outputView = outputView;
    }

    public void process(Attendance attendance) {
        LocalDate localDate = DateTimes.now().toLocalDate();
        outputView.printHelloMessage(localDate);

        Menu menu = iteratorInputHandler.inputMenu();
    }
}
