package attendance.domain;

public enum Subject {
    WARNING("경고"), INTERVIEW("면담"), WEEDING("재적"), NONE("");

    private final String name;

    Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
