package attendance.domain;

import java.util.Arrays;

public enum Menu {
    ATTENDANCE_INSERT("1"),
    ATTENDANCE_UPDATE("2"),
    ATTENDANCE_READ("3"),
    RISK_CREW_READ("4"),
    QUIT("Q");

    private final String symbol;

    Menu(String symbol) {
        this.symbol = symbol;
    }

    public static Menu findBySymbol(String symbol) {
        return Arrays.stream(Menu.values())
                .filter(day -> day.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }
}
