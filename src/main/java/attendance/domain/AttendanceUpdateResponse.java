package attendance.domain;

public record AttendanceUpdateResponse(
        AttendanceResult prevAttendanceResult,
        AttendanceResult updateAttendanceResult
) {
}
