package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate localDate;

    Holiday(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static boolean contains(LocalDate localDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.localDate.isEqual(localDate));
    }
}
