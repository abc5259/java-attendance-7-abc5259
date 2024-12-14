package attendance.domain;

import java.util.Arrays;

public enum Day {
    MON("월"), THU("화"), WES("수"), TUR("목"), FRI("금"), SAT("토"), SUN("일");

    private final String name;

    Day(String name) {
        this.name = name;
    }

    public static Day findByName(String name) {
        return Arrays.stream(Day.values())
                .filter(day -> day.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요일 이름입니다."));
    }

    public Day nextDay() {
        if (this == MON) {
            return THU;
        }
        if (this == THU) {
            return WES;
        }
        if (this == WES) {
            return TUR;
        }
        if (this == TUR) {
            return FRI;
        }
        if (this == FRI) {
            return SAT;
        }
        if (this == SAT) {
            return SUN;
        }
        return MON;
    }

    public boolean isDayOff() {
        return this == SAT || this == SUN;
    }

    public String getName() {
        return name;
    }
}
