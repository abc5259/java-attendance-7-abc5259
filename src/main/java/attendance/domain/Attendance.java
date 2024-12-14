package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void validateContainsCrew(Crew crew) {
        if (!attendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public AttendanceState attendanceCrew(Crew crew, LocalDateTime attendanceDatTime) {
        attendances.get(crew).add(attendanceDatTime);
        return Late.calculateAttendanceState(attendanceDatTime);
    }

    public AttendanceUpdateResponse updateAttendance(Crew crew, LocalDateTime updateDateTime) {
        List<LocalDateTime> dateTimes = attendances.get(crew);

        LocalDateTime prevDateTime = findDateTime(updateDateTime, dateTimes);
        List<LocalDateTime> newDateTimes = dateTimes.stream()
                .filter(dateTime -> !dateTime.toLocalDate().isEqual(updateDateTime.toLocalDate()))
                .collect(Collectors.toList());
        newDateTimes.add(updateDateTime);
        attendances.put(crew, newDateTimes);

        return new AttendanceUpdateResponse(
                new AttendanceResult(prevDateTime, Late.calculateAttendanceState(prevDateTime)),
                new AttendanceResult(updateDateTime, Late.calculateAttendanceState(updateDateTime))
        );
    }

    private LocalDateTime findDateTime(LocalDateTime updateDateTime, List<LocalDateTime> dateTimes) {
        return dateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(updateDateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 요일이 없습니다."));
    }

    private boolean isAlreadyAttendance(LocalDate date, List<LocalDateTime> dateTimes) {
        return dateTimes.stream().anyMatch(dateTime -> dateTime.toLocalDate().isEqual(date));
    }
}
