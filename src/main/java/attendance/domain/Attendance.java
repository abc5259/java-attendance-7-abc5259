package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        Optional<LocalDateTime> optionalDateTime = findOptionalDateTime(updateDateTime.toLocalDate(), dateTimes);
        LocalDateTime prevDateTime = optionalDateTime.orElseGet(
                () -> LocalDateTime.of(updateDateTime.toLocalDate(), LocalTime.MIN));
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

    public AttendanceHistory getAttendanceHistory(Crew crew, int year, int month, LocalDate lastDate) {
        List<LocalDateTime> dateTimes = attendances.get(crew);
        List<AttendanceResult> attendanceResults = new ArrayList<>();
        LocalDate currDate = LocalDate.of(year, month, 1);
        while (currDate.isBefore(lastDate) || currDate.isEqual(lastDate)) {
            if (isHoliday(currDate)) {
                currDate = currDate.plusDays(1);
                continue;
            }

            Optional<LocalDateTime> attendanceDateTime = findOptionalDateTime(currDate, dateTimes);
            addAttendanceResult(attendanceDateTime, attendanceResults, currDate);
            currDate = currDate.plusDays(1);
        }

        return new AttendanceHistory(attendanceResults);
    }

    public WeedingSubjectCrews getWeedingSubjectCrews(int year, int month, LocalDate lastDate) {
        List<WeedingSubjectCrew> weedingSubjectCrews = new ArrayList<>();
        attendances.forEach((crew, history) -> {
            AttendanceHistory attendanceHistory = getAttendanceHistory(crew, year, month, lastDate);
            SubjectCalculator subjectCalculator = new SubjectCalculator(attendanceHistory);
            Subject subject = subjectCalculator.calculateSubject();
            if (subject != Subject.NONE) {
                weedingSubjectCrews.add(new WeedingSubjectCrew(
                        crew,
                        attendanceHistory.totalLate(),
                        attendanceHistory.totalAbsence(),
                        subject
                ));
            }
        });
        return new WeedingSubjectCrews(weedingSubjectCrews);
    }

    private boolean isHoliday(LocalDate currDate) {
        return currDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                currDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                Holiday.contains(currDate);
    }

    private void addAttendanceResult(Optional<LocalDateTime> attendanceDateTime,
                                     List<AttendanceResult> attendanceResults,
                                     LocalDate currDate) {
        if (attendanceDateTime.isPresent()) {
            attendanceResults.add(
                    new AttendanceResult(attendanceDateTime.get(),
                            Late.calculateAttendanceState(attendanceDateTime.get())));
            return;
        }

        attendanceResults.add(
                new AttendanceResult(
                        LocalDateTime.of(currDate, LocalTime.MIN),
                        AttendanceState.ABSENCE
                )
        );
    }

    private LocalDateTime findDateTime(LocalDateTime findDateTime, List<LocalDateTime> dateTimes) {
        return dateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(findDateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 요일이 없습니다."));
    }

    private Optional<LocalDateTime> findOptionalDateTime(LocalDate findDate, List<LocalDateTime> dateTimes) {
        return dateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(findDate))
                .findFirst();
    }

    private boolean isAlreadyAttendance(LocalDate date, List<LocalDateTime> dateTimes) {
        return dateTimes.stream().anyMatch(dateTime -> dateTime.toLocalDate().isEqual(date));
    }
}
