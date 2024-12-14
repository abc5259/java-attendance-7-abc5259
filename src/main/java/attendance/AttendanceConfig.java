package attendance;

import attendance.controller.AttendanceController;
import attendance.controller.InputHandler;
import attendance.controller.InputTemplate;
import attendance.io.AttendanceInit;
import attendance.io.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceConfig {

    private InputView inputView;
    private OutputView outputView;
    private InputTemplate inputTemplate;
    private InputHandler inputHandler;
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

    public InputTemplate iteratorInputTemplate() {
        if (inputTemplate == null) {
            inputTemplate = new InputTemplate();
        }
        return inputTemplate;
    }

    public InputHandler iteratorInputHandler() {
        if (inputHandler == null) {
            inputHandler = new InputHandler(inputView(), iteratorInputTemplate());
        }
        return inputHandler;
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
