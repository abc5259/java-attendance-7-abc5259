package attendance.controller;

import static attendance.domain.Menu.ATTENDANCE_INSERT;
import static attendance.domain.Menu.ATTENDANCE_UPDATE;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.utils.DayUtils;
import attendance.view.InputView;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class IteratorInputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate iteratorInputTemplate;

    public IteratorInputHandler(InputView inputView, IteratorInputTemplate iteratorInputTemplate) {
        this.inputView = inputView;
        this.iteratorInputTemplate = iteratorInputTemplate;
    }

    public Menu inputMenu(LocalDateTime dateTime) {
        StringToMenuConverter converter = new StringToMenuConverter();
        return iteratorInputTemplate.execute(
                inputView::inputMenu,
                (value) -> {
                    Menu menu = converter.convert(value);
                    validateMenu(dateTime, menu);

                    return menu;
                }
        );
    }

    private void validateMenu(LocalDateTime dateTime, Menu menu) {
        if (menu != ATTENDANCE_INSERT && menu != ATTENDANCE_UPDATE) {
            return;
        }

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (
                dayOfWeek == DayOfWeek.SATURDAY
                        || dayOfWeek == DayOfWeek.SUNDAY
                        || Holiday.contains(dateTime.toLocalDate()
                )
        ) {
            throw new IllegalArgumentException(String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                    dateTime.getMonth().getValue(),
                    dateTime.getDayOfMonth(),
                    DayUtils.toKorDayOfWeek(dateTime.getDayOfWeek())));
        }
    }

}
