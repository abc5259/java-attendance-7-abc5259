package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String INPUT_MENU = "1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";
    private static final String INPUT_CREW_NAME = "닉네임을 입력해 주세요.";
    private static final String INPUT_GOING_SCHOOL_TIME = "등교 시간을 입력해 주세요.";
    private static final String INPUT_ATTENDANCE_UPDATE_CREW_NAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String INPUT_ATTENDANCE_UPDATE_DAY_OF_MONTH = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String INPUT_ATTENDANCE_UPDATE_TIME = "언제로 변경하겠습니까?";

    public String inputMenu() {
        System.out.println(INPUT_MENU);
        return Console.readLine();
    }

    public String inputCrewName() {
        System.out.println();
        System.out.println(INPUT_CREW_NAME);
        return Console.readLine();
    }

    public String inputGoingSchoolTime() {
        System.out.println(INPUT_GOING_SCHOOL_TIME);
        return Console.readLine();
    }

    public String inputAttendanceUpdateCrewName() {
        System.out.println(INPUT_ATTENDANCE_UPDATE_CREW_NAME);
        return Console.readLine();
    }

    public String inputAttendanceUpdateDayInMonth() {
        System.out.println(INPUT_ATTENDANCE_UPDATE_DAY_OF_MONTH);
        return Console.readLine();
    }

    public String inputAttendanceUpdateTime() {
        System.out.println(INPUT_ATTENDANCE_UPDATE_TIME);
        return Console.readLine();
    }

}
