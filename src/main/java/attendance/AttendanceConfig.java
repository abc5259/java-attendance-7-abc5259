package attendance;

import attendance.controller.AttendanceController;
import attendance.controller.IteratorInputHandler;
import attendance.controller.IteratorInputTemplate;
import attendance.io.AttendanceInit;
import attendance.io.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceConfig {

    private InputView inputView;
    private OutputView outputView;
    private IteratorInputTemplate iteratorInputTemplate;
    private IteratorInputHandler iteratorInputHandler;
    private AttendanceController attendanceController;
    private FileReader fileReader;
    private AttendanceInit attendanceInit;

    public InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    public OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

    public IteratorInputTemplate iteratorInputTemplate() {
        if (iteratorInputTemplate == null) {
            iteratorInputTemplate = new IteratorInputTemplate(outputView());
        }
        return iteratorInputTemplate;
    }

    public IteratorInputHandler iteratorInputHandler() {
        if (iteratorInputHandler == null) {
            iteratorInputHandler = new IteratorInputHandler(inputView(), iteratorInputTemplate());
        }
        return iteratorInputHandler;
    }

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            attendanceController = new AttendanceController(iteratorInputHandler(), outputView());
        }
        return attendanceController;
    }

    public FileReader fileReader() {
        if (fileReader == null) {
            fileReader = new FileReader();
        }
        return fileReader;
    }

    public AttendanceInit attendanceInit() {
        if (attendanceInit == null) {
            attendanceInit = new AttendanceInit(fileReader());
        }
        return attendanceInit;
    }
}
