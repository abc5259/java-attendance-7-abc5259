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
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                    } catch (DateTimeException exception) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
                }
        );
    }

    public Crew inputAttendanceUpdateCrew(Attendance attendance) {
        return iteratorInputTemplate.execute(
                inputView::inputAttendanceUpdateCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateContainsCrew(crew);
                    return crew;
                }
        );
    }

    public LocalDateTime inputAttendanceUpdateDateTime(LocalDate currentDate) {
        LocalDate updateDate = inputAttendanceUpdateDayInMonth(currentDate);
        return iteratorInputTemplate.execute(
                inputView::inputAttendanceUpdateTime,
                value -> {
                    try {
                        return LocalDateTime.parse(
                                updateDate.toString() + " " + value.trim(), DATE_TIME_FORMATTER);
                    } catch (DateTimeException dateTimeException) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
                }
        );
    }

    private LocalDate inputAttendanceUpdateDayInMonth(LocalDate currentDate) {
        return iteratorInputTemplate.execute(
                inputView::inputAttendanceUpdateDayInMonth,
                value -> {
                    try {
                        int day = Integer.parseInt(value);
                        LocalDate updateDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
                        if (updateDate.isAfter(currentDate)) {
                            throw new IllegalArgumentException("아직 수정할 수 없습니다.");
                        }
                        return updateDate;
                    } catch (NumberFormatException | DateTimeException exception) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
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
