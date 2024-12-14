package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String INPUT_MENU = "1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";
    private static final String INPUT_CREW_NAME = "닉네임을 입력해 주세요.";

    public String inputMenu() {
        System.out.println(INPUT_MENU);
        return Console.readLine();
    }

    public String inputCrewName() {
        System.out.println(INPUT_CREW_NAME);
        return Console.readLine();
    }
}
