package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void 잘못된_메뉴_입력시_예외_발생() {
        //given //when
        String menu = "-1";

        //then
        assertThatThrownBy(() -> Menu.findBySymbol(menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 형식을 입력하였습니다.");
    }
}