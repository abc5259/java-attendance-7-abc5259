package attendance.domain;

public class SubjectCalculator {

    private static final int LATE_ABSENT_COUNT = 3;

    private final AttendanceHistory attendanceHistory;

    public SubjectCalculator(AttendanceHistory attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    public Subject calculateSubject() {
        int totalAbsence = attendanceHistory.totalLate() / LATE_ABSENT_COUNT + attendanceHistory.totalAbsence();
        if (totalAbsence > 5) {
            return Subject.WEEDING;
        }

        if (totalAbsence >= 3) {
            return Subject.INTERVIEW;
        }

        if (totalAbsence >= 2) {
            return Subject.WARNING;
        }

        return Subject.NONE;
    }
}
