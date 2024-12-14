package attendance.controller;

import static attendance.domain.Menu.ATTENDANCE_INSERT;
import static attendance.domain.Menu.ATTENDANCE_UPDATE;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Attendance;
import attendance.domain.Campus;
import attendance.domain.Crew;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.domain.Time;
import attendance.utils.DayUtils;
import attendance.view.InputView;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InputHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final InputView inputView;
    private final InputTemplate inputTemplate;

    public InputHandler(InputView inputView, InputTemplate inputTemplate) {
        this.inputView = inputView;
        this.inputTemplate = inputTemplate;
    }

    public Menu inputMenu(LocalDateTime dateTime) {
        StringToMenuConverter converter = new StringToMenuConverter();
        return inputTemplate.execute(
                inputView::inputMenu,
                (value) -> {
                    Menu menu = converter.convert(value);
                    validateMenu(dateTime, menu);

                    return menu;
                }
        );
    }

    public Crew inputCrew(Attendance attendance, LocalDate date) {
        return inputTemplate.execute(
                inputView::inputCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateAttendanceCrew(crew, date);
                    return crew;
                }
        );
    }

    public LocalDateTime inputGoingSchoolDateTime(LocalDate currentDate, Campus campus) {
        return inputTemplate.execute(
                inputView::inputGoingSchoolTime,
                value -> {
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(
                                currentDate.toString() + " " + value.trim(), DATE_TIME_FORMATTER);
                        campus.validateCampusTime(new Time(dateTime.getHour(), dateTime.getMinute()));
                        return dateTime;
                    } catch (DateTimeException exception) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
                }
        );
    }

    public Crew inputAttendanceUpdateCrew(Attendance attendance) {
        return inputTemplate.execute(
                inputView::inputAttendanceUpdateCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateContainsCrew(crew);
                    return crew;
                }
        );
    }

    public LocalDateTime inputAttendanceUpdateDateTime(LocalDate currentDate, Campus campus) {
        LocalDate updateDate = inputAttendanceUpdateDayInMonth(currentDate);
        return inputTemplate.execute(
                inputView::inputAttendanceUpdateTime,
                value -> {
                    try {
                        LocalDateTime updateDateTime = LocalDateTime.parse(
                                updateDate.toString() + " " + value.trim(), DATE_TIME_FORMATTER);
                        campus.validateCampusTime(new Time(updateDateTime.getHour(), updateDateTime.getMinute()));
                        return updateDateTime;
                    } catch (DateTimeException dateTimeException) {
                        throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
                    }
                }
        );
    }

    public Crew inputAttendanceCheckCrew(Attendance attendance) {
        return inputTemplate.execute(
                inputView::inputCrewName,
                value -> {
                    Crew crew = new Crew(value);
                    attendance.validateContainsCrew(crew);
                    return crew;
                }
        );
    }

    private LocalDate inputAttendanceUpdateDayInMonth(LocalDate currentDate) {
        return inputTemplate.execute(
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
