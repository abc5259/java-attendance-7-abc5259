package attendance.domain;

import static attendance.domain.AttendanceState.GOOD;
import static attendance.domain.AttendanceState.LATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendances;

    public Attendance(Map<Crew, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    public void validateAttendanceCrew(Crew crew, LocalDate date) {
        if (!attendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }

        List<LocalDateTime> dateTimes = attendances.get(crew);
        if (isAlreadyAttendance(date, dateTimes)) {
            throw new IllegalArgumentException("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
    }

    public AttendanceState attendanceCrew(Crew crew, LocalDateTime attendanceDatTime) {
        attendances.get(crew).add(attendanceDatTime);

        int hour = attendanceDatTime.getHour();
        int minute = attendanceDatTime.getMinute();
        if (Late.isLate(attendanceDatTime.getDayOfWeek(), new Time(hour, minute))) {
            return LATE;
        }
        return GOOD;
    }

    private boolean isAlreadyAttendance(LocalDate date, List<LocalDateTime> dateTimes) {
        return dateTimes.stream().anyMatch(dateTime -> dateTime.toLocalDate().isEqual(date));
    }
}
