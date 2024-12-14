package attendance.controller;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Menu;
import attendance.view.InputView;

public class IteratorInputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate iteratorInputTemplate;

    public IteratorInputHandler(InputView inputView, IteratorInputTemplate iteratorInputTemplate) {
        this.inputView = inputView;
        this.iteratorInputTemplate = iteratorInputTemplate;
    }

    public Menu inputMenu() {
        return iteratorInputTemplate.execute(
                inputView::inputMenu,
                new StringToMenuConverter()
        );
    }

}
