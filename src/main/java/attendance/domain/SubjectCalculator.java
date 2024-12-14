package attendance.domain;

public class SubjectCalculator {

    private final AttendanceHistory attendanceHistory;

    public SubjectCalculator(AttendanceHistory attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    public Subject calculateSubject() {
        int totalAbsence = attendanceHistory.totalLate() / 3 + attendanceHistory.totalAbsence();
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
