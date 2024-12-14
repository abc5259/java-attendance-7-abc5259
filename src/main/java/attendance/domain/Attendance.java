package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendances;

    public Attendance(Map<Crew, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }
}
