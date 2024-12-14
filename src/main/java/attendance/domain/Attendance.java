package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendances;

    public Attendance(Map<Crew, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    public void validateCrew(Crew crew) {
        if (!attendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }
}
