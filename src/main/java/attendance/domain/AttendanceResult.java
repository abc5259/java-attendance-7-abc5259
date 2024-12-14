package attendance.domain;

import java.time.LocalDateTime;

public record AttendanceResult(
        LocalDateTime dateTime,
        AttendanceState attendanceState
) {
}
