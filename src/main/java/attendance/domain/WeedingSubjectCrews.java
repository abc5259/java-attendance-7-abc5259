package attendance.domain;

import java.util.List;

public class WeedingSubjectCrews {
    private final List<WeedingSubjectCrew> weedingSubjectCrews;

    public WeedingSubjectCrews(List<WeedingSubjectCrew> weedingSubjectCrews) {
        this.weedingSubjectCrews = weedingSubjectCrews;
    }

    public List<WeedingSubjectCrew> getWeedingSubjectCrews() {
        return weedingSubjectCrews;
    }
}
