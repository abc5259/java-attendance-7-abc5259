package attendance.io;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceInit {

    private static final Path ATTENDANCE_PATH = Path.of("src/main/resources/attendances.csv");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final FileReader fileReader;

    public AttendanceInit(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Attendance init() {
        List<String> lines = fileReader.readAllLines(ATTENDANCE_PATH);
        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        for (String line : lines) {
            String[] sources = line.split(",");
            if (sources.length != 2) {
                throw new IllegalArgumentException("잘못된 파일 형식입니다.");
            }
            Crew crew = new Crew(sources[0].trim());
            LocalDateTime localDateTime = LocalDateTime.parse(sources[1].trim(), DATE_TIME_FORMATTER);
            List<LocalDateTime> localDateTimes = attendances.getOrDefault(crew, new ArrayList<>());
            localDateTimes.add(localDateTime);
            attendances.put(crew, localDateTimes);
        }
        return new Attendance(attendances);
    }
}
