package attendance.domain;

public class Campus {

    private static final Time START_TIME = new Time(8, 0);
    private static final Time END_TIME = new Time(23, 0);

    public void validateCampusTime(Time time) {
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
}
