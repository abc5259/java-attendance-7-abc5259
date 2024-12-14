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
