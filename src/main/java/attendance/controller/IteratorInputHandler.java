package attendance.controller;

import static attendance.domain.Menu.ATTENDANCE_INSERT;
import static attendance.domain.Menu.ATTENDANCE_UPDATE;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.utils.DayUtils;
import attendance.view.InputView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IteratorInputHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    public Crew inputCrew(Attendance attendance, LocalDate date) {
        return iteratorInputTemplate.execute(
                inputView::inputCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateAttendanceCrew(crew, date);
                    return crew;
                }
        );
    }

    public LocalDateTime inputGoingSchoolDateTime(LocalDate currentDate) {
        return iteratorInputTemplate.execute(
                inputView::inputGoingSchoolTime,
                value -> {
                    try {
                        return LocalDateTime.parse(
                                currentDate.toString() + " " + value.trim(), DATE_TIME_FORMATTER);
                    } catch (DateTimeParseException exception) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
                }
        );
    }

    public Crew inputAttendanceUpdateCrew(Attendance attendance, LocalDate date) {
        return iteratorInputTemplate.execute(
                inputView::inputAttendanceUpdateCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateAttendanceCrew(crew, date);
                    return crew;
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
