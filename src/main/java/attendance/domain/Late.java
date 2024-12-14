package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

public enum Late {
    MON_LATE(DayOfWeek.MONDAY, new Time(13, 6), new Time(18, 0)),
    TUE_LATE(DayOfWeek.TUESDAY, new Time(10, 6), new Time(18, 0)),
    WED_LATE(DayOfWeek.WEDNESDAY, new Time(10, 6), new Time(18, 0)),
    TUR_LATE(DayOfWeek.THURSDAY, new Time(10, 6), new Time(18, 0)),
    FRI_LATE(DayOfWeek.FRIDAY, new Time(10, 6), new Time(18, 0));

    private final DayOfWeek dayOfWeek;
    private final Time startTime;
    private final Time endTime;

    Late(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static AttendanceState calculateAttendanceState(LocalDateTime dateTime) {
        return calculateAttendanceState(dateTime.getDayOfWeek(), new Time(dateTime.getHour(), dateTime.getMinute()));
    }

    public static AttendanceState calculateAttendanceState(DayOfWeek dayOfWeek, Time time) {
        if (isAbsence(dayOfWeek, time)) {
            return AttendanceState.ABSENCE;
        }
        if (isLate2(dayOfWeek, time)) {
            return AttendanceState.LATE;
        }
        return AttendanceState.GOOD;
    }

    private static boolean isAbsence(DayOfWeek dayOfWeek, Time time) {
        return Arrays.stream(Late.values())
                .anyMatch(late -> late.dayOfWeek == dayOfWeek && time.isAfter(late.endTime));
    }

    private static boolean isLate2(DayOfWeek dayOfWeek, Time time) {
        return Arrays.stream(Late.values())
                .anyMatch(late -> late.dayOfWeek == dayOfWeek && time.isEqualAndAfter(late.startTime));
    }
}
