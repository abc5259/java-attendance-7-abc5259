package attendance.domain;

import java.util.Collections;
import java.util.List;

public class AttendanceHistory {

    private final List<AttendanceResult> attendanceResults;

    public AttendanceHistory(List<AttendanceResult> attendanceResults) {
        this.attendanceResults = attendanceResults;
    }

    public List<AttendanceResult> getAttendanceResults() {
        return Collections.unmodifiableList(attendanceResults);
    }

    public int totalLate() {
        return attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendanceState() == AttendanceState.LATE)
                .toList()
                .size();
    }

    public int totalAbsence() {
        return attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendanceState() == AttendanceState.ABSENCE)
                .toList()
                .size();
    }

    public int totalAttendance() {
        return attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendanceState() == AttendanceState.GOOD)
                .toList()
                .size();
    }
}
