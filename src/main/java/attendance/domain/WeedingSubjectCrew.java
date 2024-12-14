package attendance.domain;

public class WeedingSubjectCrew implements Comparable<WeedingSubjectCrew> {
    private final Crew crew;
    private final int lateCount;
    private final int absenceCount;
    private final Subject subject;

    public WeedingSubjectCrew(Crew crew, int lateCount, int absenceCount, Subject subject) {

        this.crew = crew;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.subject = subject;
    }

    public String getCrewName() {
        return crew.getName();
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public Subject getSubject() {
        return subject;
    }

    public int calculateAbsenceCount() {
        return getLateCount() / 3 + getAbsenceCount();
    }

    // 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며,
    // 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.

    @Override
    public int compareTo(WeedingSubjectCrew o) {
        int absenceCount = calculateAbsenceCount();
        int otherAbsenceCount = o.calculateAbsenceCount();

        if (absenceCount == otherAbsenceCount) {
            if (lateCount == o.lateCount) {
                return getCrewName().compareTo(o.getCrewName());
            }
            return o.lateCount - lateCount;
        }
        return o.absenceCount - otherAbsenceCount;
    }
}
