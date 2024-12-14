# java-attendance-precourse

## 기능 구현 목록

- [x] src/main/resources/attendances.csv를 통해 출석현황을 초기화한다.
- [x] 오늘 날짜와 함께 기능 선택 메시지를 출력한다.
- [x] 기능 선택지를 출력하고 기능을 입력받는다.
    - [x] 예외1. 없는 기능을 선택한 경우. (잘못된 형식을 입력하였습니다.)
- [ ] 출석 확인 기능을 구현한다.
    - [ ] 예외1. 주말 또는 공휴일에 출석을 확인하는 경우 (12월 14일 토요일은 등교일이 아닙니다.)
    - [ ] 출석을 확인하고자 하는 닉네임을 입력받는다.
        - [ ] 예외1. 존재하지 않는 닉네임일 경우 (등록되지 않은 닉네임입니다.)
        - [ ] 예외2. 아무값도 작성하지 않는 경우
        - [ ] 예외3. 이미 출석을 하였는데 다시 출석 확인을 하는 경우 (이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.)
    - [ ] 등교시간을 입력받는다.
        - [ ] 예외1. 시간을 잘못된 형식으로 입력한 경우 (잘못된 형식을 입력하였습니다.)
    - [ ] 출석 기록을 계산한다.
    - [ ] 출석 기록을 출력한다.
- [ ] 출석 수정 기능을 구현한다.
    - [ ] 예외1. 주말 또는 공휴일에 출석을 수정하는 경우 (12월 14일 토요일은 등교일이 아닙니다.)
    - [ ] 출석을 수정하려는 크루의 닉네임을 입력한다.
        - [ ] 예외1. 존재하지 않는 닉네임일 경우 (등록되지 않은 닉네임입니다.)
        - [ ] 예외2. 아무값도 작성하지 않는 경우
    - [ ] 수정하려는 날짜(일)를 입력받는다.
        - [ ] 예외1. 날짜를 잘못된 형식으로 입력한 경우 (잘못된 형식을 입력하였습니다.)
        - [ ] 예외2. 미래 날짜로 출석을 수정하는 경우 ( 아직 수정할 수 없습니다.)
        - [ ] 예외3. 출석을 하지 않은 경우
    - [ ] 수정하려는 시간을 입력받는다.
        - [ ] 예외1. 날짜를 잘못된 형식으로 입력한 경우 (잘못된 형식을 입력하였습니다.)
        - [ ] 예외2. 캠퍼스 운영 시간이 아닌 경우 (캠퍼스 운영 시간에만 출석이 가능합니다.)
- [ ] 크루별 출석 기록 확인 기능을 구현한다.
    - [ ] 출석을 확인하려는 크루의 닉네임을 입력한다.
        - [ ] 예외1. 존재하지 않는 닉네임일 경우 (등록되지 않은 닉네임입니다.)
        - [ ] 예외2. 아무값도 작성하지 않는 경우
    - [ ] 해당 크루의 이번 달 출석 기록을 출력한다.
        - [ ] 주말과 공휴일은 포함하지 않는다.
    - [ ] 출석, 지각, 결석 횟수를 출력한다.
    - [ ] 경고대상자, 면담대상자, 재적 대상자를 구해 출력한다.
- [ ] 재적 위험자 확인 기능을 구현한다.
    - [ ] 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
    - [ ] 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며, 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.