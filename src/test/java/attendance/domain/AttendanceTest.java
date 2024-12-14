package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {


    @ParameterizedTest
    @CsvSource({
            "2024-12-13T10:00, GOOD",
            "2024-12-13T10:06, LATE",
            "2024-12-13T10:31, ABSENCE",
    })
    void 크루가_출석체크를_하면_출석인지_지각인지_결석인지_알_수_있다(LocalDateTime localDateTime, AttendanceState expected) {
        //given
        Crew crew = new Crew("재훈");
        Map<Crew, List<LocalDateTime>> attendances = Map.of(
                crew, new ArrayList<>()
        );
        Attendance attendance = new Attendance(attendances);

        //when
        AttendanceState attendanceState = attendance.attendanceCrew(crew, localDateTime);

        //then
        assertThat(attendanceState).isEqualTo(expected);
    }
}
