package attendance.domain;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum Late {
    MON_LATE(DayOfWeek.MONDAY, new Time(13, 6)),
    TUE_LATE(DayOfWeek.TUESDAY, new Time(10, 6)),
    WED_LATE(DayOfWeek.WEDNESDAY, new Time(10, 6)),
    TUR_LATE(DayOfWeek.THURSDAY, new Time(10, 6)),
    FRI_LATE(DayOfWeek.FRIDAY, new Time(10, 6));

    private final DayOfWeek dayOfWeek;
    private final Time startTime;

    Late(DayOfWeek dayOfWeek, Time startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static boolean isLate(DayOfWeek dayOfWeek, Time time) {
        return Arrays.stream(Late.values())
                .anyMatch(late -> late.dayOfWeek == dayOfWeek && time.isEqualAndAfter(late.startTime));
    }
}
